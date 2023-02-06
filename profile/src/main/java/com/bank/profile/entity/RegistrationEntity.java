package com.bank.profile.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Entity для таблицы registration.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "registration", schema = "profile")
public class RegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @NotNull
    @Column(name = "country")
    String country;

    @Column(name = "region")
    String region;

    @Column(name = "city")
    String city;

    @Column(name = "district")
    String district;

    @Column(name = "locality")
    String locality;

    @Column(name = "street")
    String street;

    @Column(name = "house_number")
    String houseNumber;

    @Column(name = "house_block")
    String houseBlock;

    @Column(name = "flat_number")
    String flatNumber;

    @NotNull
    @Column(name = "index")
    Long index;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RegistrationEntity registration = (RegistrationEntity) o;
        return id.equals(registration.id) &&
                country.equals(registration.country) &&
                region.equals(registration.region) &&
                city.equals(registration.city) &&
                district.equals(registration.district) &&
                locality.equals(registration.locality) &&
                street.equals(registration.street) &&
                houseNumber.equals(registration.houseNumber) &&
                houseBlock.equals(registration.houseBlock) &&
                flatNumber.equals(registration.flatNumber) &&
                index.equals(registration.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, region, city, district, locality, street, houseNumber,
                houseBlock, flatNumber, index
        );
    }
}
