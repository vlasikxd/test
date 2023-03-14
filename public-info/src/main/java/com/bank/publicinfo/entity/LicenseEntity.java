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
@Table(name = "license", schema = "public_bank_information")
public class LicenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "photo")
    Byte photo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "bank_details_id")
    BankDetailsEntity bankDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LicenseEntity that)) {
            return false;
        }
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getPhoto(), that.getPhoto()) &&
                Objects.equals(getBankDetails(), that.getBankDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPhoto(), getBankDetails());
    }
}
