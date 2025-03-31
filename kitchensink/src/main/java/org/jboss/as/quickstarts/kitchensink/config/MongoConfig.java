package org.jboss.as.quickstarts.kitchensink.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "org.jboss.as.quickstarts.kitchensink.data")
public class MongoConfig {
}