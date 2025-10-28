package natcash.business.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import natcash.business.dto.request.PaymentRequestDTO;
import natcash.business.dto.response.PaymentResponseDTO;
import natcash.business.dto.response.RequestResponseDTO;
import natcash.business.entity.TransactionLog;

import java.util.UUID;

public interface TransactionLogService {

    TransactionLog saveTransactionLog(PaymentRequestDTO request, PaymentResponseDTO response, UUID paymentId) throws JsonProcessingException;
    TransactionLog saveConfirmTransactionLog(RequestResponseDTO response, String orderId) throws JsonProcessingException;

    TransactionLog saveExpiredTransactionLog(RequestResponseDTO response, UUID paymentId) throws JsonProcessingException;

    boolean isExistsByRequestIdOrOrderId(String requestId, String orderId);
}
