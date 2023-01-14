package tw.backend.model;

import java.sql.Date;

public class Ad {
    private int aid;
    private String type;
    private String typology;
    private String gender;
    private Double price;
    private String advertiser;
    private String email;
    private String local;
    private Date date;
    private String state;
    private String description;

    public Ad() {
        state = "inativo";
        date = new Date(System.currentTimeMillis());
    }

    public Ad(int value) {}

    /* sets */
    public void setAid(int aid) {
        this.aid = aid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTypology(String typology) {
        this.typology = typology;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /* gets */
    public int getAid() {
        return aid;
    }

    public String getType() {
        return type;
    }

    public String getTypology() {
        return typology;
    }

    public String getGender() {
        return gender;
    }

    public Double getPrice() {
        return price;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public String getEmail() {
        return email;
    }

    public String getLocal() {
        return local;
    }

    public Date getDate() {
        return date;
    }

    public String getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }
}
