package natcash.business.service.impl;

import lombok.RequiredArgsConstructor;
import natcash.business.entity.FinAccount;
import natcash.business.repository.FinAccountRepository;
import natcash.business.service.FinAccountService;
import org.springframework.stereotype.Service;

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
}
