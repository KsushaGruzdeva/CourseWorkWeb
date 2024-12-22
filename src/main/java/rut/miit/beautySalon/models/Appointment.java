package rut.miit.beautySalon.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment")
public class Appointment extends BaseEntity {
    private MastersAttendance mastersAttendance;
    private Client client;
    private LocalDateTime date;

    protected Appointment() {}

    public Appointment (MastersAttendance mastersAttendance, Client client, LocalDateTime date) {
        this.mastersAttendance = mastersAttendance;
        this.client = client;
        this.date = date;
    }

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "mastersAttendance_id", referencedColumnName = "id")
    public MastersAttendance getMastersAttendance() {
        return mastersAttendance;
    }

    public void setMastersAttendance(MastersAttendance mastersAttendance) {
        this.mastersAttendance = mastersAttendance;
    }

    @Column(name = "date", nullable = false)
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
