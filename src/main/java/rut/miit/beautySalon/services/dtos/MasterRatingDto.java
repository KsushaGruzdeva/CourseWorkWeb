package rut.miit.beautySalon.services.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class MasterRatingDto {
    private String master;
    private List<FeedbackDto> feedbackDtoList;
    private int count;

    protected MasterRatingDto() {}

    public MasterRatingDto (String master, List<FeedbackDto> feedbackDtoList, int count) {
        this.master = master;
        this.feedbackDtoList = feedbackDtoList;
        this.count = count;
    }

    @NotNull
    @NotEmpty
    public String getMaster() {
        return master;
    }

    public void setMaster (String master) {
        this.master = master;
    }

    @NotNull
    @NotEmpty
    public int getCount() {
        return count;
    }

    public void setCount (int count) {
        this.count = count;
    }

    @NotNull
    @NotEmpty
    public List<FeedbackDto> getFeedbackDtoList() {
        return feedbackDtoList;
    }

    public void setFeedbackDtoList(List<FeedbackDto> feedbackDtoList) {
        this.feedbackDtoList = feedbackDtoList;
    }
}
