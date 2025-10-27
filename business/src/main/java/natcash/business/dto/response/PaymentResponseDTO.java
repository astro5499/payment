package natcash.business.dto.response;

import lombok.Data;

@Data
public class PaymentResponseDTO {

    private String status;

    private String code;

    private String message;

    private String url;

    private Integer expiredAt;

}