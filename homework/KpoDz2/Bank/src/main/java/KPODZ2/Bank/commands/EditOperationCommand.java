package KPODZ2.Bank.commands;

import KPODZ2.Bank.domainModels.BankAccount;
import KPODZ2.Bank.domainModels.Operation;
import KPODZ2.Bank.facade.FinanceFacade;
import KPODZ2.Bank.interfaces.TransactionCommand;
import KPODZ2.Bank.odt.OperationOdt;

public class EditOperationCommand implements TransactionCommand {
    private final OperationOdt operationOdt;

    private final FinanceFacade facade;

    public EditOperationCommand(FinanceFacade financeFacade, OperationOdt operationOdt) {
        this.facade = financeFacade;
        this.operationOdt = operationOdt;
    }

    @Override
    public void execute() {
        Operation oldOperation = facade.findOperationById(operationOdt.getId());
        BankAccount account = oldOperation.getBank_account();
        facade.undoOperation(account, oldOperation);
        oldOperation.setCategory(facade.findCategoryById(operationOdt.getCategory()));
        oldOperation.setAmount(operationOdt.getAmount());
        oldOperation.setDescription(operationOdt.getDescription());
        oldOperation.setType(operationOdt.getType());
        facade.doOperation(account, oldOperation);
        System.out.println("Operation edited succesfully!");
    }
}
