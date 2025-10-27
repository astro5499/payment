package natcash.business.repository;

import natcash.business.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
    Optional<TransactionLog> findByRequestId(String requestId);
    Optional<TransactionLog> findByOrderId(String orderId);
    boolean existsByRequestIdOrOrderId(String requestId, String orderId);

}