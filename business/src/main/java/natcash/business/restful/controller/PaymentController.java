package natcash.business.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import natcash.business.service.PaymentService;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@PostMapping("/init-payment")
	public ResponseEntity<Map<String, String>> initPayment(@RequestBody Map<String, Object> request) {
		Double amount = Double.valueOf(request.get("amount").toString());
		String orderId = "test";
		Map<String, String> response = new HashMap<>();
		response.put("orderId", orderId);
		response.put("webViewUrl", "http://localhost:3000/payment?orderId=" + orderId);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/confirm/{orderId}")
	public ResponseEntity<String> confirmPayment(@PathVariable String orderId) {
	    paymentService.updateTransaction(orderId, "SUCCESS");

	    // Push realtime sang client
	    messagingTemplate.convertAndSend("/topic/payment-status-" + orderId, "SUCCESS");

	    return ResponseEntity.ok("Payment confirmed for " + orderId);
	}
}