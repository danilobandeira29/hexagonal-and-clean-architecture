package br.com.danilobandeira29.hexagonal.and.clean.arch.application.repository;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.partner.Partner;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.partner.PartnerId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person.Cnpj;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person.Email;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.repositories.PartnerRepository;

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