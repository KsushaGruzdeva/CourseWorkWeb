package rut.miit.beautySalon.services.dtos.createDto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class BeautySalonCreateDto implements Serializable {
    private String name;
    private String type;
    private String address;
    private String email;

    protected BeautySalonCreateDto() {}

    public BeautySalonCreateDto (String name, String type, String address, int rating, String email) {
        this.name = name;
        this.type = type;
        this.address = address;
        this.email = email;
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
