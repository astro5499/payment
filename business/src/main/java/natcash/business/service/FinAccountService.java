package natcash.business.service;

import natcash.business.entity.FinAccount;
import natcash.business.entity.Payment;

import java.util.List;

public interface FinAccountService {
    FinAccount findAvailableFinAccount(Double amount);
    FinAccount findFinAccountById(String accountId);

    void updateFinAccountBalance(Payment payment);

    List<String> findAllFinAccountsByPendingPayment();
}
