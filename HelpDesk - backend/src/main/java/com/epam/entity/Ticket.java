package com.epam.entity;

import com.epam.enums.State;
import com.epam.enums.Urgency;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "ticket")
public class Ticket {

    private Long id;
    private String name;
    private String description;
    private Date createdOn;
    private Date desiredResolutionDate;
    private User assignee;
    private User owner;
    private State state;
    private Category category;
    private Urgency urgency;
    private User approver;
    private Set<Comment> comments;
    private Set<Attachment> attachments;

    public Ticket() {
    }

    private Ticket(final Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.createdOn = builder.createdOn;
        this.desiredResolutionDate = builder.desiredResolutionDate;
        this.assignee = builder.assignee;
        this.owner = builder.owner;
        this.state = builder.state;
        this.category = builder.category;
        this.urgency = builder.urgency;
        this.approver = builder.approver;
        this.comments = builder.comments;
        this.attachments = builder.attachments;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "created_on")
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name = "desired_resolution_date")
    public Date getDesiredResolutionDate() {
        return desiredResolutionDate;
    }

    public void setDesiredResolutionDate(Date desiredResolutionDate) {
        this.desiredResolutionDate = desiredResolutionDate;
    }

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "assignee_id")
    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "owner_id")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Column(name = "state_id")
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Column(name = "urgency_id")
    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "approver_id")
    public User getApprover() {
        return approver;
    }

    public void setApprover(User approver) {
        this.approver = approver;
    }

    @OneToMany(mappedBy = "ticket", orphanRemoval = true)
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "ticket", orphanRemoval = true)
    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Ticket ticket = (Ticket) o;

        return new EqualsBuilder()
            .append(id, ticket.id)
            .append(name, ticket.name)
            .append(description, ticket.description)
            .append(createdOn, ticket.createdOn)
            .append(desiredResolutionDate, ticket.desiredResolutionDate)
            .append(assignee, ticket.assignee)
            .append(owner, ticket.owner)
            .append(state, ticket.state)
            .append(category, ticket.category)
            .append(urgency, ticket.urgency)
            .append(approver, ticket.approver)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(name)
            .append(description)
            .append(createdOn)
            .append(desiredResolutionDate)
            .append(assignee)
            .append(owner)
            .append(state)
            .append(category)
            .append(urgency)
            .append(approver)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("name", name)
            .append("description", description)
            .append("createdOn", createdOn)
            .append("desiredResolutionDate", desiredResolutionDate)
            .append("assignee", assignee)
            .append("owner", owner)
            .append("state", state)
            .append("category", category)
            .append("urgency", urgency)
            .append("approver", approver)
            .toString();
    }

    public static class Builder {

        private Long id;
        private String name;
        private String description;
        private Date createdOn;
        private Date desiredResolutionDate;
        private User assignee;
        private User owner;
        private State state;
        private Category category;
        private Urgency urgency;
        private User approver;
        private Set<Comment> comments;
        private Set<Attachment> attachments;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCreatedOn(Date createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public Builder setDesiredResolutionDate(Date desiredResolutionDate) {
            this.desiredResolutionDate = desiredResolutionDate;
            return this;
        }

        public Builder setAssignee(User assignee) {
            this.assignee = assignee;
            return this;
        }

        public Builder setOwner(User owner) {
            this.owner = owner;
            return this;
        }

        public Builder setState(State state) {
            this.state = state;
            return this;
        }

        public Builder setCategory(Category category) {
            this.category = category;
            return this;
        }

        public Builder setUrgency(Urgency urgency) {
            this.urgency = urgency;
            return this;
        }

        public Builder setApprover(User approver) {
            this.approver = approver;
            return this;
        }

        public Builder setComments(Set<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public Builder setAttacjments(Set<Attachment> attachments) {
            this.attachments = attachments;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }
}
