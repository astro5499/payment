package natcash.business.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import natcash.business.dto.request.ConfirmPaymentRequestDTO;
import natcash.business.dto.request.WalletTransactionRequest;
import natcash.business.dto.response.MessageResponse;
import natcash.business.dto.response.PaymentQueryDTO;
import natcash.business.dto.response.RequestResponseDTO;
import natcash.business.entity.Payment;
import natcash.business.entity.TransactionLog;
import natcash.business.entity.WalletPaymentLog;
import natcash.business.repository.WalletPaymentLogRepository;
import natcash.business.service.MessageSenderService;
import natcash.business.service.PaymentService;
import natcash.business.service.TransactionLogService;
import natcash.business.service.WalletPaymentLogService;
import natcash.business.utils.ErrorCode;
import natcash.business.utils.PaymentStatus;
import natcash.business.utils.PaymentUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class WalletPaymentLogServiceImpl implements WalletPaymentLogService {
    private final WalletPaymentLogRepository walletPaymentLogRepository;
    private final PaymentService paymentService;
    private final TransactionLogService transactionLogService;
    private final MessageSenderService messageSenderService;

    @Value("${payment.secret_key}")
    private String privateKey;

    @Value("${payment.callback_params}")
    private String callbackParams;


    @Override
    public void save(WalletPaymentLog paymentLog) {
        walletPaymentLogRepository.save(paymentLog);
    }

    @Override
    public void saveAll(List<WalletPaymentLog> paymentLogs) {
        walletPaymentLogRepository.saveAll(paymentLogs);
    }

    @Override
    @Transactional
    public RequestResponseDTO confirmPayment(WalletTransactionRequest request) throws JsonProcessingException {
        Payment payment = paymentService.findPaymentByTransCodeAndStatus(request.getTransactionContent(), PaymentStatus.PENDING.getValue());
        try {
            if (Objects.isNull(payment)) {
                RequestResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(ErrorCode.ERR_PAYMENT_NOT_FOUND, ErrorCode.ERR_PAYMENT_NOT_FOUND.message());
                transactionLogService.saveConfirmTransactionLog(responseDTO, null);
                return responseDTO;
            }

            WalletPaymentLog logtrans = new WalletPaymentLog();
            logtrans.setEwalletTransactionId(Long.valueOf(request.getTransactionId()));
            logtrans.setAmount(BigDecimal.valueOf(request.getAmount().doubleValue()));
            logtrans.setTransactionContent(request.getTransactionContent());
            logtrans.setFromPhone(request.getFromPhone());
            logtrans.setToPhone(request.getToPhone());
            logtrans.setCreated_at(LocalDateTime.now());
            RequestResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(ErrorCode.SUCCESS, ErrorCode.SUCCESS.message());
            walletPaymentLogRepository.save(logtrans);

            TransactionLog transactionLog = transactionLogService.saveConfirmTransactionLog(responseDTO, payment.getId());
            payment.setStatus(PaymentStatus.SUCCESS.getValue());
            paymentService.updatePaymentStatus(payment);

            String callbackUrl = PaymentUtils.generateCallbackToken(transactionLog.getCallbackUrl(), logtrans.getEwalletTransactionId(), logtrans.getToPhone(), privateKey, callbackParams);
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setStatus("SUCCESS");
            messageResponse.setCallbackUrl(callbackUrl);


            log.info("Sending to topic: /topic/payment-status-" + payment.getId());
            messageSenderService.sendMessage("/topic/payment-status-" + payment.getId().toString(),messageResponse);

            return responseDTO;
        } catch (Exception e) {
            RequestResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(ErrorCode.ERR_PAYMENT_NOT_FOUND, ErrorCode.ERR_PAYMENT_NOT_FOUND.message());
            transactionLogService.saveConfirmTransactionLog(responseDTO, payment.getId());

            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setStatus("FAILED");
            messageSenderService.sendMessage("/topic/payment-status-" + payment.getId().toString(), messageResponse);
            return responseDTO;
        }
    }

    @Override
    public Long getMaxIdByFinAccount(String accountId) {
        return walletPaymentLogRepository.findMaxTransactionIdByFinAccount(accountId);
    }

    @Override
    public void autoConfirmPayment(Set<PaymentQueryDTO> payments) throws JsonProcessingException {
        for (PaymentQueryDTO payment : payments) {
            UUID paymentId = UUID.fromString(payment.getId());
            try {
                RequestResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(ErrorCode.SUCCESS, ErrorCode.SUCCESS.message());
                TransactionLog transactionLog = transactionLogService.saveConfirmTransactionLog(responseDTO, paymentId);
                paymentService.confirmPayments(paymentId);

                String callbackUrl = PaymentUtils.generateCallbackToken(transactionLog.getCallbackUrl(), payment.getTransactionId(), payment.getToAccount(), privateKey, callbackParams);
                MessageResponse messageResponse = new MessageResponse();
                messageResponse.setStatus("SUCCESS");
                messageResponse.setCallbackUrl(callbackUrl);


                log.info("Sending to topic: /topic/payment-status-" + paymentId);
                log.info("Sending to topic: messageResponse: " + messageResponse);
                messageSenderService.sendMessage("/topic/payment-status-" + paymentId, messageResponse);

            } catch (Exception e) {
                RequestResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(ErrorCode.ERR_PAYMENT_NOT_FOUND, ErrorCode.ERR_PAYMENT_NOT_FOUND.message());
                transactionLogService.saveConfirmTransactionLog(responseDTO, paymentId);

                MessageResponse messageResponse = new MessageResponse();
                messageResponse.setStatus("FAILED");
                messageSenderService.sendMessage("/topic/payment-status-" + paymentId.toString(), messageResponse);
            }
        }
    }

    @Override
    public void confirmPayment(ConfirmPaymentRequestDTO requestDTO) throws JsonProcessingException {
        Set<PaymentQueryDTO> payments = paymentService.findByPartnerCodeAndOrderIdAndStatus(requestDTO.getPartnerCode(), requestDTO.getOrderId(), PaymentStatus.PENDING.getValue());

        if (!CollectionUtils.isEmpty(payments)) {
            this.autoConfirmPayment(payments);
        }
    }
}
