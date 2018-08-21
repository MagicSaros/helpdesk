package com.epam.dto;

import java.sql.Timestamp;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CommentDto {

    private Long id;
    private UserDto user;
    private String text;
    private Timestamp date;
    private TicketDto ticket;

    public CommentDto() {

    }

    private CommentDto(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.text = builder.text;
        this.date = builder.date;
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

    @NotEmpty
    @Size(min = 1, max = 500)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
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
        private String text;
        private Timestamp date;
        private TicketDto ticket;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUser(UserDto user) {
            this.user = user;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setDate(Timestamp date) {
            this.date = date;
            return this;
        }

        public Builder setTicket(TicketDto ticket) {
            this.ticket = ticket;
            return this;
        }

        public CommentDto build() {
            return new CommentDto(this);
        }
    }
}
