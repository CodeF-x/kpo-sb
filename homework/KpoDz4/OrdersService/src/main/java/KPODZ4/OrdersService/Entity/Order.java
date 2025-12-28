package KPODZ4.OrdersService.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String description;

    private LocalDateTime createdAt;

    @PrePersist
    public void init() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }
}