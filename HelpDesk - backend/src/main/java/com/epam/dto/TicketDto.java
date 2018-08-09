package com.epam.dto;

import com.epam.enums.State;
import com.epam.enums.Urgency;

import java.util.Date;

public class TicketDto {

    private Long id;
    private String name;
    private String description;
    private Date createdOn;
    private Date desiredResolutionDate;
    private UserDto assignee;
    private UserDto owner;
    private State state;
    private CategoryDto category;
    private Urgency urgency;
    private UserDto approver;

    public TicketDto() {
    }

    private TicketDto(final Builder builder) {
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
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public UserDto getAssignee() {
        return assignee;
    }

    public void setAssignee(UserDto assignee) {
        this.assignee = assignee;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public UserDto getApprover() {
        return approver;
    }

    public void setApprover(UserDto approver) {
        this.approver = approver;
    }

    public static class Builder {

        private Long id;
        private String name;
        private String description;
        private Date createdOn;
        private Date desiredResolutionDate;
        private UserDto assignee;
        private UserDto owner;
        private State state;
        private CategoryDto category;
        private Urgency urgency;
        private UserDto approver;

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

        public Builder setAssignee(UserDto assignee) {
            this.assignee = assignee;
            return this;
        }

        public Builder setOwner(UserDto owner) {
            this.owner = owner;
            return this;
        }

        public Builder setState(State state) {
            this.state = state;
            return this;
        }

        public Builder setCategory(CategoryDto category) {
            this.category = category;
            return this;
        }

        public Builder setUrgency(Urgency urgency) {
            this.urgency = urgency;
            return this;
        }

        public Builder setApprover(UserDto approver) {
            this.approver = approver;
            return this;
        }

        public TicketDto build() {
            return new TicketDto(this);
        }
    }
}
