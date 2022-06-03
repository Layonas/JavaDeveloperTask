package com.dovydas.JDT.Models;

import java.util.Date;

public class PersonMeetingAdd {
    private String Name;
    private Date date;

    public PersonMeetingAdd(String name, Date date) {
        Name = name;
        this.date = date;
    }

    public String getName() {
        return Name;
    }

    public Date getDate() {
        return date;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
