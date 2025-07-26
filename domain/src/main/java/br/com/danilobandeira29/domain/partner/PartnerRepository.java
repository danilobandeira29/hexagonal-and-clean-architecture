package br.com.danilobandeira29.domain.partner;

import br.com.danilobandeira29.domain.person.Cnpj;
import br.com.danilobandeira29.domain.person.Email;

import java.util.Optional;

public interface PartnerRepository {
    Optional<Partner> partnerOfId(PartnerId andId);
    Optional<Partner> partnerOfCnpj(Cnpj cnpj);
    Optional<Partner> partnerOfEmail(Email email);
    Partner create(Partner partner);
    Partner update(Partner partner);
    void deleteAll();
}
