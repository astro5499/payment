package natcash.business.restful.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import natcash.business.entity.Partner;
import natcash.business.entity.TransactionLog;
import natcash.business.repository.PartnerRepository;
import natcash.business.repository.TransactionLogRepository;
import natcash.business.restful.models.InitPayment;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/partners")
@RequiredArgsConstructor
public class PartnerController {

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @PostMapping("/init")
    public ResponseEntity<?> initPayment(@RequestBody InitPayment request) {
        TransactionLog log = new TransactionLog();
        log.setRequestId(request.getRequestId());
        log.setOrderId(request.getOrderId());
        log.setUsername(request.getUsername());
        log.setAmount(request.getAmount());
        log.setTimestamp(request.getTimestamp());
        log.setSignature(request.getSignature());
        log.setCallbackUrl(request.getCallbackUrl());
        log.setStatus("INIT");

        String responseMessage = "";
        HttpStatus responseStatus = HttpStatus.OK;

        try {
            // 1. Check partner
            Partner partner = partnerRepository.findByUsername(request.getUsername());
            if (partner == null) {
                responseMessage = "Invalid partner username";
                responseStatus = HttpStatus.BAD_REQUEST;
                log.setStatus("FAILED");
                log.setErrorDesc(responseMessage);
                return ResponseEntity.status(responseStatus).body(responseMessage);
            }

            // 2. Verify signature
            String dataToSign = request.getRequestId() + request.getUsername() +
                                request.getAmount() + request.getTimestamp();
//            String expectedSig = hashSHA256(dataToSign + partner.getPassword());
//            if (!expectedSig.equals(request.getSignature())) {
//                responseMessage = "Invalid signature";
//                responseStatus = HttpStatus.BAD_REQUEST;
//                log.setStatus("FAILED");
//                log.setErrorDesc(responseMessage);
//                return ResponseEntity.status(responseStatus).body(responseMessage);
//            }

            // 3. Check duplicate requestId
            if (transactionLogRepository.findByRequestId(request.getRequestId()).isPresent()) {
                responseMessage = "Duplicate requestId";
                responseStatus = HttpStatus.BAD_REQUEST;
                log.setStatus("FAILED");
                log.setErrorDesc(responseMessage);
                return ResponseEntity.status(responseStatus).body(responseMessage);
            }

            // 4. Check duplicate orderId
            if (transactionLogRepository.findByOrderId(request.getOrderId()).isPresent()) {
                responseMessage = "Duplicate orderId";
                responseStatus = HttpStatus.BAD_REQUEST;
                log.setStatus("FAILED");
                log.setErrorDesc(responseMessage);
                return ResponseEntity.status(responseStatus).body(responseMessage);
            }

            // 5. Nếu không lỗi thì ghi log thành công
            log.setStatus("SUCCESS");
            responseMessage = "Transaction initialized successfully";
            return ResponseEntity.ok(responseMessage);
        } catch (Exception e) {
            responseMessage = "Exception: " + e.getMessage();
            responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            log.setStatus("ERROR");
            log.setErrorDesc(e.getMessage());
            return ResponseEntity.status(responseStatus).body(responseMessage);
        } finally {
            try {
                // Ghi log chắc chắn, kể cả khi lỗi
                if (log.getCreatedAt() == null) {
                    //log.setCreatedAt(new java.util.Date());
                }
                if (!transactionLogRepository.findByRequestId(log.getRequestId()).isPresent()) {
                    transactionLogRepository.save(log);
                }
            } catch (Exception ex) {
                // Nếu log cũng lỗi thì ghi ra console
                System.err.println("❌ Failed to save transaction log: " + ex.getMessage());
            }
        }
    }
}
