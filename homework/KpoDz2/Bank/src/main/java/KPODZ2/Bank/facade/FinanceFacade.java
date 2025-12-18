package KPODZ2.Bank.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import KPODZ2.Bank.domainModels.BankAccount;
import KPODZ2.Bank.domainModels.Category;
import KPODZ2.Bank.domainModels.Operation;
import KPODZ2.Bank.models.OperationType;

@Service
public class FinanceFacade {
    private final List<BankAccount> accounts = new ArrayList<>();
    private final List<Operation> operations = new ArrayList<>();
    private final List<Category> categories = new ArrayList<>();

    public BankAccount findAccountById(UUID id) {
        return accounts.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such Account"));
    }

    public Category findCategoryById(UUID id) {
        return categories.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such Category"));
    }

    public Operation findOperationById(UUID id) {
        return operations.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such Operation"));
    }

    public void saveOperation(Operation op) {
        operations.add(op);
        BankAccount account = op.getBank_account();
        if (op.getType() == OperationType.Expense) {
            account.setBalance(account.getBalance().subtract(op.getAmount()));
        } else {
            account.setBalance(account.getBalance().add(op.getAmount()));
        }
    }

    public void undoOperation(BankAccount account, Operation op) {
        if (op.getType() == OperationType.Expense) {
            account.setBalance(account.getBalance().add(op.getAmount()));
        } else {
            account.setBalance(account.getBalance().subtract(op.getAmount()));
        }
    }

    public void doOperation(BankAccount account, Operation op) {
        if (op.getType() == OperationType.Expense) {
            account.setBalance(account.getBalance().subtract(op.getAmount()));
        } else {
            account.setBalance(account.getBalance().add(op.getAmount()));
        }
    }

    public void DeleteOperation(UUID op) {
        Operation oldOperation = findOperationById(op);
        BankAccount account = oldOperation.getBank_account();
        if (oldOperation.getType() == OperationType.Expense) {
            account.setBalance(account.getBalance().add(oldOperation.getAmount()));
        } else {
            account.setBalance(account.getBalance().subtract(oldOperation.getAmount()));
        }
        operations.remove(oldOperation);
    }

    public void SaveAccount(BankAccount account) {
        accounts.add(account);
    }

    public void DeleteAccount(UUID account) {
        operations.removeIf(op -> op.getBank_account().equals(account));
        accounts.removeIf(acc -> acc.getId().equals(account));
    }

    public void SaveCategory(Category category) {
        categories.add(category);
    }

    public void ShowAccounts() {
        for (BankAccount account : accounts) {
            System.out.println(
                    "Id: " + account.getId() + " Name: " + account.getName() + " Balance: " + account.getBalance());
        }
    }

    public void ShowCategories() {
        for (Category category : categories) {
            System.out.println(
                    "Id: " + category.getId() + " Name: " + category.getName() + " Type: " + category.getType());
        }
    }

    public void ShowOperations() {
        for (Operation oper : operations) {
            System.out.println("Id: " + oper.getId() + " Amount: " + oper.getAmount() + " Account: "
                    + oper.getBank_account().getId() + " Category: " + oper.getCategory().getId() + " Type: "
                    + oper.getType() + " Description: " + oper.getDescription());
        }
    }

    public void DeleteCategory(UUID category) {
        Category oldCategory = findCategoryById(category);
        boolean isUsed = operations.stream()
                .anyMatch(op -> op.getCategory().equals(oldCategory));
        if (isUsed) {
            throw new IllegalStateException("You cant delete category while it is in use!");
        }
        categories.removeIf(c -> c.getId().equals(category));
    }

}