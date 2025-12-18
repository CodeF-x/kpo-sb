package KPODZ2.Bank.commands;

import java.util.UUID;

import KPODZ2.Bank.facade.FinanceFacade;
import KPODZ2.Bank.interfaces.TransactionCommand;

public class DeleteOperationCommand implements TransactionCommand {
    private final UUID operation;

    private final FinanceFacade facade;

    public DeleteOperationCommand(FinanceFacade financeFacade, UUID operation) {
        this.facade = financeFacade;
        this.operation = operation;
    }

    @Override
    public void execute() {
        facade.DeleteOperation(operation);
        System.out.println("Operation deleted succesfully!");
    }
}
