package com.dovydas.JDT.Models;

import java.util.Date;

public class MeetingFilters {
    private String Description;
    private String ResponsiblePerson;
    private Meeting.Category Category;
    private Meeting.Type Type;
    private Date StartDate;
    private Date EndDate;
    private int AttendeeCount;

    public MeetingFilters(String description, String responsiblePerson, Meeting.Category category, Meeting.Type type, Date startDate, Date endDate, int attendeeCount) {
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

    public Meeting.Category getCategory() {
        return Category;
    }

    public Meeting.Type getType() {
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

    public void setCategory(Meeting.Category category) {
        Category = category;
    }

    public void setType(Meeting.Type type) {
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
