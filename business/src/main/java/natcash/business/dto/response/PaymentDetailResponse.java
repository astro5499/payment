package natcash.business.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PaymentDetailResponse {

    private UUID paymentId;

    private String orderId;

    private String transCode;

    private Double amount;

    private LocalDateTime createdAt;

    private String accountId;

    private String qrCode;

    private Long expiredTime;

    private String status;
}
