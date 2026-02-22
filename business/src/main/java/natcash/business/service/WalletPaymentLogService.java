package natcash.business.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import natcash.business.dto.request.ConfirmPaymentRequestDTO;
import natcash.business.dto.request.WalletTransactionRequest;
import natcash.business.dto.response.PaymentQueryDTO;
import natcash.business.dto.response.RequestResponseDTO;
import natcash.business.entity.WalletPaymentLog;

import java.util.List;
import java.util.Set;

public interface WalletPaymentLogService {
    void save(WalletPaymentLog paymentLog);

    void saveAll(List<WalletPaymentLog> paymentLogs);

    RequestResponseDTO confirmPayment(WalletTransactionRequest paymentLog) throws Exception;

    Long getMaxIdByFinAccount(String accountId);

    void autoConfirmPayment(Set<PaymentQueryDTO> payments) throws JsonProcessingException;

    void confirmPayment(ConfirmPaymentRequestDTO requestDTO) throws JsonProcessingException;

}
