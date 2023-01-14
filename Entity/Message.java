package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.Entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "messages")
public class Message {
    @Column(name = "sender")
    private String sender;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private Date date;

    @Column(name = "aid")
    private long aid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Ad ad;

    public Message() {
        date = new Date(System.currentTimeMillis());
    }

    /* sets */

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }
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

    public long getAid() {
        return aid;
    }

    @Override
    public String toString() {
        return "Message[sender=" + sender +
                ", content=" + content +
                ", date=" + date +
                ", aid=" + aid + "]";
    }
}
