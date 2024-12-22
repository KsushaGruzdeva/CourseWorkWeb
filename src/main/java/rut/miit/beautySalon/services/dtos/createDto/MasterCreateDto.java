package rut.miit.beautySalon.services.dtos.createDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class MasterCreateDto implements Serializable {
    private String beautySalonId;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String username;
    private String password;
    private String confirmPassword;

    public MasterCreateDto() {}

    public MasterCreateDto (String beautySalonName, String surname, String name, String patronymic, String email, String username, String password, String confirmPassword) {
        this.beautySalonId = beautySalonName;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
//        this.position = position;
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

//    @NotNull
//    @NotEmpty
//    public String getPosition() {
//        return position;
//    }
//
//    public void setPosition(String position) {
//        this.position = position;
//    }

    @NotEmpty(message = "Email cannot be null or empty!")
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Password cannot be null or empty!")
    @Size(min = 5, max = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "User name cannot be null or empty!")
    @Size(min = 5, max = 20)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = "Confirm Password cannot be null or empty!")
    @Size(min = 5, max = 20)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
