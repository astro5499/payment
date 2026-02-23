package natcash.business.restful.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import natcash.business.dto.request.ConfirmPaymentRequestDTO;
import natcash.business.dto.request.WalletTransactionRequest;
import natcash.business.dto.response.PaymentDetailResponse;
import natcash.business.dto.response.RequestResponseDTO;
import natcash.business.service.PaymentService;
import natcash.business.service.WalletPaymentLogService;
import natcash.business.utils.ErrorCode;
import natcash.business.utils.PaymentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Log4j2
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

    @Autowired
    WalletPaymentLogService walletPaymentLogService;

	@Value("${payment.isMock}")
	private boolean isMock;

	@PostMapping("/init-payment")
	public ResponseEntity<Map<String, String>> initPayment(@RequestBody Map<String, Object> request) {
		Double amount = Double.valueOf(request.get("amount").toString());
		String orderId = "test";
		Map<String, String> response = new HashMap<>();
		response.put("orderId", orderId);
		response.put("webViewUrl", "http://localhost:3000/payment?orderId=" + orderId);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/confirm")
	public ResponseEntity<Void> confirmPayment(@RequestBody ConfirmPaymentRequestDTO requestDTO) throws JsonProcessingException {
		if (isMock) {
			walletPaymentLogService.confirmPayment(requestDTO);

			return new ResponseEntity<>(HttpStatus.CREATED);
		}

		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PaymentDetailResponse> getPaymentDetails(@PathVariable String id) {
		return ResponseEntity.ok(paymentService.findPaymentById(UUID.fromString(id)));
	}

	@GetMapping
	public ResponseEntity<PaymentDetailResponse> getPaymentDetailsByPartnerCodeAndOrderId(@RequestParam(name = "partnerCode") String partnerCode, @RequestParam(name = "orderId") String orderId) {
		return ResponseEntity.ok(paymentService.findPaymentByPartnerCodeAndOrderId(partnerCode, orderId));
	}

    @PostMapping("/transaction")
    public ResponseEntity<RequestResponseDTO> walletPaymentLog(@RequestBody WalletTransactionRequest request) throws JsonProcessingException {
        try {
            RequestResponseDTO responseDTO = walletPaymentLogService.confirmPayment(request);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            log.error("Unexpected error when confirm payment: {}", e.getMessage());
            RequestResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(ErrorCode.ERR_COMMON, e.getMessage());
//            messagingTemplate.convertAndSend("/topic/payment-status-" + id, "FAILED");
            return ResponseEntity.ok(responseDTO);
        }
    }
}