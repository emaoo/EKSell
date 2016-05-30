package com.eksell.eksell.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an email to be sent
 *
 * @author Sean Meng
 * @version May 27, 2016
 *
 * @author Period - 3
 * @author Assignment - EKSell
 *
 * @sources None
 */
 
public class eMail {
    /**
     * List of emails the email is sent to
     */
    List<String> sentTo = new ArrayList<String>();

    /**
     * The sender of the email
     */
    private String sender;

    /**
     * Message of the email
     */
    private String message;

    /**
     * Subject of the email
     */
    private String subject;

    /**
     * Email constructor
     */
    public eMail () {}

    /**
     * Email constructor
     *
     * @param sentTo email sent to
     * @param sender email receiving
     * @param message message body
     * @param subject subject of email
     */
    public eMail (List<String> sentTo, String sender, String message, String subject)
    {
        this.sentTo = sentTo;
        this.sender = sender;
        this.message = message;
        this.subject = subject;
    }

    /**
     * Returns email recipient
     * @return email recipient
     */
    public List<String> getRecipient()
    {
        return sentTo;
    }

    /**
     * Emails of message recipients
     */
    public void setRecipient(List<String> sentTo)
    {
        this.sentTo = sentTo;
    }

    /**
     * Returns the sender
     * @return sender
     */
    public String getSender()
    {
        return sender;
    }

    /**
     * Sets the sender
     * @param sender the sender
     */
    public void setSender(String sender)
    {
        this.sender = sender;
    }

    /**
     * Returns the message of the email
     * @return the message of the email
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Sets the message of the email
     * @param message the message of the email
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * Gets the subject of the email
     * @return the subject of the email
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * Sets the subject of the email
     * @param subject of the email
     */
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
}
