package com.epam.entity;

import java.sql.Blob;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "attachment")
public class Attachment {

    private Long id;
    private Blob blob;
    private Ticket ticket;
    private String name;

    public Attachment() {
    }

    private Attachment(Builder builder) {
        this.id = builder.id;
        this.blob = builder.blob;
        this.ticket = builder.ticket;
        this.name = builder.name;
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
    @Lob
    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder {

        private Long id;
        private Blob blob;
        private Ticket ticket;
        private String name;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setBlob(Blob blob) {
            this.blob = blob;
            return this;
        }

        public Builder setTicket(Ticket ticket) {
            this.ticket = ticket;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Attachment build() {
            return new Attachment(this);
        }
    }
}
