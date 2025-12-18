package KPODZ2.Bank.commands;

import KPODZ2.Bank.domainModels.Category;
import KPODZ2.Bank.facade.FinanceFacade;
import KPODZ2.Bank.interfaces.TransactionCommand;
import KPODZ2.Bank.odt.CategoryOdt;

public class EditCategoryCommand implements TransactionCommand{
    private final CategoryOdt categoryOdt;

    private final FinanceFacade facade;

    public EditCategoryCommand(FinanceFacade financeFacade, CategoryOdt categoryOdt) {
        this.facade = financeFacade;
        this.categoryOdt = categoryOdt;
    }

    @Override
    public void execute() {
        Category oldCategory = facade.findCategoryById(categoryOdt.getId());
        oldCategory.setName(categoryOdt.getName());
        oldCategory.setType(categoryOdt.getType());
        System.out.println("Category edited succesfully!");
    }
}
