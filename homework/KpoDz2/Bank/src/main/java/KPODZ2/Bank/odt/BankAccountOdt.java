package KPODZ2.Bank.odt;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data 
public class BankAccountOdt {
    private UUID Id;
    private String name;
    private BigDecimal balance;

    public BankAccountOdt(){}

    public BankAccountOdt(UUID id, String name, BigDecimal balance){
        this.Id = id;
        this.name = name;
        this.balance = balance;
    }
}
