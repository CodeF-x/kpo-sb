package KPODZ2.Bank.commands;

import KPODZ2.Bank.domainModels.BankAccount;
import KPODZ2.Bank.facade.FinanceFacade;
import KPODZ2.Bank.interfaces.TransactionCommand;
import KPODZ2.Bank.odt.BankAccountOdt;

public class EditAccountCommand implements TransactionCommand{
    private final BankAccountOdt bankAccountOdt;

    private final FinanceFacade facade;

    public EditAccountCommand(FinanceFacade financeFacade, BankAccountOdt bankAccountOdt) {
        this.facade = financeFacade;
        this.bankAccountOdt = bankAccountOdt;
    }

    @Override
    public void execute() {
        BankAccount oldAccount = facade.findAccountById(bankAccountOdt.getId());
        oldAccount.setBalance(bankAccountOdt.getBalance());
        oldAccount.setName(bankAccountOdt.getName());
        System.out.println("Account edited succesfully!");
    }
}
