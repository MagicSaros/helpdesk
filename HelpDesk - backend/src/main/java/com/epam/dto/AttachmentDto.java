package com.epam.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Blob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

public class AttachmentDto {

    private Long id;
    private Blob blob;
    private TicketDto ticket;
    private String name;

    public AttachmentDto() {
    }

    private AttachmentDto(Builder builder) {
        this.id = builder.id;
        this.blob = builder.blob;
        this.ticket = builder.ticket;
        this.name = builder.name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public TicketDto getTicket() {
        return ticket;
    }

    public void setTicket(TicketDto ticket) {
        this.ticket = ticket;
    }

    @NotEmpty
    @Pattern(regexp = "^([a-zA-Z0-9\\s_\\\\.\\-():])+(.pdf|.doc|.docx|.png|.jpeg|.jpg)$", flags = Flag.CASE_INSENSITIVE)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder {

        private Long id;
        private Blob blob;
        private TicketDto ticket;
        private String name;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setBlob(Blob blob) {
            this.blob = blob;
            return this;
        }

        public Builder setTicket(TicketDto ticket) {
            this.ticket = ticket;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public AttachmentDto build() {
            return new AttachmentDto(this);
        }
    }
}
