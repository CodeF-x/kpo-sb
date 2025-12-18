package KPODZ2.Bank;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import KPODZ2.Bank.service.UIService;

public class BankApplication {

	public static void main(String[] args) {
		try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {
			UIService ui = context.getBean(UIService.class);
			ui.run();
		}
	}

}
