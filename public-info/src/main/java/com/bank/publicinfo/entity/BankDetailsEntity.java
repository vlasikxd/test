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
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "bank_details", schema = "public_bank_information")
public class BankDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "bik")
    String bik;

    @Column(name = "inn")
    String inn;

    @Column(name = "kpp")
    String kpp;

    @Column(name = "cor_account")
    String corAccount;

    @Column(name = "city")
    String city;

    @Column(name = "joint_stock_company")
    String jointStockCompany;

    @Column(name = "name")
    String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankDetailsEntity bankDetails)) {
            return false;
        }
        return Objects.equals(getId(), bankDetails.getId()) &&
                Objects.equals(getBik(), bankDetails.getBik()) &&
                Objects.equals(getInn(), bankDetails.getInn()) &&
                Objects.equals(getKpp(), bankDetails.getKpp()) &&
                Objects.equals(getCorAccount(), bankDetails.getCorAccount()) &&
                Objects.equals(getCity(), bankDetails.getCity()) &&
                Objects.equals(getJointStockCompany(), bankDetails.getJointStockCompany()) &&
                Objects.equals(getName(), bankDetails.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getId(), getBik(), getInn(), getKpp(), getCorAccount(), getCity(), getJointStockCompany(), getName()
        );
    }
}
