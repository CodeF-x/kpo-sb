package KPODZ4.PaymentsService.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "payment_outbox")
@Data
@NoArgsConstructor
public class PaymentOutbox {
    @Id
    private UUID id = UUID.randomUUID();
    private String eventType;
    private String payload;
    private LocalDateTime createdAt = LocalDateTime.now();

    public PaymentOutbox(String eventType, String payload) {
        this.eventType = eventType;
        this.payload = payload;
    }
}