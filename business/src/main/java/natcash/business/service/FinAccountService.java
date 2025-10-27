package natcash.business.service;

import natcash.business.entity.FinAccount;

public interface FinAccountService {
    FinAccount findAvailableFinAccount(Double amount);
}
