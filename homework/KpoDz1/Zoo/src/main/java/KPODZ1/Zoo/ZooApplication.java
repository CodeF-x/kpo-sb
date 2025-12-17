package KPODZ1.Zoo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import KPODZ1.Zoo.service.ConsoleUI;

public class ZooApplication {

	public static void main(String[] args) {
		try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {
			ConsoleUI ui = context.getBean(ConsoleUI.class);
			ui.run();
		}
	}

}
