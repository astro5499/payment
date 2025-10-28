package natcash.business.utils;

import lombok.Getter;

@Getter
public enum TransactionAction {

    INIT("INIT"), CONFIRM("CONFIRM"), EXPIRED("EXPIRED");

    private final String value;

    TransactionAction(String value) {
        this.value = value;
    }
}
