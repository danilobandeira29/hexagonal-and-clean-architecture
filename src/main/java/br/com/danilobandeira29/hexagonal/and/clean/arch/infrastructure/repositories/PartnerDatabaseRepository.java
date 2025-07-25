package br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.repositories;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.partner.Partner;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.partner.PartnerId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person.Cnpj;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person.Email;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.repositories.PartnerRepository;
import br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.jpa.entities.PartnerEntity;
import br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.jpa.repositories.PartnerJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

// Interface Adapter
@Component
public class PartnerDatabaseRepository implements PartnerRepository {

    private final PartnerJpaRepository partnerJpaRepository;

    public PartnerDatabaseRepository(final PartnerJpaRepository partnerJpaRepository) {
        this.partnerJpaRepository = Objects.requireNonNull(partnerJpaRepository);
    }

    @Override
    public Optional<Partner> partnerOfId(final PartnerId anId) {
        Objects.requireNonNull(anId, "id cannot be null");
        return partnerJpaRepository
                .findById(UUID.fromString(anId.value()))
                .map(PartnerEntity::toPartner);
    }

    @Override
    public Optional<Partner> partnerOfCnpj(Cnpj cnpj) {
        Objects.requireNonNull(cnpj, "cnpj cannot be null");
        return partnerJpaRepository
                .findByCnpj(cnpj.value())
                .map(PartnerEntity::toPartner);
    }

    @Override
    public Optional<Partner> partnerOfEmail(Email email) {
        Objects.requireNonNull(email, "email cannot be null");
        return partnerJpaRepository
                .findByEmail(email.value())
                .map(PartnerEntity::toPartner);
    }

    @Override
    @Transactional
    public Partner create(Partner partner) {
        return this.partnerJpaRepository
                .save(PartnerEntity.of(partner))
                .toPartner();
    }

    @Override
    public Partner update(Partner partner) {
        return this.partnerJpaRepository
                .save(PartnerEntity.of(partner))
                .toPartner();
    }

    @Override
    public void deleteAll() {
        this.partnerJpaRepository.deleteAll();
    }
}
