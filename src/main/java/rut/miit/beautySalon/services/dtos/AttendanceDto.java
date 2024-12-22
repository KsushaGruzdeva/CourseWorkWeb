package rut.miit.beautySalon.services.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class AttendanceDto implements Serializable {
    private String id;
    private String name;
    private int price;

    protected AttendanceDto() {}

    public AttendanceDto (String id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    @DecimalMin(value = "0")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
