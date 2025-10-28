package natcash.business.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "fin_account")
public class FinAccount {

    @Id
    @Column(name = "account_id", nullable = false, length = 50)
    private String accountId;

    @Column(name = "limit_amount", nullable = false)
    private Double limitAmount;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "account_type")
    private Integer accountType;

    @Column(name = "status")
    private Integer status;

    @Column(name = "description")
    private String description;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "currency", nullable = false)
    private String currency = "HTG";

    @Column(name = "qr_code", nullable = false)
    private String qrCode;
}
