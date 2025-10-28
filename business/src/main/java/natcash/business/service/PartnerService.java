package natcash.business.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import natcash.business.dto.request.PaymentRequestDTO;
import natcash.business.dto.response.PaymentResponseDTO;
import natcash.business.dto.response.RequestResponseDTO;
import natcash.business.entity.Partner;

import java.util.UUID;

public interface PartnerService {
    Partner findByUsername(String username);

    PaymentResponseDTO initPayment(PaymentRequestDTO requestDTO, String clientIp) throws Exception;

    RequestResponseDTO confirmPayment(String orderId) throws JsonProcessingException;

    RequestResponseDTO expiredPayment(UUID paymentId) throws JsonProcessingException;

}
