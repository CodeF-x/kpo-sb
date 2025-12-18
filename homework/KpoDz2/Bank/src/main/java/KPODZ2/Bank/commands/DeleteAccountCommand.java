package KPODZ2.Bank.commands;

import java.util.UUID;

import KPODZ2.Bank.facade.FinanceFacade;
import KPODZ2.Bank.interfaces.TransactionCommand;

public class DeleteAccountCommand implements TransactionCommand {
    private final UUID account;

    private final FinanceFacade facade;

    public DeleteAccountCommand(FinanceFacade financeFacade, UUID account) {
        this.facade = financeFacade;
        this.account = account;
    }

    @Override
    public void execute() {
        facade.DeleteAccount(account);
        System.out.println("Accaunt deleted succesfully!");
    }
}
