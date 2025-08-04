package br.com.danilobandeira29.infrastructure.configurations;

import br.com.danilobandeira29.infrastructure.http.SprintHttpRouter;
import br.com.danilobandeira29.infrastructure.rest.PartnerFnController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;

// Frameworks and Drivers
@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<?> routes(
            final PartnerFnController partnerFnController
    ) {
        final var router = new SprintHttpRouter();
        partnerFnController.bind(router);
        return router.router().build();
    }
}
