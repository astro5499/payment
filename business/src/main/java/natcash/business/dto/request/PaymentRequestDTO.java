package natcash.business.dto.request;

import lombok.Data;

@Data
public class PaymentRequestDTO {

    private String requestId;

    private String partnerCode;

    private String username;

    private String password;

    private String callbackUrl;

    private Long timestamp;

    private String orderNumber;

    private Double amount;

    private String signature;
}
