package com.epam.entity;

import com.epam.enums.UserRole;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "user")
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private UserRole role;
    private String email;
    private String password;
    private Set<Ticket> assignedTickets;
    private Set<Ticket> ownedTickets;
    private Set<Ticket> approvedTickets;

    public User() {
    }

    private User(final Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.role = builder.role;
        this.email = builder.email;
        this.password = builder.password;
        this.assignedTickets = builder.assignedTickets;
        this.ownedTickets = builder.ownedTickets;
        this.approvedTickets = builder.approvedTickets;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "role_id")
    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "assignee")
    public Set<Ticket> getAssignedTickets() {
        return assignedTickets;
    }

    public void setAssignedTickets(Set<Ticket> assignedTickets) {
        this.assignedTickets = assignedTickets;
    }

    @OneToMany(mappedBy = "owner")
    public Set<Ticket> getOwnedTickets() {
        return ownedTickets;
    }

    public void setOwnedTickets(Set<Ticket> ownedTickets) {
        this.ownedTickets = ownedTickets;
    }

    @OneToMany(mappedBy = "approver", fetch = FetchType.LAZY)
    public Set<Ticket> getApprovedTickets() {
        return approvedTickets;
    }

    public void setApprovedTickets(Set<Ticket> approvedTickets) {
        this.approvedTickets = approvedTickets;
    }

    public void addAssignee(Ticket ticket) {
        assignedTickets.add(ticket);
    }

    public void removeAssignee(Ticket ticket) {
        assignedTickets.remove(ticket);
    }

    public void addOwner(Ticket ticket) {
        assignedTickets.add(ticket);
    }

    public void removeOwner(Ticket ticket) {
        assignedTickets.remove(ticket);
    }

    public void addApprover(Ticket ticket) {
        assignedTickets.add(ticket);
    }

    public void removeApprover(Ticket ticket) {
        assignedTickets.remove(ticket);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return new EqualsBuilder()
            .append(id, user.id)
            .append(firstName, user.firstName)
            .append(lastName, user.lastName)
            .append(role, user.role)
            .append(email, user.email)
            .append(password, user.password)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(firstName)
            .append(lastName)
            .append(role)
            .append(email)
            .append(password)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("firstName", firstName)
            .append("lastName", lastName)
            .append("role", role)
            .append("email", email)
            .toString();
    }

    //todo take a look on appache builder and hashcode

    public static class Builder {

        private Long id;
        private String firstName;
        private String lastName;
        private UserRole role;
        private String email;
        private String password;
        private Set<Ticket> assignedTickets;
        private Set<Ticket> ownedTickets;
        private Set<Ticket> approvedTickets;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setRole(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setAssignedTickets(Set<Ticket> assignedTickets) {
            this.assignedTickets = assignedTickets;
            return this;
        }

        public Builder setOwnedTickets(Set<Ticket> ownedTickets) {
            this.ownedTickets = ownedTickets;
            return this;
        }

        public Builder setApprovedTickets(Set<Ticket> approvedTickets) {
            this.approvedTickets = approvedTickets;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
