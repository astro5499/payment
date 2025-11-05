package natcash.business.service;

import natcash.business.dto.request.WalletTransactionRequest;
import natcash.business.dto.response.RequestResponseDTO;
import natcash.business.entity.WalletPaymentLog;

public interface WalletPaymentLogService {
    void save(WalletPaymentLog paymentLog);
    RequestResponseDTO confirmPayment(WalletTransactionRequest paymentLog);
}
