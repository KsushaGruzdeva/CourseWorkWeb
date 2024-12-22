package rut.miit.beautySalon.models;

import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class Client extends User {
    private String name;
    private String email;

    protected Client() {
    }

    public Client (String name, String email, String username, String password) {
        super(username, password);
        this.name = name;
        this.email = email;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
