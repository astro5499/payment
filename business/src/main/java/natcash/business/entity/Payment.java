package natcash.business.entity;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "payment")
@Data
public class Payment {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "amount", nullable = false, precision = 18, scale = 6)
    private Double amount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "from_partner", nullable = false)
    private String fromPartner;

    @Column(name = "to_account", nullable = false)
    private String toAccount;

    @Column(name = "status")
    private String status;

    @Column(name = "trans_code")
    private String transCode;
}
