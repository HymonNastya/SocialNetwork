package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    @Expose
    @JsonProperty
    private int id;

    @Expose
    @JsonProperty
    private String firstName;

    @Expose
    @JsonProperty
    private String lastName;

    @JsonIgnore
    private transient String email;

    @Expose
    @JsonProperty
    private List<Message> messages;

    public User() {
        this.messages = new ArrayList<>();
    }

    public User(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.messages = new ArrayList<>();
    }

    // Getters and setters

    public void setId(int id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setMessages(List<Message> messages) {
        this.messages = messages != null ? new ArrayList<>(messages) : new ArrayList<>();
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public List<Message> getMessages() { return new ArrayList<>(messages); }

    public void addMessage(Message message) {
        messages.add(message);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", firstName='" + firstName + "', lastName='" +
                lastName + "', email='" + email + "', messages=" + messages + '}';
    }
}

