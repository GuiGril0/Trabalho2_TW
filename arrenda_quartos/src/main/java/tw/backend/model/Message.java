package tw.backend.model;

import java.sql.Date;

public class Message {
    private String sender;
    private String content;
    private Date date;
    private int ad_aid;

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

    public void setAd_aid(int ad_aid) { this.ad_aid = ad_aid; }
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

    public int getAd_aid() { return ad_aid; }
}
