package pl.zajavka.workshop15.infrastructure.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.zajavka.workshop15.ZajavkaStoreApplication;

@Configuration
@Import({DatabaseConfiguration.class})
@ComponentScan(basePackageClasses = ZajavkaStoreApplication.class)
public class ApplicationConfiguration {
}
