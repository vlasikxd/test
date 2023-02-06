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
import java.time.LocalDate;
import java.util.Objects;

/**
 * Entity для таблицы passport.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "passport", schema = "profile")
public class PassportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @NotNull
    @Column(name = "series")
    Integer series;

    @NotNull
    @Column(name = "number")
    Long number;

    @NotNull
    @Column(name = "last_name")
    String lastName;

    @NotNull
    @Column(name = "first_name")
    String firstName;

    @Column(name = "middle_name")
    String middleName;

    @NotNull
    @Column(name = "gender")
    String gender;

    @NotNull
    @Column(name = "birth_date")
    LocalDate birthDate;

    @NotNull
    @Column(name = "birth_place")
    String birthPlace;

    @NotNull
    @Column(name = "issued_by")
    String issuedBy;

    @NotNull
    @Column(name = "date_of_issue")
    LocalDate dateOfIssue;

    @NotNull
    @Column(name = "division_code")
    Integer divisionCode;

    @Column(name = "expiration_date")
    LocalDate expirationDate;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "registration_id")
    RegistrationEntity registration;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PassportEntity passport = (PassportEntity) o;
        return id.equals(passport.id) &&
                series.equals(passport.series) &&
                number.equals(passport.number) &&
                lastName.equals(passport.lastName) &&
                firstName.equals(passport.firstName) &&
                middleName.equals(passport.middleName) &&
                gender.equals(passport.gender) &&
                birthDate.equals(passport.birthDate) &&
                birthPlace.equals(passport.birthPlace) &&
                issuedBy.equals(passport.issuedBy) &&
                dateOfIssue.equals(passport.dateOfIssue) &&
                divisionCode.equals(passport.divisionCode) &&
                expirationDate.equals(passport.expirationDate) &&
                registration.equals(passport.registration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, series, number);
    }
}
