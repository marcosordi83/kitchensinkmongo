package org.jboss.as.quickstarts.kitchensink.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.logging.Logger;

@Configuration
public class Resources {

    @PersistenceContext
    private EntityManager em;

    // @Bean
    // @Scope(WebApplicationContext.SCOPE_REQUEST)
    // public EntityManager entityManager() {
    //     return em;
    // }

    @Bean
    public Logger produceLog() {
        return Logger.getLogger(Resources.class.getName());
    }
}