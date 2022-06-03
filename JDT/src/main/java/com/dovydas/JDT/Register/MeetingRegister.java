package com.dovydas.JDT.Register;

import com.dovydas.JDT.Models.Meeting;
import com.dovydas.JDT.Models.MeetingFilters;
import com.dovydas.JDT.Models.Person;
import com.dovydas.JDT.Utils.Utilities;

import java.util.*;
import java.util.Map.Entry;

public class MeetingRegister {
    private List<Meeting> meetings;

    public MeetingRegister() {
        this.meetings = new ArrayList<>();
        this.meetings = Utilities.ReadFromMeetingJSON("meetings.json");
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public MeetingRegister(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    public void add(Meeting meeting){
        this.meetings.add(meeting);
    }

    public Meeting getByName(String name){
        Meeting meeting = null;

        for (Meeting m : this.meetings){
            if(m.getName().equals(name)){
                meeting = m;
                break;
            }
        }

        return meeting;
    }

    public void deleteMeeting(Meeting meeting){
        this.meetings.remove(meeting);
    }

    public Meeting getByDate(Date date){
        Meeting meeting = null;

        for (Meeting m : this.meetings){
            if(m.getStartDate().compareTo(date) == 0){
                meeting = m;
                break;
            }
        }

        return meeting;
    }

    public List<Meeting> filterMeetings(List<Person> personList, MeetingFilters filters) {

        if(filters.getDescription() != null){
            return filterByDescription(filters.getDescription());
        }

        if(filters.getResponsiblePerson() != null){
            return filterByResponsiblePerson(filters.getResponsiblePerson());
        }

        if(filters.getCategory() != null){
            return filterByCategory(filters.getCategory());
        }

        if(filters.getType() != null){
            return filterByType(filters.getType());
        }

        if(filters.getStartDate() != null || filters.getEndDate() != null){
            return filterByDate(filters.getStartDate(), filters.getEndDate());
        }

        if(filters.getAttendeeCount() != 0){
            return filterByAttendeeCount(personList, filters.getAttendeeCount());
        }

        return null;
    }

    private List<Meeting> filterByAttendeeCount(List<Person> personList, int attendeeCount) {

        List<Meeting> meetingList = new ArrayList<>();

        Map<Meeting, Integer> meetingMap = new TreeMap<>();

        meetingMap = listMeetings();

        meetingMap = countMeetings(personList, meetingMap);

        for(Entry<Meeting, Integer> entry : meetingMap.entrySet())
            if(entry.getValue() >= attendeeCount)
                meetingList.add(entry.getKey());


        return meetingList;
    }

    private Map<Meeting, Integer> countMeetings(List<Person> personList, Map<Meeting, Integer> meetingMap) {

        for(Person p : personList)
            for(Meeting m : p.getMeetings())
            {
                int count = meetingMap.get(m);
                meetingMap.put(m, ++count);
            }

        return meetingMap;
    }

    private Map<Meeting, Integer> listMeetings() {
        Map<Meeting, Integer> meetingMap = new TreeMap<>();

        for(Meeting m : this.meetings)
            meetingMap.put(m, 0);

        return meetingMap;
    }


    private List<Meeting> filterByDate(Date startDate, Date endDate) {
        List<Meeting> meetingList = new ArrayList<>();

        for (Meeting m : this.meetings){
            if(startDate != null && endDate != null){
                if(m.getStartDate().compareTo(startDate) >= 0 && m.getEndDate().compareTo(endDate) <= 0)
                    meetingList.add(m);
            } else if(startDate == null && endDate != null){
                if(m.getEndDate().compareTo(endDate) <= 0)
                    meetingList.add(m);

            } else if (startDate != null && endDate == null) {
                if(m.getStartDate().compareTo(startDate) >= 0)
                    meetingList.add(m);
            }
        }

        return meetingList;
    }

    private List<Meeting> filterByType(Meeting.Type type) {
        List<Meeting> meetingList = new ArrayList<>();

        for (Meeting m : this.meetings){
            if(m.getType().equals(type)){
                meetingList.add(m);
            }
        }

        return meetingList;
    }

    private List<Meeting> filterByCategory(Meeting.Category category) {
        List<Meeting> meetingList = new ArrayList<>();

        for (Meeting m : this.meetings){
            if(m.getCategory().equals(category)){
                meetingList.add(m);
            }
        }

        return meetingList;
    }

    private List<Meeting> filterByResponsiblePerson(String responsiblePerson) {
        List<Meeting> meetingList = new ArrayList<>();

        for (Meeting m : this.meetings){
            if(m.getResponsiblePerson().equals(responsiblePerson)){
                meetingList.add(m);
            }
        }

        return meetingList;
    }

    private List<Meeting> filterByDescription(String description) {
        List<Meeting> meetingList = new ArrayList<>();

        for (Meeting m : this.meetings){
            if(m.getDescription().matches("(.*)"+description+"(.*)")){
                meetingList.add(m);
            }
        }

        return meetingList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Meetings: \n");
        for (Meeting m : this.meetings)
            builder.append(m.toString() + "\n");

        return builder.toString();
    }
}
