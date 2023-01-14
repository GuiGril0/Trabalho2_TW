package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.Entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "advertisement")
public class Ad {
    @Column(name = "advertiser")
    private String advertiser;

    @Column(name = "typead")
    private String type;

    @Column(name = "statead")
    private String state;

    @Column(name = "price")
    private double price;

    @Column(name = "gender")
    private String gender;

    @Column(name = "localad")
    private String local;

    @Column(name = "typology")
    private String typology;

    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "aid", updatable = false, nullable = false)
    private long aid;

/*    @OneToMany(mappedBy = "ad")
    private List<Message> messages; */

    public Ad() {
        date = new Date(System.currentTimeMillis());
    }

    /* sets */
    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setTypology(String typology) {
        this.typology = typology;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /* gets */

    public String getAdvertiser() {
        return advertiser;
    }

    public String getType() {
        return type;
    }

    public String getState() {
        return state;
    }

    public double getPrice() {
        return price;
    }

    public String getGender() {
        return gender;
    }

    public String getLocal() {
        return local;
    }

    public String getTypology() {
        return typology;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public long getAid() {
        return aid;
    }

    @Override
    public String toString() {
        return "Ad[advertiser=" + advertiser +
                ", type=" + type +
                ", state=" + state +
                ", price=" + price +
                ", gender=" + gender +
                ", local=" + local +
                ", typology=" + typology +
                ", date=" + date +
                ", description=" + description +
                ", aid=" + aid + "]";
    }
}
