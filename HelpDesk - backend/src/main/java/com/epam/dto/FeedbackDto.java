package com.epam.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public class FeedbackDto {

    private Long id;
    private UserDto user;
    private Integer rate;
    private Date date;
    private String text;
    private TicketDto ticket;

    public FeedbackDto() {

    }

    private FeedbackDto(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.rate = builder.rate;
        this.date = builder.date;
        this.text = builder.text;
        this.ticket = builder.ticket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @NotNull
    @Range(min = 1, max = 5)
    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Size(max = 500)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TicketDto getTicket() {
        return ticket;
    }

    public void setTicket(TicketDto ticket) {
        this.ticket = ticket;
    }

    public static class Builder {

        private Long id;
        private UserDto user;
        private Integer rate;
        private Date date;
        private String text;
        private TicketDto ticket;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUser(UserDto user) {
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

        public Builder setTicket(TicketDto ticket) {
            this.ticket = ticket;
            return this;
        }

        public FeedbackDto build() {
            return new FeedbackDto(this);
        }
    }
}
