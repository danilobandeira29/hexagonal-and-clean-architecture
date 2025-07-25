package br.com.danilobandeira29.hexagonal.and.clean.arch.application.repositories;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.partner.Partner;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.partner.PartnerId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person.Cnpj;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person.Email;

import java.util.Optional;

public interface PartnerRepository {
    Optional<Partner> partnerOfId(PartnerId andId);
    Optional<Partner> partnerOfCnpj(Cnpj cnpj);
    Optional<Partner> partnerOfEmail(Email email);
    Partner create(Partner partner);
    Partner update(Partner partner);
    void deleteAll();
}
