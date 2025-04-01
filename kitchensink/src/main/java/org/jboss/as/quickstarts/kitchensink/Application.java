package org.jboss.as.quickstarts.kitchensink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "org.jboss.as.quickstarts.kitchensink")
//@EnableJpaRepositories(basePackages = "org.jboss.as.quickstarts.kitchensink.data")
@EnableMongoRepositories(basePackages = "org.jboss.as.quickstarts.kitchensink.data")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}