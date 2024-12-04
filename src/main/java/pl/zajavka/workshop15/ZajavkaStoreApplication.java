package pl.zajavka.workshop15;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.zajavka.workshop15.business.RandomDataService;
import pl.zajavka.workshop15.infrastructure.configuration.ApplicationConfiguration;

public class ZajavkaStoreApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        RandomDataService randomDataService = applicationContext.getBean(RandomDataService.class);
        randomDataService.create();
    }
}
