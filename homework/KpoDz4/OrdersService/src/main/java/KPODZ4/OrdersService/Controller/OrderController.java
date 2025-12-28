package KPODZ4.OrdersService.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import KPODZ4.OrdersService.Dto.OrderRequest;
import KPODZ4.OrdersService.Entity.Order;
import KPODZ4.OrdersService.Service.OrderService;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") 
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable UUID userId) {
        return orderService.getOrdersByUser(userId);
    }
    
    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable UUID orderId) {
        return orderService.getOrderById(orderId);
    }

}

