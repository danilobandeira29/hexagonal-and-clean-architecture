package br.com.danilobandeira29.application.partner;

import br.com.danilobandeira29.application.UseCase;
import br.com.danilobandeira29.domain.exceptions.ValidationException;
import br.com.danilobandeira29.domain.partner.Partner;
import br.com.danilobandeira29.domain.partner.PartnerRepository;
import br.com.danilobandeira29.domain.person.Cnpj;
import br.com.danilobandeira29.domain.person.Email;


import java.util.Objects;

public class CreatePartnerUseCase extends UseCase<CreatePartnerUseCase.Input, CreatePartnerUseCase.Output> {

    private final PartnerRepository partnerRepository;

    public CreatePartnerUseCase(final PartnerRepository partnerRepository) {
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
    }

    @Override
    public Output execute(Input input) {
        if (partnerRepository.partnerOfCnpj(new Cnpj(input.cnpj())).isPresent()) {
            throw new ValidationException("Partner already exists");
        }
        if (partnerRepository.partnerOfEmail(new Email(input.email())).isPresent()) {
            throw new ValidationException("Partner already exists");
        }
        var partner = partnerRepository.create(Partner.newPartner(input.name, input.cnpj, input.email));
        return new CreatePartnerUseCase.Output(
                partner.id().value(),
                partner.cnpj().value(),
                partner.email().value(),
                partner.name().value()
        );
    }

    public record Input(String cnpj, String email, String name) {}

    public record Output(String id, String cnpj, String email, String name) {}
}
