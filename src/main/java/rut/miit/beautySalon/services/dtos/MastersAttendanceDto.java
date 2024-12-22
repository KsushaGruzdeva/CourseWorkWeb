package rut.miit.beautySalon.services.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class MastersAttendanceDto implements Serializable {
    private String id;
    private String attendanceName;
    private String masterName;

    protected MastersAttendanceDto() {}

    public MastersAttendanceDto (String id, String attendanceId, String masterId) {
        this.id = id;
        this.attendanceName = attendanceId;
        this.masterName = masterId;
    }

    @NotNull
    @NotEmpty
    public String getAttendance() {
        return attendanceName;
    }

    public void setAttendance(String attendanceId) {
        this.attendanceName = attendanceId;
    }

    @NotNull
    @NotEmpty
    public String getMaster() {
        return masterName;
    }

    public void setMaster(String masterId) {
        this.masterName = masterId;
    }


    @NotNull
    @NotEmpty
    public String getId() {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

}
