package natcash.business.repository;

import natcash.business.entity.TransactionLog;
import natcash.business.utils.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
    Optional<TransactionLog> findByRequestId(String requestId);
    TransactionLog findFirstByOrderIdOrderByCreatedAtDesc(String orderId);
    TransactionLog findFirstByPaymentIdOrderByCreatedAtDesc(UUID paymentId);
    boolean existsByRequestIdAndOrderIdAndStatus(String requestId, String orderId, String status);

}