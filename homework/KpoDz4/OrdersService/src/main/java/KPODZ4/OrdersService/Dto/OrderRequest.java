package KPODZ4.OrdersService.Dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderRequest {
    private UUID userId;
    private BigDecimal amount;
    private String description;
}