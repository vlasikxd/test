package com.bank.publicinfo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "certificate", schema = "public_bank_information")
public class CertificateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "photo")
    Byte photo;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "bank_details_id")
    BankDetailsEntity bankDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CertificateEntity certificate)) {
            return false;
        }
        return Objects.equals(getId(), certificate.getId()) &&
                Objects.equals(getPhoto(), certificate.getPhoto()) &&
                Objects.equals(getBankDetails(), certificate.getBankDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPhoto(), getBankDetails());
    }
}
