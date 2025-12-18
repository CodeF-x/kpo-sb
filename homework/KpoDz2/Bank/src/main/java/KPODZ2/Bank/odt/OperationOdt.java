package KPODZ2.Bank.odt;

import java.math.BigDecimal;
import java.util.UUID;

import KPODZ2.Bank.models.OperationType;

import lombok.Data;
 
@Data
public class OperationOdt {
    private UUID Id;
    private OperationType type;
    private UUID bank_account;
    private BigDecimal amount;
    private String description;
    private UUID category;

    public OperationOdt(){}
    
    public OperationOdt(UUID Id, OperationType type, UUID bank_account, BigDecimal amount,
            UUID category, String description) {
        this.Id = Id;
        this.type = type;
        this.bank_account = bank_account;
        this.amount = amount;
        this.description = description;
        this.category = category;
    }
}
