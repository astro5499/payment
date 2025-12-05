package natcash.business.service;

public interface MessageSenderService {
    void sendMessage(String topic, Object response);
}
