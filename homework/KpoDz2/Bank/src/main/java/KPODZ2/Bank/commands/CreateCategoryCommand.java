package KPODZ2.Bank.commands;

import KPODZ2.Bank.domainModels.Category;
import KPODZ2.Bank.facade.FinanceFacade;
import KPODZ2.Bank.factory.FinanceFactory;
import KPODZ2.Bank.interfaces.TransactionCommand;
import KPODZ2.Bank.odt.CategoryOdt;

public class CreateCategoryCommand implements TransactionCommand{
    private final CategoryOdt categoryOdt;

    private final FinanceFacade facade;
    private final FinanceFactory factory;

    public CreateCategoryCommand(FinanceFacade financeFacade, FinanceFactory financeFactory,
            CategoryOdt categoryOdt) {
        this.facade = financeFacade;
        this.factory = financeFactory;
        this.categoryOdt = categoryOdt;
    } 

    @Override
    public void execute() {

        Category category = factory.createCategory(categoryOdt.getType(), categoryOdt.getName());

        facade.SaveCategory(category);

        System.out.println("Category created succesfully!");
    }
}
