package natcash.business.service.impl;

import lombok.RequiredArgsConstructor;
import natcash.business.entity.FinAccount;
import natcash.business.entity.Payment;
import natcash.business.repository.FinAccountRepository;
import natcash.business.service.FinAccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FinAccountServiceImpl implements FinAccountService {

    private final FinAccountRepository repository;

    @Override
    public FinAccount findAvailableFinAccount(Double amount) {
        Optional<FinAccount> availableFinAccount = repository.findAvailableFinAccount(amount);
        return availableFinAccount.orElse(null);
    }

    @Override
    public FinAccount findFinAccountById(String accountId) {
        Optional<FinAccount> availableFinAccount = repository.findById(accountId);
        return availableFinAccount.orElse(null);
    }

    @Override
    public void updateFinAccountBalance(Payment payment) {
        Optional<FinAccount> finAccountOtp = repository.findById(payment.getToAccount());
        if (finAccountOtp.isPresent()) {
            FinAccount finAccount = finAccountOtp.get();
            finAccount.setBalance(payment.getAmount() + finAccount.getBalance());

            repository.save(finAccount);
        }
    }

    @Override
    public List<String> findAllFinAccountsByPendingPayment() {
        return repository.findAllFinAccountsByPendingPayment();
    }
}
