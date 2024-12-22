package rut.miit.beautySalon.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public abstract class User extends BaseEntity implements Serializable {

    private String username;

    private String password;

    private List<Role> roles;

    public User() {
        this.roles = new ArrayList<>();
    }

    public User(String username, String password) {
        this.roles = new ArrayList<>();

        this.username = username;
        this.password = password;
    }

//    @Column(nullable = false, unique = true)
    @Column (name = "username", nullable = false)
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }


//    @Column(nullable = false)
    @Column (name = "password", nullable = false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "client_id", referencedColumnName = "id")
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
