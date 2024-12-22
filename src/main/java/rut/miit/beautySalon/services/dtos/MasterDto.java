package rut.miit.beautySalon.services.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class MasterDto implements Serializable {
    private String id;
    private String beautySalonId;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String username;
    private String password;

    protected MasterDto() {}

    public MasterDto (String id, String beautySalonName, String name, String surname, String patronymic, String email, String username, String password) {
        this.id = id;
        this.beautySalonId = beautySalonName;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @NotNull
    @NotEmpty
    public String getId() {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    @NotNull
    @NotEmpty
    public String getBeautySalonId() {
        return beautySalonId;
    }

    public void setBeautySalonId(String beautySalonId) {
        this.beautySalonId = beautySalonId;
    }

    @NotNull
    @NotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @NotEmpty
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @NotNull
    @NotEmpty
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @NotNull
    @NotEmpty
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    @NotEmpty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    @NotEmpty
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
