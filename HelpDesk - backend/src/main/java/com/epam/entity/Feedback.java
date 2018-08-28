package com.epam.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "feedback")
public class Feedback {

    private Long id;
    private User user;
    private Integer rate;
    private Date date;
    private String text;
    private Ticket ticket;

    public Feedback() {

    }

    private Feedback(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.rate = builder.rate;
        this.date = builder.date;
        this.text = builder.text;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column
    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    @Column
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @OneToOne
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

        Feedback feedback = (Feedback) o;

        return new EqualsBuilder()
            .append(id, feedback.id)
            .append(user, feedback.user)
            .append(rate, feedback.rate)
            .append(date, feedback.date)
            .append(text, feedback.text)
            .append(ticket, feedback.ticket)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(user)
            .append(rate)
            .append(date)
            .append(text)
            .append(ticket)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("user", user)
            .append("rate", rate)
            .append("date", date)
            .append("text", text)
            .append("ticket", ticket)
            .toString();
    }

    public static class Builder {

        private Long id;
        private User user;
        private Integer rate;
        private Date date;
        private String text;
        private Ticket ticket;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setRate(Integer rate) {
            this.rate = rate;
            return this;
        }

        public Builder setDate(Date date) {
            this.date = date;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setTicket(Ticket ticket) {
            this.ticket = ticket;
            return this;
        }

        public Feedback build() {
            return new Feedback(this);
        }
    }
}
