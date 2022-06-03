package com.dovydas.JDT.Models;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Meeting implements Comparable<Meeting>{

//    Name
//○ ResponsiblePerson
//○ Description
//○ Category (Fixed values - CodeMonkey / Hub / Short / TeamBuilding)
//○ Type (Fixed values - Live / InPerson)
//○ StartDate
//○ EndDate

    private String Name;
    private String ResponsiblePerson;
    private String Description;
    private String Category; // enum possibly
    private String Type; // enum possibly
    private Date StartDate;
    private Date EndDate;

    public Meeting(String name, String responsiblePerson, String description, String category, String type, Date startDate, Date endDate) {
        this.Name = name;
        this.ResponsiblePerson = responsiblePerson;
        this.Description = description;
        this.Category = category;
        this.Type = type;
        this.StartDate = startDate;
        this.EndDate = endDate;
    }

    public Meeting() {
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.ResponsiblePerson = responsiblePerson;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public void setCategory(String category) {
        this.Category = category;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public void setStartDate(Date startDate) {
        this.StartDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.EndDate = endDate;
    }

    public String getName() {
        return Name;
    }

    public String getResponsiblePerson() {
        return ResponsiblePerson;
    }

    public String getDescription() {
        return Description;
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Meeting){
            return this.Name.equals(((Meeting) obj).Name);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "name='" + Name + '\'' +
                ", responsiblePerson='" + ResponsiblePerson + '\'' +
                ", description='" + Description + '\'' +
                ", category='" + Category + '\'' +
                ", type='" + Type + '\'' +
                ", startDate=" + StartDate +
                ", endDate=" + EndDate +
                '}';
    }

    public int compareTo(Meeting other){
        return this.Name.compareTo(other.getName());
    }

}