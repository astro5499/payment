package natcash.business.repository;


import natcash.business.entity.FinAccount;
import natcash.business.entity.WalletPaymentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletPaymentLogRepository extends JpaRepository<WalletPaymentLog, Long> {

    @Query(value = "SELECT MAX(pl.ewallet_transaction_id) FROM payment_log pl WHERE pl.to_phone = :accountId", nativeQuery = true)
    Long findMaxTransactionIdByFinAccount(@Param("accountId") String accountId);
}

