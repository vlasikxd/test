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
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Entity для таблицы profile.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "profile", schema = "profile")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @NotNull
    @Column(name = "phone_number")
    Long phoneNumber;

    @Column(name = "email")
    String email;

    @Column(name = "name_on_card")
    String nameOnCard;

    @Column(name = "inn")
    Long inn;

    @Column(name = "snils")
    Long snils;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "passport_id")
    private PassportEntity passport;

    @ManyToOne
    @JoinColumn(name = "actual_registration_id")
    private ActualRegistrationEntity actualRegistration;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProfileEntity profile = (ProfileEntity) o;
        return id.equals(profile.id) &&
                phoneNumber.equals(profile.phoneNumber) &&
                email.equals(profile.email) &&
                nameOnCard.equals(profile.nameOnCard) &&
                inn.equals(profile.inn) &&
                snils.equals(profile.snils) &&
                passport.equals(profile.passport) &&
                actualRegistration.equals(profile.actualRegistration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber, email, nameOnCard, inn, snils, passport, actualRegistration);
    }
}
