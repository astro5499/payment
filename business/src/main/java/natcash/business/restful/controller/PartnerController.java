package natcash.business.restful.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import natcash.business.dto.request.PaymentRequestDTO;
import natcash.business.dto.response.PaymentResponseDTO;
import natcash.business.dto.response.RequestResponseDTO;
import natcash.business.service.PartnerService;
import natcash.business.service.TransactionLogService;
import natcash.business.utils.ErrorCode;
import natcash.business.utils.PaymentUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/partners")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;
    private final TransactionLogService transactionLogService;

    @PostMapping("/init")
    public ResponseEntity<PaymentResponseDTO> initPayment(@RequestBody PaymentRequestDTO request, HttpServletRequest httpServletRequest) throws JsonProcessingException {

        try {
            return ResponseEntity.ok(partnerService.initPayment(request, httpServletRequest.getRemoteAddr()));
        } catch (Exception e) {
            log.error("Unexpected error when init payment: {}", e.getMessage());
            PaymentResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(String.valueOf(ErrorCode.ERR_COMMON.status()), ErrorCode.ERR_COMMON.code(), e.getMessage(), null, null);
            transactionLogService.saveTransactionLog(request, responseDTO, null);
            return ResponseEntity.ok(responseDTO);
        }
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<RequestResponseDTO> confirmPayment(@PathVariable("id") String id) throws JsonProcessingException {
        try {
            return ResponseEntity.ok(partnerService.confirmPayment(id));
        } catch (Exception e) {
            log.error("Unexpected error when confirm payment: {}", e.getMessage());
            RequestResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(ErrorCode.ERR_COMMON, e.getMessage());
            transactionLogService.saveConfirmTransactionLog(responseDTO, id);
            return ResponseEntity.ok(responseDTO);
        }
    }

    @PatchMapping("/expired/{id}")
    public ResponseEntity<RequestResponseDTO> expiredPayment(@PathVariable String id) throws JsonProcessingException {
        try {
            return ResponseEntity.ok(partnerService.expiredPayment(UUID.fromString(id)));
        } catch (Exception e) {
            log.error("Unexpected error when expired payment: {}", e.getMessage());
            RequestResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(ErrorCode.ERR_COMMON, e.getMessage());
            transactionLogService.saveExpiredTransactionLog(responseDTO, UUID.fromString(id));
            return ResponseEntity.ok(responseDTO);
        }
    }
}
