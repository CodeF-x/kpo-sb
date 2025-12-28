package KPODZ4.PaymentsService.Service;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import KPODZ4.PaymentsService.Dto.OrderEvent;
import KPODZ4.PaymentsService.Entity.ProcessedMessage;
import KPODZ4.PaymentsService.Repository.ProcessedMessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {

    private final PaymentService paymentService;
    private final ProcessedMessageRepository processedMessageRepository;

    @RabbitListener(queues = "order.created")
    @Transactional
    public void onOrderCreated(OrderEvent event, @Header(AmqpHeaders.MESSAGE_ID) String messageId) {
        if (messageId == null) {
            log.error("Сообщение без ID! Невозможно гарантировать точность списания.");
            return; 
        }

        if (processedMessageRepository.existsById(messageId)) {
            log.warn("Сообщение {} уже было обработано. Пропускаем дубликат.", messageId);
            return;
        }

        log.info("Начало обработки заказа: {}. MsgId: {}", event.getOrderId(), messageId);

        try {
            paymentService.processPayment(event);

            processedMessageRepository.save(new ProcessedMessage(messageId, LocalDateTime.now()));
            
        } catch (Exception e) {
            log.error("Ошибка при обработке платежа для заказа {}: {}", event.getOrderId(), e.getMessage());
            throw e; 
        }
    
    }
}