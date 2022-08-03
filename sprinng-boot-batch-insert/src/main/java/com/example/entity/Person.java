package com.example.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Person")
@Table(name = "PERSON")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
//    @SequenceGenerator(name = "seqGen", sequenceName = "products_id_seq", allocationSize = 100)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "JOBTITILE", nullable = false)
    private String jobTitle;

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstName;

    @Column(name = "LASTNAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false)
    private String emailAddress;

    public Person() {

    }

    public Person(Long id, String jobTitle, String firstName, String lastName, String emailAddress) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(jobTitle, person.jobTitle) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(emailAddress, person.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jobTitle, firstName, lastName, emailAddress);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", jobTitle='" + jobTitle + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
