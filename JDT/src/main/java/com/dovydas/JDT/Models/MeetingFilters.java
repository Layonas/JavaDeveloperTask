package com.dovydas.JDT.Models;

import java.util.Date;

public class MeetingFilters {
    private String Description;
    private String ResponsiblePerson;
    private String Category;
    private String Type;
    private Date StartDate;
    private Date EndDate;
    private int AttendeeCount;

    public MeetingFilters(String description, String responsiblePerson, String category, String type, Date startDate, Date endDate, int attendeeCount) {
        Description = description;
        ResponsiblePerson = responsiblePerson;
        Category = category;
        Type = type;
        StartDate = startDate;
        EndDate = endDate;
        AttendeeCount = attendeeCount;
    }

    public String getDescription() {
        return Description;
    }

    public String getResponsiblePerson() {
        return ResponsiblePerson;
    }

    public String getCategory() {
        return Category;
    }

    public String getType() {
        return Type;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public int getAttendeeCount() {
        return AttendeeCount;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        ResponsiblePerson = responsiblePerson;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public void setAttendeeCount(int attendeeCount) {
        AttendeeCount = attendeeCount;
    }
}
