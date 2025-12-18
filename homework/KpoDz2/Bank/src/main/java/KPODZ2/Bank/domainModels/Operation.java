package KPODZ2.Bank.domainModels;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import KPODZ2.Bank.models.OperationType;

import lombok.Data;

@Data
public class Operation {
    private UUID Id;
    private OperationType type;
    private BankAccount bank_account;
    private BigDecimal amount;
    private LocalDateTime date;
    private String description;
    private Category category;

    public Operation(UUID Id, OperationType type, BankAccount bank_account, BigDecimal amount, LocalDateTime date,
            Category category, String description) {
        this.Id = Id;
        this.type = type;
        this.bank_account = bank_account;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

}
