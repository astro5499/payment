package natcash.business.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import natcash.business.dto.request.PaymentRequestDTO;
import natcash.business.dto.response.PaymentResponseDTO;
import natcash.business.dto.response.RequestResponseDTO;
import natcash.business.entity.FinAccount;
import natcash.business.entity.Partner;
import natcash.business.entity.Payment;
import natcash.business.repository.CodeSeqRepository;
import natcash.business.repository.PartnerRepository;
import natcash.business.service.FinAccountService;
import natcash.business.service.PartnerService;
import natcash.business.service.PaymentService;
import natcash.business.service.TransactionLogService;
import natcash.business.utils.ErrorCode;
import natcash.business.utils.PaymentStatus;
import natcash.business.utils.PaymentUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Log4j2
@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    @Value("${payment.secret_key}")
    private String privateKey;

    @Value("${payment.expired_time}")
    private Long expiredAt;

    @Value("${payment.url}")
    private String url;


    private final PartnerRepository repository;

    private final TransactionLogService transactionLogService;

    private final FinAccountService finAccountService;

    private final PaymentService paymentService;

    private final CodeSeqRepository codeSeqRepository;
    @Override
    public Partner findByUsername(String username) {
        return repository.findByUsername(username);
    }



    @Override
    public PaymentResponseDTO initPayment(PaymentRequestDTO requestDTO, String clientIp) throws Exception {
        String generatedSignature = PaymentUtils.getSignature(requestDTO, privateKey);

        if (!generatedSignature.equals(requestDTO.getSignature())) {
            return handleError(requestDTO, ErrorCode.ERR_PARAMETERS_INVALID);
        }

        Partner partner = this.findByUsername(requestDTO.getUsername());
        if (Objects.isNull(partner)) {
            return handleError(requestDTO, ErrorCode.ERR_ACCOUNT_EXISTS);
        }

        List<String> partnerIps = Arrays.asList(partner.getIp().split(";"));
        if (!partnerIps.contains(clientIp)) {
            return handleError(requestDTO, ErrorCode.ERR_PARTNER_IP_NOT_VALID);
        }

        if (transactionLogService.isExistsByRequestId(requestDTO.getRequestId())) {
            return handleError(requestDTO, ErrorCode.ERR_PARTNER_REQUEST_ORDER_ALREADY_EXIST);
        }

        FinAccount availableFinAccount = finAccountService.findAvailableFinAccount(requestDTO.getAmount());
        if (Objects.isNull(availableFinAccount)) {
            return handleError(requestDTO, ErrorCode.ERR_SYSTEM_BUSY);
        }
        Long nextVal = codeSeqRepository.getNextSequenceValue();
        log.info(PaymentUtils.generateTransCode(nextVal));
        Payment payment = paymentService.createPayment(requestDTO, availableFinAccount.getAccountId(), PaymentUtils.generateTransCode(nextVal));
        PaymentResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(String.valueOf(ErrorCode.SUCCESS.status()), ErrorCode.SUCCESS.code(), ErrorCode.SUCCESS.message(), PaymentUtils.getUrl(url, payment.getId()), expiredAt);
        transactionLogService.saveTransactionLog(requestDTO, responseDTO, payment.getId(), PaymentUtils.generateTransCode(nextVal));
        return responseDTO;
    }

    @Override
    public RequestResponseDTO confirmPayment(String paymentId) throws JsonProcessingException {
        LocalDateTime current = LocalDateTime.now();
        Payment payment = paymentService.findById(UUID.fromString(paymentId));
        if (Objects.isNull(payment)) {
            RequestResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(ErrorCode.ERR_PAYMENT_NOT_FOUND, ErrorCode.ERR_PAYMENT_NOT_FOUND.message());
            transactionLogService.saveConfirmTransactionLog(responseDTO, UUID.fromString(paymentId));
            return responseDTO;
        }

        LocalDateTime dateTime = payment.getCreatedAt();
        if (!dateTime.toLocalDate().isEqual(current.toLocalDate()) || Duration.between(dateTime, current).getSeconds() > expiredAt) {
            RequestResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(ErrorCode.ERR_PAYMENT_EXPIRED, ErrorCode.ERR_PAYMENT_EXPIRED.message());
            transactionLogService.saveConfirmTransactionLog(responseDTO, UUID.fromString(paymentId));
            return responseDTO;
        }

        RequestResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(ErrorCode.SUCCESS, ErrorCode.SUCCESS.message());
        transactionLogService.saveConfirmTransactionLog(responseDTO, UUID.fromString(paymentId));

        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        paymentService.updatePaymentStatus(payment);

        finAccountService.updateFinAccountBalance(payment);

        return responseDTO;
    }

    @Override
    public RequestResponseDTO expiredPayment(UUID paymentId) throws JsonProcessingException {
        Payment payment = paymentService.findById(paymentId);
        if (Objects.isNull(payment)) {
            RequestResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(ErrorCode.ERR_PAYMENT_NOT_FOUND, ErrorCode.ERR_PAYMENT_NOT_FOUND.message());
            transactionLogService.saveExpiredTransactionLog(responseDTO, paymentId);
            return responseDTO;
        }

        RequestResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(ErrorCode.SUCCESS, ErrorCode.SUCCESS.message());
        transactionLogService.saveExpiredTransactionLog(responseDTO, paymentId);

        payment.setStatus(PaymentStatus.EXPIRED.getValue());
        paymentService.updatePaymentStatus(payment);

        return responseDTO;
    }

    private PaymentResponseDTO handleError(PaymentRequestDTO requestDTO, ErrorCode errorCode) throws JsonProcessingException {
        PaymentResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(String.valueOf(errorCode.status()), errorCode.code(), errorCode.message(), null, null);
        transactionLogService.saveTransactionLog(requestDTO, responseDTO, null, null);
        return responseDTO;
    }
}
