package tw.backend.model;

import java.sql.Date;

public class Message {
    private String sender;
    private String content;
    private Date date;

    public Message() { date = new Date(System.currentTimeMillis()); }

    public Message(int value) {}
    /* sets*/

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) { this.date = date; }
    /* gets */

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }
}
