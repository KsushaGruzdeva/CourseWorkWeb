package rut.miit.beautySalon.services.dtos.createDto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class AttendanceCreateDto implements Serializable {
    private String name;
    private int price;

    protected AttendanceCreateDto() {}

    public AttendanceCreateDto (String name, int price) {
        this.name = name;
        this.price = price;
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
