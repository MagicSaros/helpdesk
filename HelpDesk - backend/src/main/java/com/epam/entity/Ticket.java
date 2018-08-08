package com.epam.entity;

import com.epam.enums.State;
import com.epam.enums.Urgency;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "desired_resolution_date")
    private Date desiredResolutionDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "state_id")
    private State state;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "urgency_id")
    private Urgency urgency;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "approver_id")
    private User approver;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getDesiredResolutionDate() {
        return desiredResolutionDate;
    }

    public void setDesiredResolutionDate(Date desiredResolutionDate) {
        this.desiredResolutionDate = desiredResolutionDate;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public User getApprover() {
        return approver;
    }

    public void setApprover(User approver) {
        this.approver = approver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(name, ticket.name) &&
                Objects.equals(description, ticket.description) &&
                Objects.equals(createdOn, ticket.createdOn) &&
                Objects.equals(desiredResolutionDate, ticket.desiredResolutionDate) &&
                Objects.equals(assignee, ticket.assignee) &&
                Objects.equals(owner, ticket.owner) &&
                state == ticket.state &&
                Objects.equals(category, ticket.category) &&
                urgency == ticket.urgency &&
                Objects.equals(approver, ticket.approver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, createdOn, desiredResolutionDate, assignee, owner, state, category, urgency, approver);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdOn=" + createdOn +
                ", desiredResolutionDate=" + desiredResolutionDate +
                ", assignee=" + assignee +
                ", owner=" + owner +
                ", state=" + state +
                ", category=" + category +
                ", urgency=" + urgency +
                ", approver=" + approver +
                '}';
    }
}
