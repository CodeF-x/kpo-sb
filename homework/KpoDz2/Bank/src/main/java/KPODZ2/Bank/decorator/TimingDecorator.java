package KPODZ2.Bank.decorator;

import KPODZ2.Bank.interfaces.TransactionCommand;

public class TimingDecorator implements TransactionCommand {
    private final TransactionCommand innerCommand;

    public TimingDecorator(TransactionCommand command) {
        this.innerCommand = command;
    }

    @Override
    public void execute() {
        long start = System.currentTimeMillis();
        
        innerCommand.execute();
        
        long end = System.currentTimeMillis();
        System.out.println("Work time: " + (end - start) + " ms");
    }
}