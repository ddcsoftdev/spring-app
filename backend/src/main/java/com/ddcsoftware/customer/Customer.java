package com.ddcsoftware.customer;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Objects;


/*
 *Controller classes are in charge of API
 *Business (Service) takes care of validating and handing logic between Controller and DataAccess
 *DataAccess implement a Dao and comunicate with DB
 */

//THIS SET UP FOR JDBC
@Entity
//This allows to setup the table however we want
//With @Entity JPA handles everything automatically if @Table not specified
@Table(
        name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(name = "customer_email_unique", columnNames = "email")
        }
)
public class Customer {

    @Id
    @SequenceGenerator(
            name = "customer_id_seq",
            sequenceName = "customer_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_seq"
    )
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Customer() {
        this.id = 0;
        this.name = "N/A";
        this.email = "N/A";
        this.age = 0;
        this.gender = Gender.MALE;
    }

    public Customer(String name, String email, Integer age, Gender gender) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
    }

    public Customer(Integer id, String name, String email, Integer age, Gender gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) { this.age = age; }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) { this.gender = gender; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(age, customer.age) &&
                Objects.equals(gender, customer.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age, gender);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }
}
