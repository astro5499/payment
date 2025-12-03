package natcash.business.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import natcash.business.dto.response.PaymentLogResponseDTO;
import natcash.business.dto.response.PaymentQueryDTO;
import natcash.business.entity.Payment;
import natcash.business.entity.WalletPaymentLog;
import natcash.business.service.AgentApiClient;
import natcash.business.service.CronJobService;
import natcash.business.service.FinAccountService;
import natcash.business.service.PaymentService;
import natcash.business.service.WalletPaymentLogService;
import natcash.business.utils.PaymentStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class CronJobServiceImpl implements CronJobService {

    private final AgentApiClient agentApiClient;

    private final WalletPaymentLogService paymentLogService;

    private final FinAccountService finAccountService;

    private final PaymentService paymentService;

    @Override
    @Scheduled(fixedDelay = 5000)
    public void getPayments() {
        log.info("process cronjob to get payments");
        try {
            List<String> phoneNumbers = finAccountService.findAllFinAccountsByPendingPayment();

            log.info("getPayments cronjob list accounts have pending payment");
            List<WalletPaymentLog> entities = new ArrayList<>();
            for (String phoneNumber : phoneNumbers) {
                Long maxTransactionId = paymentLogService.getMaxIdByFinAccount(phoneNumber);

                List<PaymentLogResponseDTO> payments = agentApiClient.getPayments(phoneNumber);
                log.info("getPayments cronjob list payments get from third party: {}", payments);

                if (CollectionUtils.isEmpty(payments)) {
                    break;
                }

                payments = payments.stream().filter(e -> e.getDirection().equals("1")).toList();
                log.info("getPayments cronjob maxTransactionId: {}", maxTransactionId);
                List<WalletPaymentLog> savedEntities = new ArrayList<>();
                if (Objects.nonNull(maxTransactionId)) {
                    savedEntities = payments.stream().filter(e -> Long.parseLong(e.getTransactionId()) > maxTransactionId).map(this::buildWalletPaymentLog).toList();
                    if (CollectionUtils.isEmpty(savedEntities)) {
                        break;
                    }
                } else {
                    savedEntities = payments.stream().map(this::buildWalletPaymentLog).toList();
                }
                entities.addAll(savedEntities);
            }
            log.info("getPayments cronjob entities to save into payment_log table: {}", entities);

            paymentLogService.saveAll(entities);

            log.info("getPayments cronjob finished");
        } catch (Exception e) {
            log.error("getPayments cronjob get error: ", e);
        }
    }

    @Override
    @Scheduled(fixedDelay = 5000)
    public void autoConfirmPayment() {
        log.info("autoConfirmPayment cronjob started");

        try {
            Set<PaymentQueryDTO> payments = paymentService.findAllPaymentByStatus(PaymentStatus.PENDING.getValue());

        log.info("autoConfirmPayment cronjob list payments to process: {}", payments);
        if (CollectionUtils.isEmpty(payments)) {
            log.info("autoConfirmPayment cronjob list payments empty");
            return;
        }

            paymentLogService.autoConfirmPayment(payments);
            log.info("autoConfirmPayment cronjob finished");
        } catch (Exception e) {
            log.error("autoConfirmPayment cronjob get error: ", e);
        }

    }

    private WalletPaymentLog buildWalletPaymentLog(PaymentLogResponseDTO dto) {
        WalletPaymentLog walletPaymentLog = new WalletPaymentLog();
        walletPaymentLog.setEwalletTransactionId(Long.parseLong(dto.getTransactionId()));
        walletPaymentLog.setTransactionContent(dto.getContent());
        walletPaymentLog.setFromPhone(dto.getAccountNumber());
        walletPaymentLog.setToPhone(dto.getToAccountNumber());
        walletPaymentLog.setAmount(BigDecimal.valueOf(Long.parseLong(dto.getTotalAmount())));
        walletPaymentLog.setCreated_at(dto.getCreateDate().toLocalDateTime());

        return walletPaymentLog;
    }
}
