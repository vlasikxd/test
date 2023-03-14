package com.bank.publicinfo.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "branch", schema = "public_bank_information")
public class BranchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "address")
    String address;

    @Column(name = "phone_number")
    Long phoneNumber;

    @Column(name = "city")
    String city;

    @Column(name = "start_of_work")
    LocalTime startOfWork;

    @Column(name = "end_of_work")
    LocalTime endOfWork;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BranchEntity branch)) {
            return false;
        }
        return Objects.equals(getId(), branch.getId()) &&
                Objects.equals(getAddress(), branch.getAddress()) &&
                Objects.equals(getPhoneNumber(), branch.getPhoneNumber()) &&
                Objects.equals(getCity(), branch.getCity()) &&
                Objects.equals(getStartOfWork(), branch.getStartOfWork()) &&
                Objects.equals(getEndOfWork(), branch.getEndOfWork());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAddress(), getPhoneNumber(), getCity(), getStartOfWork(), getEndOfWork());
    }
}
