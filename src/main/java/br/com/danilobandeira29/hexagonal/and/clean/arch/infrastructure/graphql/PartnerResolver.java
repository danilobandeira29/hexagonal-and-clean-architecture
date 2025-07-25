package br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.graphql;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.usecases.partner.CreatePartnerUseCase;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.usecases.partner.GetPartnerByIdUseCase;
import br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.dtos.NewPartnerDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Objects;
import java.util.Optional;

@Controller
public class PartnerResolver {

    private final CreatePartnerUseCase createPartnerUseCase;
    private final GetPartnerByIdUseCase getPartnerByIdUseCase;

    public PartnerResolver(
            final CreatePartnerUseCase createPartnerUseCase,
            final GetPartnerByIdUseCase getPartnerByIdUseCase
    ) {
        this.createPartnerUseCase = Objects.requireNonNull(createPartnerUseCase);
        this.getPartnerByIdUseCase = Objects.requireNonNull(getPartnerByIdUseCase);
    }

    @MutationMapping
    public CreatePartnerUseCase.Output createPartner(@Argument NewPartnerDTO input) {
        return createPartnerUseCase.execute(
                new CreatePartnerUseCase.Input(input.cnpj(), input.email(), input.name())
        );
    }

    @QueryMapping
    public Optional<GetPartnerByIdUseCase.Output> partnerOfId(@Argument String id) {
        return getPartnerByIdUseCase.execute(new GetPartnerByIdUseCase.Input(id));
    }
}
