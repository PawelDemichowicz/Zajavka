package pl.zajavka.workshop15;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.zajavka.workshop15.business.ReloadDataService;
import pl.zajavka.workshop15.infrastructure.configuration.ApplicationConfiguration;

public class ZajavkaStoreApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        ReloadDataService reloadDataService = applicationContext.getBean(ReloadDataService.class);
        reloadDataService.loadRandomData();
    }
}
