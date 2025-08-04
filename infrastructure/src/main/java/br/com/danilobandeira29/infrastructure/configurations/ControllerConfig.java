package br.com.danilobandeira29.infrastructure.configurations;

import br.com.danilobandeira29.application.partner.CreatePartnerUseCase;
import br.com.danilobandeira29.application.partner.GetPartnerByIdUseCase;
import br.com.danilobandeira29.infrastructure.rest.PartnerFnController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Bean
    public PartnerFnController partnerFnController(
            final CreatePartnerUseCase createPartnerUseCase,
            final GetPartnerByIdUseCase getPartnerByIdUseCase
    ) {
        return new PartnerFnController(createPartnerUseCase, getPartnerByIdUseCase);
    }
}
