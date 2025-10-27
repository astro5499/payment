package natcash.business.utils;

import lombok.Getter;

@Getter
public enum PaymentStatus {

    PENDING("PENDING"), SUCCESS("SUCCESS");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }
}
