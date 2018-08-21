package com.epam.dto;

import java.sql.Timestamp;
import javax.validation.constraints.NotEmpty;

public class HistoryDto {

    private Long id;
    private TicketDto ticket;
    private Timestamp date;
    private String action;
    private UserDto user;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TicketDto getTicket() {
        return ticket;
    }

    public void setTicket(TicketDto ticket) {
        this.ticket = ticket;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @NotEmpty
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @NotEmpty
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HistoryDto() {

    }

    private HistoryDto(Builder builder) {
        this.id = builder.id;
        this.ticket = builder.ticket;
        this.date = builder.date;
        this.action = builder.action;
        this.user = builder.user;
        this.description = builder.description;
    }

    public static class Builder {

        private Long id;
        private TicketDto ticket;
        private Timestamp date;
        private String action;
        private UserDto user;
        private String description;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setTicket(TicketDto ticket) {
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

        public Builder setUser(UserDto user) {
            this.user = user;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public HistoryDto build() {
            return new HistoryDto(this);
        }
    }
}
