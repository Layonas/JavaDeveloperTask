package com.dovydas.JDT.Models;

public class PersonMeetingRemove {

    private String MeetingName;
    private String PersonName;

    public PersonMeetingRemove(String meetingName, String personName) {
        MeetingName = meetingName;
        PersonName = personName;
    }

    public String getMeetingName() {
        return MeetingName;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setMeetingName(String meetingName) {
        MeetingName = meetingName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }
}
