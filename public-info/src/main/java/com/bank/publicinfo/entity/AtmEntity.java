package com.bank.publicinfo.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "atm", schema = "public_bank_information")
public class AtmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @NotNull
    @Column(name = "address")
    String address;

    @Column(name = "start_of_work")
    LocalTime startOfWork;

    @Column(name = "end_of_work")
    LocalTime endOfWork;

    @Column(name = "all_hours")
    Boolean allHours;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "branch_id")
    BranchEntity branch;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AtmEntity atm)) {
            return false;
        }
        return Objects.equals(getId(), atm.getId()) &&
                Objects.equals(getAddress(), atm.getAddress()) &&
                Objects.equals(getStartOfWork(), atm.getStartOfWork()) &&
                Objects.equals(getEndOfWork(), atm.getEndOfWork()) &&
                Objects.equals(getAllHours(), atm.getAllHours()) &&
                Objects.equals(getBranch(), atm.getBranch());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAddress(), getStartOfWork(), getEndOfWork(), getAllHours(), getBranch());
    }
}
