package KPODZ2.Bank.domainModels;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class BankAccount {
    private UUID Id;
    private String name;
    private BigDecimal balance;

    public BankAccount(UUID id, String name, BigDecimal balance){
        this.Id = id;
        this.name = name;
        this.balance = balance;
    }
}
