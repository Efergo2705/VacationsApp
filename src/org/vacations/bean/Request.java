package org.vacations.bean;

import java.sql.Date;

public class Request {
    private int id_request;
    private int id_user;
    private int id_vacation;
    private Date request_date;
    private Date request_state;

    public Request() {
    }

    public Request(int id_request, int id_user, int id_vacation, Date request_date, Date request_state) {
        this.id_request = id_request;
        this.id_user = id_user;
        this.id_vacation = id_vacation;
        this.request_date = request_date;
        this.request_state = request_state;
    }

    public int getId_request() {
        return id_request;
    }

    public void setId_request(int id_request) {
        this.id_request = id_request;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_vacation() {
        return id_vacation;
    }

    public void setId_vacation(int id_vacation) {
        this.id_vacation = id_vacation;
    }

    public Date getRequest_date() {
        return request_date;
    }

    public void setRequest_date(Date request_date) {
        this.request_date = request_date;
    }

    public Date getRequest_state() {
        return request_state;
    }

    public void setRequest_state(Date request_state) {
        this.request_state = request_state;
    }
    
    
}