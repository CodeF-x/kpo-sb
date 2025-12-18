package KPODZ2.Bank.commands;

import KPODZ2.Bank.domainModels.BankAccount;
import KPODZ2.Bank.facade.FinanceFacade;
import KPODZ2.Bank.factory.FinanceFactory;
import KPODZ2.Bank.interfaces.TransactionCommand;
import KPODZ2.Bank.odt.BankAccountOdt;

public class CreateAccountCommand implements TransactionCommand{
    private final BankAccountOdt bankAccountOdt;

    private final FinanceFacade facade;
    private final FinanceFactory factory;

    public CreateAccountCommand(FinanceFacade financeFacade, FinanceFactory financeFactory,
            BankAccountOdt bankAccountOdt) {
        this.facade = financeFacade;
        this.factory = financeFactory;
        this.bankAccountOdt = bankAccountOdt;
    } 

    @Override
    public void execute() {

        BankAccount account = factory.createBankAccount(bankAccountOdt.getBalance(), bankAccountOdt.getName());

        facade.SaveAccount(account);

        System.out.println("Account created succesfully!");
    }
}
