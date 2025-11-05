package natcash.business.repository;


import natcash.business.entity.WalletPaymentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletPaymentLogRepository extends JpaRepository<WalletPaymentLog, Long> {
}

