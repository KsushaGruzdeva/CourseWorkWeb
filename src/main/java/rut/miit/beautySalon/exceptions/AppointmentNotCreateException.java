package rut.miit.beautySalon.exceptions;

public class AppointmentNotCreateException extends RuntimeException {
    public AppointmentNotCreateException (String clientId){
        super ("Client have appointment at that time" + clientId);
    }
}

