package natcash.business.repository;

import natcash.business.entity.FinAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FinAccountRepository extends JpaRepository<FinAccount, String> {

    @Query(value = "SELECT * FROM fin_account fa WHERE (fa.balance + (:amount)) <= fa.limit_amount AND fa.status = 1 LIMIT 1", nativeQuery = true)
    Optional<FinAccount> findAvailableFinAccount(@Param("amount") Double amount);
}
