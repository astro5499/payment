package natcash.business.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import natcash.business.dto.request.WalletTransactionRequest;
import natcash.business.dto.response.PaymentResponseDTO;
import natcash.business.dto.response.RequestResponseDTO;
import natcash.business.entity.WalletPaymentLog;
import natcash.business.repository.PaymentRepository;
import natcash.business.repository.TransactionLogRepository;
import natcash.business.repository.WalletPaymentLogRepository;
import natcash.business.service.WalletPaymentLogService;
import natcash.business.utils.ErrorCode;
import natcash.business.utils.PaymentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class WalletPaymentLogServiceImpl implements WalletPaymentLogService {
    private final WalletPaymentLogRepository walletPaymentLogRepository;
    private final PaymentRepository paymentRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Override
    public void save(WalletPaymentLog paymentLog) {
        walletPaymentLogRepository.save(paymentLog);
    }

    @Override
    public RequestResponseDTO confirmPayment(WalletTransactionRequest request) {
        WalletPaymentLog logtrans = new WalletPaymentLog();
        logtrans.setEwalletTransactionId(Long.valueOf(request.getTransactionId()));
        logtrans.setAmount(BigDecimal.valueOf(request.getAmount().doubleValue()));
        logtrans.setTransactionContent(request.getTransactionContent());
        logtrans.setFromPhone(request.getFromPhone());
        logtrans.setToPhone(request.getToPhone());
        logtrans.setCreated_at(LocalDateTime.now());
        RequestResponseDTO responseDTO = PaymentUtils.buildPaymentResponse(ErrorCode.SUCCESS, ErrorCode.SUCCESS.message());
        walletPaymentLogRepository.save(logtrans);
        Double amount = request.getAmount().doubleValue();
        String paymentId = paymentRepository.findIdByTransCode(request.getTransactionContent());
        if(paymentRepository.confirmTrans(request.getTransactionContent(), amount, request.getToPhone()) > 0 ){
            log.info("Sending to topic: /topic/payment-status-" + paymentId);
            messagingTemplate.convertAndSend("/topic/payment-status-" + paymentId,"SUCCESS");
        }
        return responseDTO;
    }
}
