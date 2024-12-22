package rut.miit.beautySalon.models;

import jakarta.persistence.*;

@Entity
@Table(name = "feedback")
public class Feedback extends BaseEntity{
    private String feedback;
    private Client client;
    private Master master;

    protected Feedback() {}

    public Feedback (Client client, Master master, String feedback) {
        this.client = client;
        this.master = master;
        this.feedback = feedback;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "master_id", referencedColumnName = "id")
    public Master getMaster() {
        return master;
    }
    public void setMaster(Master master) {
        this.master = master;
    }

    @Column(name = "feedback", nullable = false)
    public String getFeedback() {
        return feedback;
    }
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
