package KPODZ4.OrdersService.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import KPODZ4.OrdersService.Entity.OrderStatus;
import KPODZ4.OrdersService.Entity.ProcessedMessage;
import KPODZ4.OrdersService.Repository.OrderRepository;
import KPODZ4.OrdersService.Repository.ProcessedMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentResultListener {

    private final OrderRepository orderRepository;
    private final ProcessedMessageRepository processedMessageRepository;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "payment.updates")
    @Transactional
    @SneakyThrows
    public void handlePaymentUpdate(Message message) {
        String msgId = message.getMessageProperties().getMessageId();
        
        if (msgId == null) {
            log.warn("Сообщение получено без MessageID. Пропускаем проверку Inbox.");
        } else if (processedMessageRepository.existsById(msgId)) {
            return; 
        }

        String body = new String(message.getBody());
        JsonNode json = objectMapper.readTree(body);
        
        if (!json.has("orderId") || json.get("orderId").isNull()) {
            log.error("В теле сообщения отсутствует orderId: {}", body);
            return; 
        }

        UUID orderId = UUID.fromString(json.get("orderId").asText());
        String status = json.get("status").asText();

        orderRepository.findById(orderId).ifPresent(order -> {
            if ("PAID".equals(status)) {
                order.setStatus(OrderStatus.PAID);
            } else {
                order.setStatus(OrderStatus.FAILED);
            }
            orderRepository.save(order);
        });

        processedMessageRepository.save(new ProcessedMessage(msgId, LocalDateTime.now()));
    }
}