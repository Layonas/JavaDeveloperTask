package com.dovydas.JDT.Models;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Person {

    private String Name;
    private List<Meeting> meetings = new ArrayList<>();

    public Person(){

    }

    public Person(String name, List<Meeting> meetings) {
        Name = name;
        this.meetings = meetings;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Person){
            if(this.Name.equals(((Person) obj).Name))
                return true;
        }
        return false;
    }

    public Person(String name) {
        Name = name;
        this.meetings = new ArrayList<>();
    }

    public String getName() {
        return Name;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    public boolean meetingsIntersect(Meeting meeting){

        for(Meeting m : this.meetings){
            //if the next task starts and end in the middle of the first task
            if(m.getStartDate().compareTo(meeting.getStartDate()) <= 0 && m.getEndDate().compareTo(meeting.getEndDate()) >= 0)
                return true;

            //if the next task start in the middle of the first task
            if(m.getStartDate().compareTo(meeting.getStartDate()) <= 0 && m.getEndDate().compareTo(meeting.getEndDate()) <= 0 && m.getEndDate().compareTo(meeting.getStartDate()) >= 0)
                return true;

            //if the next task ends in the middle of the first task
            if(m.getStartDate().compareTo(meeting.getStartDate()) >= 0 && m.getEndDate().compareTo(meeting.getEndDate()) >= 0 && m.getStartDate().compareTo(meeting.getEndDate()) <= 0)
                return true;

            //if the first task is in the middle of the next task
            if(m.getStartDate().compareTo(meeting.getStartDate()) >= 0 && m.getStartDate().compareTo(meeting.getEndDate()) <= 0)
                return true;

            //if the first task ended in the middle of the next task
            if(m.getEndDate().compareTo(meeting.getEndDate()) <= 0 && m.getEndDate().compareTo(meeting.getStartDate()) >= 0)
                return true;
        }


        return false;
    }

    public void addMeeting(Meeting meeting){
        this.meetings.add(meeting);
    }

}
