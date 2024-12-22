package rut.miit.beautySalon.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "master")
public class Master extends User {
    private BeautySalon beautySalon;
    private String name;
    private String surname;
    private String patronymic;
    private String email;

    protected Master() {
    }

    public Master (BeautySalon beautySalon, String name, String surname,
    String patronymic, String email, String username, String password) {
        super(username, password);
        this.beautySalon = beautySalon;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
    }

    @ManyToOne(optional = false)
    @JoinColumn (name = "beautySalon_id", referencedColumnName = "id")
    public BeautySalon getBeautySalon() {
        return beautySalon;
    }

    public void setBeautySalon(BeautySalon beautySalon) {
        this.beautySalon = beautySalon;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "surname", nullable = false)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "patronymic", nullable = false)
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
