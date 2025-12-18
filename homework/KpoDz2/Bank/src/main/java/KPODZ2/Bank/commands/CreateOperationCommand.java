package KPODZ2.Bank.commands;

import KPODZ2.Bank.domainModels.Operation;
import KPODZ2.Bank.facade.FinanceFacade;
import KPODZ2.Bank.factory.FinanceFactory;
import KPODZ2.Bank.interfaces.TransactionCommand;
import KPODZ2.Bank.odt.OperationOdt;

public class CreateOperationCommand implements TransactionCommand {
    private final OperationOdt operationOdt;

    private final FinanceFacade facade;
    private final FinanceFactory factory;

    public CreateOperationCommand(FinanceFacade financeFacade, FinanceFactory financeFactory,
            OperationOdt operationOdt) {
        this.facade = financeFacade;
        this.factory = financeFactory;
        this.operationOdt = operationOdt;
    } 

    @Override
    public void execute() {

        Operation operation = factory.createOperation(operationOdt.getAmount(), facade.findAccountById(operationOdt.getBank_account()),
                        facade.findCategoryById(operationOdt.getCategory()), operationOdt.getType(), operationOdt.getDescription());

        facade.saveOperation(operation);

        System.out.println("Operation created succesfully!");
    }
}