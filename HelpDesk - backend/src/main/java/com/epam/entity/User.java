package com.epam.entity;

import com.epam.enums.UserRole;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "role_id")
    private UserRole role;

    private String email;

    private String password;

    @OneToMany(mappedBy = "assignee")
    private Set<Ticket> assignees = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<Ticket> owners = new HashSet<>();

    @OneToMany(mappedBy = "approver")
    private Set<Ticket> approvers = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Ticket> getAssignees() {
        return assignees;
    }

    public void setAssignees(Set<Ticket> assignees) {
        this.assignees = assignees;
    }

    public Set<Ticket> getOwners() {
        return owners;
    }

    public void setOwners(Set<Ticket> owners) {
        this.owners = owners;
    }

    public Set<Ticket> getApprovers() {
        return approvers;
    }

    public void setApprovers(Set<Ticket> approvers) {
        this.approvers = approvers;
    }

    public void addAssignee(Ticket ticket) {
        assignees.add(ticket);
    }

    public void removeAssignee(Ticket ticket) {
        assignees.remove(ticket);
    }

    public void addOwner(Ticket ticket) {
        assignees.add(ticket);
    }

    public void removeOwner(Ticket ticket) {
        assignees.remove(ticket);
    }

    public void addApprover(Ticket ticket) {
        assignees.add(ticket);
    }

    public void removeApprover(Ticket ticket) {
        assignees.remove(ticket);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                role == user.role &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, role, email, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                '}';
    }
}
