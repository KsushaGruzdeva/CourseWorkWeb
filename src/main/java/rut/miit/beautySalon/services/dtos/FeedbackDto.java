package rut.miit.beautySalon.services.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class FeedbackDto implements Serializable {
    private String id;
    private String clientName;
    private String masterName;
    private String feedback;

    public FeedbackDto(){}

    public FeedbackDto (String id, String clientName, String masterName, String feedback) {
        this.id = id;
        this.clientName = clientName;
        this.masterName = masterName;
        this.feedback = feedback;
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
    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
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
    public String getFeedback() {
        return feedback;
    }
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
