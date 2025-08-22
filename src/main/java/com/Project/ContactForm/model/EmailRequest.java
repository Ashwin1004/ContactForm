package com.Project.ContactForm.model;

public class EmailRequest {
    private String recipientEmail; // Where to send
    private String userEmail;      // Reply-To user
    private String subject;
    private String body;

    public EmailRequest() {}

    public EmailRequest(String recipientEmail, String userEmail, String subject, String body) {
        this.recipientEmail = recipientEmail;
        this.userEmail = userEmail;
        this.subject = subject;
        this.body = body;
    }

    public String getRecipientEmail() { return recipientEmail; }
    public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}
