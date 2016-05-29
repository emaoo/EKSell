package com.eksell.eksell.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Awesome on 5/27/2016.
 */
public class eMail {
    List<String> sentTo = new ArrayList<String>();
    private String sender;
    private String message;
    private String subject;

    public eMail () {}

    public eMail (List<String> sentTo, String sender, String message, String subject)
    {
        this.sentTo = sentTo;
        this.sender = sender;
        this.message = message;
        this.subject = subject;
    }

    public List<String> getRecipient()
    {
        return sentTo;
    }
    public void setRecipient(List<String> sentTo)
    {
        this.sentTo = sentTo;
    }

    public String getSender()
    {
        return sender;
    }
    public void setSender(String sender)
    {
        this.sender = sender;
    }
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
    public String getSubject()
    {
        return subject;
    }
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
}