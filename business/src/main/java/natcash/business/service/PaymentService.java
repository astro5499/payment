package natcash.business.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

@Service
public class PaymentService {

    private final Map<String, String> orderStatus = new ConcurrentHashMap<>();

    public String createTransaction(Double amount) {
        String orderId = "ORD-" + System.currentTimeMillis();
        orderStatus.put(orderId, "PENDING");
        return orderId;
    }

    public void updateTransaction(String orderId, String status) {
        orderStatus.put(orderId, status);
    }

    public String checkPaymentStatus(String orderId) {
        return orderStatus.getOrDefault(orderId, "NOT_FOUND");
    }
}