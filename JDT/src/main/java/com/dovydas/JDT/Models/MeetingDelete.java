package com.dovydas.JDT.Models;

public class MeetingDelete {
    private String Name;
    private String Sender;

    public MeetingDelete(String name, String sender) {
        Name = name;
        Sender = sender;
    }

    public String getName() {
        return Name;
    }

    public String getSender() {
        return Sender;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setSender(String sender) {
        Sender = sender;
    }
}
