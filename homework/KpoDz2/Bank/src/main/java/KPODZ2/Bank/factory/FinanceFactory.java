package KPODZ2.Bank.factory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import KPODZ2.Bank.domainModels.BankAccount;
import KPODZ2.Bank.domainModels.Category;
import KPODZ2.Bank.domainModels.Operation;
import KPODZ2.Bank.models.OperationType;

@Component 
public class FinanceFactory {
    public Operation createOperation(BigDecimal amount, BankAccount account, Category category, OperationType type, String decription) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }
        return new Operation(UUID.randomUUID(), type, account, amount, LocalDateTime.now(), category, decription);
    }

    public BankAccount createBankAccount(BigDecimal balance, String name) {
        if (balance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("balance must be > 0");
        }
        return new BankAccount(UUID.randomUUID(), name, balance);
    }

    public Category createCategory(OperationType type, String name) {
        return new Category(UUID.randomUUID(), type, name);
    }

}