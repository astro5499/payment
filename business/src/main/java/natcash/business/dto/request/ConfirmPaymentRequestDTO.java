package natcash.business.dto.request;

import lombok.Data;

@Data
public class ConfirmPaymentRequestDTO {

    private String orderId;
    private String partnerCode;
}
