package natcash.business.repository;

import natcash.business.dto.response.PaymentQueryDTO;
import natcash.business.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    Payment findPaymentByTransCodeAndStatus(String transCode, String status);
    @Modifying
    @Transactional
    @Query("UPDATE Payment p SET p.status = 'SUCCESS', p.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE p.id = :id ")
    void confirmTrans(@Param("id") UUID id);

    @Query("SELECT p.id FROM Payment p WHERE p.transCode = :transCode")
    String findIdByTransCode(@Param("transCode") String transCode);

    @Query(value = """
        SELECT p.id AS id,
               p.to_account AS toAccount,
               pl.ewallet_transaction_id AS transactionId
        FROM payment p
        LEFT JOIN payment_log pl ON pl.amount = p.amount and p.trans_code = pl.transaction_content
        WHERE p.status = :status AND pl.ewallet_transaction_id IS NOT NULL
        AND pl.created_at::date BETWEEN CURRENT_DATE - INTERVAL '1 day' AND CURRENT_DATE;
    """, nativeQuery = true)
    Set<PaymentQueryDTO> findAllPaymentByStatus(@Param("status") String status);

}
