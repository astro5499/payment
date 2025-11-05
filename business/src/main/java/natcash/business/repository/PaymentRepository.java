package natcash.business.repository;

import natcash.business.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    Payment findPaymentByOrderId(String orderId);
    @Modifying
    @Transactional
    @Query("UPDATE Payment p SET p.status = 'SUCCESS', p.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE p.transCode = :transCode AND p.amount = :amount AND p.toAccount = :toAccount AND p.status = 'PENDING' ")
    int confirmTrans(@Param("transCode") String transCode,@Param("amount") Double amount, @Param("toAccount") String toAccount);

    @Query("SELECT p.id FROM Payment p WHERE p.transCode = :transCode")
    String findIdByTransCode(@Param("transCode") String transCode);

}
