package KPODZ4.PaymentsService.Entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "accounts")
@Data
public class Account {
    @Id
    private UUID userId;
    private BigDecimal balance;
}