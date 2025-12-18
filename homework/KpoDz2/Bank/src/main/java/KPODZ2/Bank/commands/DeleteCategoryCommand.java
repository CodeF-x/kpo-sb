package KPODZ2.Bank.commands;

import java.util.UUID;

import KPODZ2.Bank.facade.FinanceFacade;
import KPODZ2.Bank.interfaces.TransactionCommand;

public class DeleteCategoryCommand implements TransactionCommand{
    private final UUID category;

    private final FinanceFacade facade;

    public DeleteCategoryCommand(FinanceFacade financeFacade, UUID category) {
        this.facade = financeFacade;
        this.category = category;
    }

    @Override
    public void execute() {
        facade.DeleteCategory(category);
        System.out.println("Category deleted succesfully!");
    }
}
