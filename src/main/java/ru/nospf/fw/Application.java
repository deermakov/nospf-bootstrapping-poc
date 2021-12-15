package ru.nospf.fw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "ru.nospf")
@EnableJpaRepositories("ru.nospf.adapter.jpa")
@EntityScan("ru.nospf.domain")
@EnableTransactionManagement
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        var application = new SpringApplication(Application.class);

        // Here we add the same initializer as we were using in our tests...
        application.addInitializers(new TestContainersInitializer.Initializer());

        // ... and start it normally
        application.run(args);
    }
}
