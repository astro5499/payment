package natcash.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import natcash.business.entity.TransactionLog;

import java.util.Optional;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
    Optional<TransactionLog> findByRequestId(String requestId);
    Optional<TransactionLog> findByOrderId(String orderId);
}