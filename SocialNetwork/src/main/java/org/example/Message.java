package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable {
    @Expose
    @JsonProperty
    private String subject;

    @Expose
    @JsonProperty
    private String text;

    @Expose
    @JsonProperty
    private Date date;


    public Message() {}

    public Message(String subject, String text, Date date) {
        this.subject = subject;
        this.text = text;
        this.date = date != null ? (Date) date.clone() : new Date();
    }

    // Getters / setters

    public void setSubject(String subject) { this.subject = subject; }
    public void setText(String text) { this.text = text; }
    public void setDate(Date date) { this.date = date != null ? (Date) date.clone() : null; }

    public String getSubject() { return subject; }
    public String getText() { return text; }
    public Date getDate() { return date != null ? (Date) date.clone() : null; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "Message{subject='" + subject + "', text='" + text +
                "', date=" + (date != null ? sdf.format(date) : "null") + '}';
    }
}
