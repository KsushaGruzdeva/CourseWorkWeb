package rut.miit.beautySalon.services.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class ClientDto implements Serializable {
    private String id;
    private String name;
    private String username;
    private String password;
    private String email;

    protected ClientDto() {}

    public ClientDto (String id, String name, String login, String password, String email) {
        this.id = id;
        this.name = name;
        this.username = login;
        this.password = password;
        this.email = email;
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @NotNull
    @NotEmpty
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
