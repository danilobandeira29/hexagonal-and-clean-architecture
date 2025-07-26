package br.com.danilobandeira29.repository;

import br.com.danilobandeira29.domain.partner.Partner;
import br.com.danilobandeira29.domain.partner.PartnerId;
import br.com.danilobandeira29.domain.partner.PartnerRepository;
import br.com.danilobandeira29.domain.person.Cnpj;
import br.com.danilobandeira29.domain.person.Email;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryPartnerRepository implements PartnerRepository {

    private Map<String, Partner> partners;
    private Map<String, Partner> partnersByCnpj;
    private Map<String, Partner> partnersByEmail;

    public InMemoryPartnerRepository() {
        this.partners = new HashMap<>();
        this.partnersByCnpj = new HashMap<>();
        this.partnersByEmail = new HashMap<>();
    }
    @Override
    public Optional<Partner> partnerOfId(PartnerId andId) {
        return Optional.ofNullable(this.partners.get(Objects.requireNonNull(andId).value()));
    }

    @Override
    public Optional<Partner> partnerOfCnpj(Cnpj cnpj) {
        return Optional.ofNullable(this.partnersByCnpj.get(Objects.requireNonNull(cnpj.value())));
    }

    @Override
    public Optional<Partner> partnerOfEmail(Email email) {
        return Optional.ofNullable(this.partnersByEmail.get(Objects.requireNonNull(email.value())));
    }

    @Override
    public Partner create(Partner partner) {
        this.partners.put(partner.id().value(), partner);
        this.partnersByCnpj.put(partner.cnpj().value(), partner);
        this.partnersByEmail.put(partner.email().value(), partner);
        return partner;
    }

    @Override
    public Partner update(Partner partner) {
        this.partners.put(partner.id().value(), partner);
        this.partnersByCnpj.put(partner.cnpj().value(), partner);
        this.partnersByEmail.put(partner.email().value(), partner);
        return partner;
    }

    @Override
    public void deleteAll() {
        this.partners = new HashMap<>();
        this.partnersByCnpj = new HashMap<>();
        this.partnersByEmail = new HashMap<>();
    }
}