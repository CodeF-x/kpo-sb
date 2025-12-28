package KPODZ4.OrdersService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import KPODZ4.OrdersService.Entity.Order;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(UUID userId);
}