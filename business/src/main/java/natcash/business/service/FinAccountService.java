package natcash.business.service;

import natcash.business.entity.FinAccount;
import natcash.business.entity.Payment;

public interface FinAccountService {
    FinAccount findAvailableFinAccount(Double amount);
    FinAccount findFinAccountById(String accountId);

    void updateFinAccountBalance(Payment payment);
}
