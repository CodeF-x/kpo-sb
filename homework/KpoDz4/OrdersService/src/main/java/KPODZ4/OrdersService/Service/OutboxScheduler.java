package KPODZ4.OrdersService.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import KPODZ4.OrdersService.Entity.Outbox;
import KPODZ4.OrdersService.Repository.OutboxRepository;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OutboxScheduler {

    private final OutboxRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void processOutbox() {
        List<Outbox> events = outboxRepository.findTop50ByOrderByCreatedAtAsc();

        for (Outbox event : events) {
            try {
                Object payloadObject = objectMapper.readValue(event.getPayload(), Object.class);
                                rabbitTemplate.convertAndSend("orders-exchange", "order.created", payloadObject, message -> {
                message.getMessageProperties().setMessageId(UUID.randomUUID().toString());
                return message;
                });
                
                outboxRepository.delete(event);
                
                
            } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                System.err.println("Ошибка парсинга JSON для события " + event.getId() + ": " + e.getMessage());
            }
        }
    }
}