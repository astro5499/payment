package natcash.business.service.impl;

import lombok.RequiredArgsConstructor;
import natcash.business.service.MessageSenderService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageSenderServiceImpl implements MessageSenderService {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional
    public void sendMessage(String topic, Object response) {
        messagingTemplate.convertAndSend(topic, response);
    }
}
