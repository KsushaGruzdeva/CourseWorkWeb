package rut.miit.beautySalon.services.dtos.createDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class FeedbackCreateDto implements Serializable {
    private String clientName;
    private String masterName;
    private String masterSurname;
    private String masterPatronymic;
    private String feedback;

    public FeedbackCreateDto(){}

    public FeedbackCreateDto (String clientName, String masterSurname, String masterName, String masterPatronymic, String feedback) {
        this.clientName = clientName;
        this.masterSurname = masterSurname;
        this.masterName = masterName;
        this.masterPatronymic = masterPatronymic;
        this.feedback = feedback;
    }

    @NotNull
    @NotEmpty
    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @NotNull
    @NotEmpty
    public String getMasterSurname() {
        return masterSurname;
    }
    public void setMasterSurname(String masterSurname) {
        this.masterSurname = masterSurname;
    }

    @NotNull
    @NotEmpty
    public String getMasterName() {
        return masterName;
    }
    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }
    @NotNull
    @NotEmpty
    public String getMasterPatronymic() {
        return masterPatronymic;
    }
    public void setMasterPatronymic(String masterPatronymic) {
        this.masterPatronymic = masterPatronymic;
    }

    @NotNull
    @NotEmpty
    public String getFeedback() {
        return feedback;
    }
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

