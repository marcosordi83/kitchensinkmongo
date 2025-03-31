package org.jboss.as.quickstarts.kitchensink.config;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "org.jboss.as.quickstarts.kitchensink.model")
public class JpaConfig {
}