package natcash.business.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import natcash.business.dto.request.WalletTransactionRequest;
import natcash.business.dto.response.MessageResponse;
import natcash.business.dto.response.RequestResponseDTO;
import natcash.business.entity.Payment;
import natcash.business.entity.TransactionLog;
import natcash.business.entity.WalletPaymentLog;
import natcash.business.repository.WalletPaymentLogRepository;
import natcash.business.service.PaymentService;
import natcash.business.service.TransactionLogService;
import natcash.business.service.WalletPaymentLogService;
import natcash.business.utils.ErrorCode;
import natcash.business.utils.PaymentStatus;
import natcash.business.utils.PaymentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class WalletPaymentLogServiceImpl implements WalletPaymentLogService {
    private final WalletPaymentLogRepository walletPaymentLogRepository;
    private final PaymentService paymentService;
    private final TransactionLogService transactionLogService;

    @Value("${payment.secret_key}")
    private String privateKey;

    @Value("${payment.callback_params}")
    private String callbackParams;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Override
    public void save(WalletPaymentLog paymentLog) {
        walletPaymentLogRepository.save(paymentLog);
    }

    @Override
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
            messagingTemplate.convertAndSend("/topic/payment-status-" + payment.getId().toString(),messageResponse);

            return responseDTO;
        } catch (Exception e) {
            RequestResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(ErrorCode.ERR_PAYMENT_NOT_FOUND, ErrorCode.ERR_PAYMENT_NOT_FOUND.message());
            transactionLogService.saveConfirmTransactionLog(responseDTO, payment.getId());
            return responseDTO;
        }

    }
}
