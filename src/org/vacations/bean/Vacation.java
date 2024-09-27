package org.vacations.bean;

import java.sql.Date;

public class Vacation {
    private int id_vacation;
    private int id_user;
    private Date start_date;
    private Date end_date;
    private String comments;
    private String state;

    public Vacation() {
        
    }

    public Vacation(int id_vacation, int id_user, Date start_date, Date end_date, String comments, String state) {
        this.id_vacation = id_vacation;
        this.id_user = id_user;
        this.start_date = start_date;
        this.end_date = end_date;
        this.comments = comments;
        this.state = state;
    }

    public int getId_vacation() {
        return id_vacation;
    }

    public void setId_vacation(int id_vacation) {
        this.id_vacation = id_vacation;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    
}
