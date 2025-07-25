package br.com.danilobandeira29.hexagonal.and.clean.arch.application.usecases.partner;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.usecases.UseCase;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.partner.PartnerId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.repositories.PartnerRepository;

import java.util.Objects;
import java.util.Optional;

public class GetPartnerByIdUseCase extends UseCase<GetPartnerByIdUseCase.Input, Optional<GetPartnerByIdUseCase.Output>> {

    private final PartnerRepository partnerRepository;

    public GetPartnerByIdUseCase(final PartnerRepository partnerRepository) {
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
    }

    @Override
    public Optional<Output> execute(Input input) {
        return partnerRepository.partnerOfId(PartnerId.with(input.id()))
                .map(p -> new Output(
                        p.id().value(),
                        p.cnpj().value(),
                        p.email().value(),
                        p.name().value()
                        )
                );
    }

    public record Input(String id) {}

    public record Output(String id, String cnpj, String email, String name) {}
}
