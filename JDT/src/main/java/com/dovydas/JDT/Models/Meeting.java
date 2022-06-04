package com.dovydas.JDT.Models;

import java.util.Date;

public class Meeting implements Comparable<Meeting>{

    public enum Category{
    CodeMonkey,
    Hub,
    Short,
    TeamBuilding
    }

    public enum Type{
        Live,
        InPerson
    }


    private String Name;
    private String ResponsiblePerson;
    private String Description;
    private Category Category; // enum possibly
    private Type Type; // enum possibly
    private Date StartDate;
    private Date EndDate;

    public Meeting(String name, String responsiblePerson, String description, Category category, Type type, Date startDate, Date endDate) {
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

    public void setCategory(Category category) {
        this.Category = category;
    }

    public void setType(Type type) {
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

    public Category getCategory() {
        return Category;
    }

    public Type getType() {
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
