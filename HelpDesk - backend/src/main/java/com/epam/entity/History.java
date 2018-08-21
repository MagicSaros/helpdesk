package com.epam.entity;

import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "history")
public class History {

    private Long id;
    private Ticket ticket;
    private Timestamp date;
    private String action;
    private User user;
    private String description;

    public History() {

    }

    private History(Builder builder) {
        this.id = builder.id;
        this.ticket = builder.ticket;
        this.date = builder.date;
        this.action = builder.action;
        this.user = builder.user;
        this.description = builder.description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "ticket_id")
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Column
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Column
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        History history = (History) o;

        return new EqualsBuilder()
            .append(id, history.id)
            .append(ticket, history.ticket)
            .append(date, history.date)
            .append(action, history.action)
            .append(user, history.user)
            .append(description, history.description)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(ticket)
            .append(date)
            .append(action)
            .append(user)
            .append(description)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("ticket", ticket)
            .append("date", date)
            .append("action", action)
            .append("user", user)
            .append("description", description)
            .toString();
    }

    public static class Builder {

        private Long id;
        private Ticket ticket;
        private Timestamp date;
        private String action;
        private User user;
        private String description;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setTicket(Ticket ticket) {
            this.ticket = ticket;
            return this;
        }

        public Builder setDate(Timestamp date) {
            this.date = date;
            return this;
        }

        public Builder setAction(String action) {
            this.action = action;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public History build() {
            return new History(this);
        }
    }
}
