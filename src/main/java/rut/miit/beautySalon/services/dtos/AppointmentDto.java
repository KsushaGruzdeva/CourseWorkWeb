package rut.miit.beautySalon.services.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AppointmentDto implements Serializable {
    private String id;
    private String mastersAttendanceAttendanceName;
    private String mastersAttendanceMasterSurname;
    private String mastersAttendanceMasterName;
    private String mastersAttendanceMasterPatronymic;
    private String clientName;
    private LocalDateTime date;

    public AppointmentDto(){}

    public AppointmentDto (String id, String mastersAttendanceMasterSurname, String mastersAttendanceAttendanceName, String mastersAttendanceMasterPatronymic, String mastersAttendanceMasterName, String clientName, LocalDateTime date) {
        this.id = id;
        this.mastersAttendanceMasterSurname = mastersAttendanceMasterSurname;
        this.mastersAttendanceAttendanceName = mastersAttendanceAttendanceName;
        this.mastersAttendanceMasterPatronymic = mastersAttendanceMasterPatronymic;
        this.mastersAttendanceMasterName = mastersAttendanceMasterName;
        this.clientName = clientName;
        this.date = date;
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
    public String getMastersAttendanceAttendanceName() {
        return mastersAttendanceAttendanceName;
    }

    public void setMastersAttendanceAttendanceName(String mastersAttendanceMasterName) {
        this.mastersAttendanceAttendanceName = mastersAttendanceMasterName;
    }

    @NotNull
    @NotEmpty
    public String getMastersAttendanceMasterSurname() {
        return mastersAttendanceMasterSurname;
    }

    public void setMastersAttendanceMasterSurname(String mastersAttendanceMasterSurname) {
        this.mastersAttendanceMasterSurname = mastersAttendanceMasterSurname;
    }

    @NotNull
    @NotEmpty
    public String getMastersAttendanceMasterName() {
        return mastersAttendanceMasterName;
    }

    public void setMastersAttendanceMasterName(String mastersAttendanceMasterName) {
        this.mastersAttendanceMasterName = mastersAttendanceMasterName;
    }

    @NotNull
    @NotEmpty
    public String getMastersAttendanceMasterPatronymic() {
        return mastersAttendanceMasterPatronymic;
    }

    public void setMastersAttendanceMasterPatronymic(String mastersAttendanceMasterPatronymic) {
        this.mastersAttendanceMasterPatronymic = mastersAttendanceMasterPatronymic;
    }

    @NotNull
    @NotEmpty
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @NotNull
    @NotEmpty
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
