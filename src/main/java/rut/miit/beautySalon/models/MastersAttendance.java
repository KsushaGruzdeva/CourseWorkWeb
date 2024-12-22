package rut.miit.beautySalon.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "mastersAttendance")
public class MastersAttendance extends BaseEntity {
    private Attendance attendance;
    private Master master;

    protected MastersAttendance() {}

    public MastersAttendance (Attendance attendance, Master master) {
        this.attendance = attendance;
        this.master = master;
    }

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "attendance_id", referencedColumnName = "id")
    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "master_id", referencedColumnName = "id")
    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }
}
