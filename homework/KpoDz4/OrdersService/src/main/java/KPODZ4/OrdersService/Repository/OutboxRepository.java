package KPODZ4.OrdersService.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import KPODZ4.OrdersService.Entity.Outbox;

public interface OutboxRepository extends JpaRepository<Outbox, UUID> {
    List<Outbox> findTop50ByOrderByCreatedAtAsc();
}