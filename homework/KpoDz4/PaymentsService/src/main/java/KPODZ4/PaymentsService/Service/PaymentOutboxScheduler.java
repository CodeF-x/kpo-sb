package KPODZ4.PaymentsService.Service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import KPODZ4.PaymentsService.Entity.PaymentOutbox;
import KPODZ4.PaymentsService.Repository.PaymentOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentOutboxScheduler {
    private final PaymentOutboxRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void processOutbox() {
        List<PaymentOutbox> events = outboxRepository.findAll();
        for (PaymentOutbox event : events) {
            try {
                Object jsonNode = objectMapper.readTree(event.getPayload());

                rabbitTemplate.convertAndSend("payment.updates", jsonNode, message -> {
                    message.getMessageProperties().setMessageId(java.util.UUID.randomUUID().toString());
                    return message;
                });

                log.info("Сообщение для заказа успешно отправлено с новым MessageID");

                outboxRepository.delete(event);

            } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                log.error("Ошибка парсинга Outbox: {}", e.getMessage());
            } catch (Exception e) {
                log.error("Ошибка при отправке сообщения: {}", e.getMessage());
            }
        }
    }
}