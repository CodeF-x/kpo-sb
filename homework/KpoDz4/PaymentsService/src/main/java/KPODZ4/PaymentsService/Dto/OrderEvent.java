package KPODZ4.PaymentsService.Dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderEvent {
    private UUID orderId;
    private UUID userId;
    private BigDecimal amount;
}