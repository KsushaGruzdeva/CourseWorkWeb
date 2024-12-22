package rut.miit.beautySalon.services.dtos.createDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class MasterAttendanceCreateDto implements Serializable {
    private String attendanceId;
    private String masterId;

    protected MasterAttendanceCreateDto() {}

    public MasterAttendanceCreateDto (String attendanceId, String masterId) {
        this.attendanceId = attendanceId;
        this.masterId = masterId;
    }

    @NotNull
    @NotEmpty
    public String getAttendance() {
        return attendanceId;
    }

    public void setAttendance(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    @NotNull
    @NotEmpty
    public String getMaster() {
        return masterId;
    }

    public void setMaster(String masterId) {
        this.masterId = masterId;
    }
}
