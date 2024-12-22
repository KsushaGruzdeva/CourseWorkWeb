package rut.miit.beautySalon.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "attendance")
public class Attendance extends BaseEntity {
    private String name;
    private int price;

    protected Attendance() {}

    public Attendance (String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price", nullable = false)
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
