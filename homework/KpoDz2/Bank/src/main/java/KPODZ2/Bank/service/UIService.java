package KPODZ2.Bank.service;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.stereotype.Service;

import KPODZ2.Bank.commands.CreateAccountCommand;
import KPODZ2.Bank.commands.CreateCategoryCommand;
import KPODZ2.Bank.commands.CreateOperationCommand;
import KPODZ2.Bank.commands.DeleteAccountCommand;
import KPODZ2.Bank.commands.DeleteCategoryCommand;
import KPODZ2.Bank.commands.DeleteOperationCommand;
import KPODZ2.Bank.commands.EditAccountCommand;
import KPODZ2.Bank.commands.EditCategoryCommand;
import KPODZ2.Bank.commands.EditOperationCommand;
import KPODZ2.Bank.decorator.TimingDecorator;
import KPODZ2.Bank.facade.FinanceFacade;
import KPODZ2.Bank.factory.FinanceFactory;
import KPODZ2.Bank.interfaces.TransactionCommand;
import KPODZ2.Bank.models.OperationType;
import KPODZ2.Bank.odt.BankAccountOdt;
import KPODZ2.Bank.odt.CategoryOdt;
import KPODZ2.Bank.odt.OperationOdt;

@Service
public class UIService {

    private final FinanceFacade facade;
    private final FinanceFactory factory;

    public UIService(FinanceFacade facade, FinanceFactory factory) {
        this.facade = facade;
        this.factory = factory;
    }

    private void ShowMenu() {
        System.out.println("-----------------Menu-----------------");
        System.out.println("Write 1 to create account");
        System.out.println("Write 2 to edit account");
        System.out.println("Write 3 to delete account");
        System.out.println("Write 4 to create category");
        System.out.println("Write 5 to edit category");
        System.out.println("Write 6 to delete category");
        System.out.println("Write 7 to create operation");
        System.out.println("Write 8 to edit operation");
        System.out.println("Write 9 to delete operation");
        System.out.println("Write 10 to show accounts");
        System.out.println("Write 11 to show categories");
        System.out.println("Write 12 to show operations");
        System.out.println("Write 0 to exit");
        System.out.println("--------------------------------------");
        return;
    }

    private void AddAccount(Scanner scanner) {
        System.out.println("-----------------Creating account-----------------");
        System.out.println("Write name: ");
        String name = scanner.nextLine();
        System.out.println("Write balance: ");
        String balance = scanner.nextLine();
        BigDecimal userBalance = new BigDecimal(balance);
        BankAccountOdt odt = new BankAccountOdt();
        odt.setBalance(userBalance);
        odt.setName(name);
        TransactionCommand command = new CreateAccountCommand(facade, factory, odt);
        TransactionCommand timedCommand = new TimingDecorator(command);
        timedCommand.execute();
    }

    private void EditAccount(Scanner scanner) {
        System.out.println("-----------------Editing account-----------------");
        System.out.println("Write id: ");
        UUID accId = UUID.fromString(scanner.nextLine());
        System.out.println("Write new name: ");
        String name = scanner.nextLine();
        System.out.println("Write new balance: ");
        String balance = scanner.nextLine();
        BigDecimal userBalance = new BigDecimal(balance);
        BankAccountOdt odt = new BankAccountOdt();
        odt.setBalance(userBalance);
        odt.setName(name);
        odt.setId(accId);
        TransactionCommand command = new EditAccountCommand(facade, odt);
        TransactionCommand timedCommand = new TimingDecorator(command);
        timedCommand.execute();
    }

    private void DeleteAccount(Scanner scanner) {
        System.out.println("-----------------Deleting account-----------------");
        System.out.println("Write id: ");
        UUID accId = UUID.fromString(scanner.nextLine());
        TransactionCommand command = new DeleteAccountCommand(facade, accId);
        TransactionCommand timedCommand = new TimingDecorator(command);
        timedCommand.execute();
    }

    private void AddCategory(Scanner scanner) {
        System.out.println("-----------------Creating category-----------------");
        System.out.println("Write name: ");
        String name = scanner.nextLine();
        System.out.println("Write type 0 for income, or 1 for expense: ");
        boolean tupeB = (Integer.parseInt(scanner.nextLine()) != 0);
        OperationType type;
        if (tupeB) {
            type = OperationType.Expense;
        } else {
            type = OperationType.Income;
        }
        CategoryOdt odt = new CategoryOdt();
        odt.setName(name);
        odt.setType(type);
        TransactionCommand command = new CreateCategoryCommand(facade, factory, odt);
        TransactionCommand timedCommand = new TimingDecorator(command);
        timedCommand.execute();
    }

    private void EditCategory(Scanner scanner) {
        System.out.println("-----------------Editing category-----------------");
        System.out.println("Write id: ");
        UUID Id = UUID.fromString(scanner.nextLine());
        System.out.println("Write new name: ");
        String name = scanner.nextLine();
        System.out.println("Write type 0 for income, or 1 for expense: ");
        boolean tupeB = (Integer.parseInt(scanner.nextLine()) != 0);
        OperationType type;
        if (tupeB) {
            type = OperationType.Expense;
        } else {
            type = OperationType.Income;
        }
        CategoryOdt odt = new CategoryOdt();
        odt.setId(Id);
        odt.setName(name);
        odt.setType(type);
        TransactionCommand command = new EditCategoryCommand(facade, odt);
        TransactionCommand timedCommand = new TimingDecorator(command);
        timedCommand.execute();
    }

    private void DeleteCategory(Scanner scanner) {
        System.out.println("-----------------Deleting category-----------------");
        System.out.println("Write id: ");
        UUID accId = UUID.fromString(scanner.nextLine());
        TransactionCommand command = new DeleteCategoryCommand(facade, accId);
        TransactionCommand timedCommand = new TimingDecorator(command);
        timedCommand.execute();
    }

    private void AddOperstion(Scanner scanner) {
        System.out.println("-----------------Creating operation-----------------");
        System.out.println("Write amount: ");
        String amount = scanner.nextLine();
        BigDecimal operationAmount = new BigDecimal(amount);
        System.out.println("Write type 0 for income, or 1 for expense: ");
        boolean tupeB = (Integer.parseInt(scanner.nextLine()) != 0);
        OperationType type;
        if (tupeB) {
            type = OperationType.Expense;
        } else {
            type = OperationType.Income;
        }
        System.out.println("Write account id: ");
        UUID accId = UUID.fromString(scanner.nextLine());
        System.out.println("Write category id: ");
        UUID catId = UUID.fromString(scanner.nextLine());
        System.out.println("Write description: ");
        String discr = scanner.nextLine();
        OperationOdt odt = new OperationOdt();
        odt.setAmount(operationAmount);
        odt.setBank_account(accId);
        odt.setCategory(catId);
        odt.setDescription(discr);
        odt.setType(type);
        TransactionCommand command = new CreateOperationCommand(facade, factory, odt);
        TransactionCommand timedCommand = new TimingDecorator(command);
        timedCommand.execute();
    }

    private void EditOperstion(Scanner scanner) {
        System.out.println("-----------------Editing operation-----------------");
        System.out.println("Write id: ");
        UUID Id = UUID.fromString(scanner.nextLine());
        System.out.println("Write new amount: ");
        String amount = scanner.nextLine();
        BigDecimal operationAmount = new BigDecimal(amount);
        System.out.println("Write type 0 for income, or 1 for expense: ");
        boolean tupeB = (Integer.parseInt(scanner.nextLine()) != 0);
        OperationType type;
        if (tupeB) {
            type = OperationType.Expense;
        } else {
            type = OperationType.Income;
        }
        System.out.println("Write new category id: ");
        UUID catId = UUID.fromString(scanner.nextLine());
        System.out.println("Write new description: ");
        String discr = scanner.nextLine();
        OperationOdt odt = new OperationOdt();
        odt.setId(Id);
        odt.setAmount(operationAmount);
        odt.setCategory(catId);
        odt.setDescription(discr);
        odt.setType(type);
        TransactionCommand command = new EditOperationCommand(facade, odt);
        TransactionCommand timedCommand = new TimingDecorator(command);
        timedCommand.execute();
    }

    private void DeleteOperstion(Scanner scanner) {
        System.out.println("-----------------Deleting operation-----------------");
        System.out.println("Write id: ");
        UUID accId = UUID.fromString(scanner.nextLine());
        TransactionCommand command = new DeleteOperationCommand(facade, accId);
        TransactionCommand timedCommand = new TimingDecorator(command);
        timedCommand.execute();
    }

    private void ShowAccounts() {
        System.out.println("-----------------Accaunts-----------------");
        facade.ShowAccounts();
    }

    private void ShowCategories() {
        System.out.println("-----------------Categories-----------------");
        facade.ShowCategories();
    }

    private void ShowOperations() {
        System.out.println("-----------------Operations-----------------");
        facade.ShowOperations();
    }

    public void run(String... args) {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        ShowMenu();
        while (running) {
            try {
                String input = scanner.nextLine();
                switch (input) {
                    case "" -> ShowMenu();
                    case "1" -> AddAccount(scanner);
                    case "2" -> EditAccount(scanner);
                    case "3" -> DeleteAccount(scanner);
                    case "4" -> AddCategory(scanner);
                    case "5" -> EditCategory(scanner);
                    case "6" -> DeleteCategory(scanner);
                    case "7" -> AddOperstion(scanner);
                    case "8" -> EditOperstion(scanner);
                    case "9" -> DeleteOperstion(scanner);
                    case "10" -> ShowAccounts();
                    case "11" -> ShowCategories();
                    case "12" -> ShowOperations();
                    case "0" -> {
                        System.out.println("Ending process...");
                        running = false;
                    }
                    default -> System.out.println("Error: write number 0 to 7");
                }
            } catch (Exception e) {
                System.out.println("ERROR: wrong input");

            }
        }
        return;
    }
}
