package rut.miit.beautySalon.services.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class BeautySalonDto implements Serializable {
    private String id;
    private String name;
    private String type;
    private String address;
    private String email;

    protected BeautySalonDto() {}

    public BeautySalonDto (String id, String name, String type, String address, String email) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.address = address;
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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NotNull
    @NotEmpty
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
