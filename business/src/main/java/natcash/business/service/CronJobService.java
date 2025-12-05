package natcash.business.service;

public interface CronJobService {

    void getPayments();

    void autoConfirmPayment();
}
