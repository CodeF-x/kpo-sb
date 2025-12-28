package KPODZ4.OrdersService.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import KPODZ4.OrdersService.Dto.OrderRequest;
import KPODZ4.OrdersService.Entity.Order;
import KPODZ4.OrdersService.Entity.OrderStatus;
import KPODZ4.OrdersService.Entity.Outbox;
import KPODZ4.OrdersService.Repository.OrderRepository;
import KPODZ4.OrdersService.Repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public Order createOrder(OrderRequest request) {
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setAmount(request.getAmount());
        order.setDescription(request.getDescription());
        order.setStatus(OrderStatus.PENDING);
        Order savedOrder = orderRepository.save(order);
        Map<String, Object> eventPayload = new HashMap<>();
        eventPayload.put("orderId", savedOrder.getId());
        eventPayload.put("userId", savedOrder.getUserId());
        eventPayload.put("amount", savedOrder.getAmount());
        saveToOutbox("ORDER_CREATED", eventPayload);
        return savedOrder;
    }

    @SneakyThrows
    private void saveToOutbox(String type, Object payload) {
        String json = objectMapper.writeValueAsString(payload);
        outboxRepository.save(new Outbox(type, json));
    }
    
    public List<Order> getOrdersByUser(UUID userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }
}