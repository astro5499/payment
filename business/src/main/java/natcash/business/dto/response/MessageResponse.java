package natcash.business.dto.response;

import lombok.Data;

@Data
public class MessageResponse {
    private String status;
    private String callbackUrl;
}
