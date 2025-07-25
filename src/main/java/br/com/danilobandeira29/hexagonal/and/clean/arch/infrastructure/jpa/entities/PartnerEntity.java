package br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.jpa.entities;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.partner.Partner;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.partner.PartnerId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "Partner")
@Table(name = "partners")
public class PartnerEntity {

    @Id
    private UUID id;

    private String name;

    private String cnpj;

    private String email;

    public PartnerEntity() {
    }

    public PartnerEntity(UUID id, String name, String cnpj, String email) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.email = email;
    }

    public static PartnerEntity of(Partner partner) {
        return new PartnerEntity(
                UUID.fromString(partner.id().value()),
                partner.name().value(),
                partner.cnpj().value(),
                partner.email().value()
        );
    }

    public Partner toPartner() {
        return new Partner(
                PartnerId.with(this.id.toString()),
                this.name,
                this.cnpj,
                this.email
                );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
