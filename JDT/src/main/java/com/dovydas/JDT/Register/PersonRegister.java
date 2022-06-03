package com.dovydas.JDT.Register;

import com.dovydas.JDT.Models.Meeting;
import com.dovydas.JDT.Models.Person;
import com.dovydas.JDT.Utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class PersonRegister {
    private List<Person> personList;

    public PersonRegister(){
        this.personList = Utilities.ReadFromPersonJSON("persons.json");
    }
    public PersonRegister(List<Person> personList) {
        this.personList = personList;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public Person getByName(String name){
        Person person = null;

        for (Person p : this.personList){
            if(p.getName().equals(name)){
                person = p;
                break;
            }
        }

        return person;
    }

    public void add(Person person){
        int index = this.personList.indexOf(person);

        if(index == -1){
            this.personList.add(person);
        } else {
            this.personList.set(index, person);
        }

    }

    public boolean removeMeetingForPerson(Person person, Meeting meeting) {

        if(meeting.getResponsiblePerson().equals(person.getName())){
            return false;
        }

        int index = this.personList.indexOf(person);
        List<Meeting> meetings = person.getMeetings();

        if(meetings.remove(meeting)){
            person.setMeetings(meetings);

            this.personList.set(index, person);
        }

        return true;

    }
}
