package com.epam.entity;

import java.util.Date;
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
@Table(name = "comment")
public class Comment {

    private Long id;
    private User user;
    private String text;
    private Date date;
    private Ticket ticket;

    public Comment() {
    }

    private Comment(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.text = builder.text;
        this.date = builder.date;
        this.ticket = builder.ticket;
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
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "ticket_id")
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Comment comment = (Comment) o;

        return new EqualsBuilder()
            .append(id, comment.id)
            .append(user, comment.user)
            .append(text, comment.text)
            .append(date, comment.date)
            .append(ticket, comment.ticket)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(user)
            .append(text)
            .append(date)
            .append(ticket)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("user", user)
            .append("text", text)
            .append("date", date)
            .append("ticket", ticket)
            .toString();
    }

    public static class Builder {

        private Long id;
        private User user;
        private String text;
        private Date date;
        private Ticket ticket;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }


        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setDate(Date date) {
            this.date = date;
            return this;
        }

        public Builder setTicket(Ticket ticket) {
            this.ticket = ticket;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }
}
