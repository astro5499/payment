package natcash.business.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_log")
@Data
public class WalletPaymentLog {
    @Id
    @Column(name = "ewallet_transaction_id", nullable = false)
    private Long ewalletTransactionId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transaction_content", nullable = false)
    private String transactionContent;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "from_phone", length = 11)
    private String fromPhone;

    @Column(name = "to_phone", length = 11)
    private String toPhone;

}
