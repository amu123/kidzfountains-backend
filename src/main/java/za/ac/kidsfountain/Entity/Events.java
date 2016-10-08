package za.ac.kidsfountain.Entity;

import javax.persistence.*;

/**
 * Created by tlharihani on 9/22/16.
 */
@Entity
@Table(name = "events")
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public long getId() {
        return id;
    }

    private String title;
    private String description;
    private String date;
    private String postedDate;
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Events() {
    }

    public String getTitle() {
        return title;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Events(String title, String description, String date, String postedDate,boolean isActive) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.postedDate = postedDate;
        this.isActive = isActive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }



}
