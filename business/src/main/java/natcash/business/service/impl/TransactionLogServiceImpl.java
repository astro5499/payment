package natcash.business.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import natcash.business.dto.request.PaymentRequestDTO;
import natcash.business.dto.response.PaymentResponseDTO;
import natcash.business.dto.response.RequestResponseDTO;
import natcash.business.entity.TransactionLog;
import natcash.business.repository.TransactionLogRepository;
import natcash.business.service.TransactionLogService;
import natcash.business.utils.ErrorCode;
import natcash.business.utils.TransactionAction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionLogServiceImpl implements TransactionLogService {

    private final TransactionLogRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public TransactionLog saveTransactionLog(PaymentRequestDTO request, PaymentResponseDTO response, UUID paymentId) throws JsonProcessingException {
        TransactionLog log = new TransactionLog();
        log.setRequestId(request.getRequestId());
        log.setOrderId(request.getOrderNumber());
        log.setUsername(request.getUsername());
        log.setAmount(request.getAmount());
        log.setTimestamp(request.getTimestamp());
        log.setSignature(request.getSignature());
        log.setCallbackUrl(request.getCallbackUrl());
        log.setStatus(response.getStatus());
        log.setErrorDesc(ErrorCode.SUCCESS.code().equals(response.getCode()) ? null : response.getMessage());
        log.setRequestPayload(objectMapper.writeValueAsString(request));
        log.setResponsePayload(objectMapper.writeValueAsString(response));
        log.setCreatedAt(LocalDateTime.now());
        log.setUpdatedAt(LocalDateTime.now());
        log.setPaymentId(paymentId);
        log.setAction(TransactionAction.INIT.getValue());

        return repository.save(log);
    }

    @Override
    public TransactionLog saveConfirmTransactionLog(RequestResponseDTO response, String orderId) throws JsonProcessingException {
        TransactionLog log;
        if (ErrorCode.ERR_PAYMENT_NOT_FOUND.code().equalsIgnoreCase(response.getCode())) {
            log = new TransactionLog();
            log.setOrderId(orderId);
            log.setCreatedAt(LocalDateTime.now());
        } else {
            log = repository.findFirstByOrderIdOrderByCreatedAtDesc(orderId);
            log.setId(null);
            log.setCreatedAt(LocalDateTime.now());
        }

        log.setStatus(response.getStatus());
        log.setErrorDesc(ErrorCode.SUCCESS.code().equals(response.getCode()) ? null : response.getMessage());
        log.setRequestPayload(orderId);
        log.setResponsePayload(objectMapper.writeValueAsString(response));
        log.setUpdatedAt(LocalDateTime.now());
        log.setAction(TransactionAction.CONFIRM.getValue());

        return repository.save(log);
    }

    @Override
    public TransactionLog saveExpiredTransactionLog(RequestResponseDTO response, UUID paymentId) throws JsonProcessingException {
        TransactionLog log;
        if (ErrorCode.ERR_PAYMENT_NOT_FOUND.code().equalsIgnoreCase(response.getCode())) {
            log = new TransactionLog();
            log.setPaymentId(paymentId);
            log.setCreatedAt(LocalDateTime.now());
        }

        log = repository.findFirstByPaymentIdOrderByCreatedAtDesc(paymentId);
        log.setId(null);
        log.setCreatedAt(LocalDateTime.now());
        log.setStatus(response.getStatus());
        log.setErrorDesc(ErrorCode.SUCCESS.code().equals(response.getCode()) ? null : response.getMessage());
        log.setRequestPayload(paymentId.toString());
        log.setResponsePayload(objectMapper.writeValueAsString(response));
        log.setUpdatedAt(LocalDateTime.now());
        log.setAction(TransactionAction.EXPIRED.getValue());

        return repository.save(log);
    }

    @Override
    public boolean isExistsByRequestIdOrOrderId(String requestId, String orderId) {
        return repository.existsByRequestIdOrOrderId(requestId, orderId);
    }
}
