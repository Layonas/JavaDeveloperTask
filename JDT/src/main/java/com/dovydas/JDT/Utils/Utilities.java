package com.dovydas.JDT.Utils;

import com.dovydas.JDT.Models.Meeting;
import com.dovydas.JDT.Models.Person;
import com.dovydas.JDT.Register.MeetingRegister;
import com.dovydas.JDT.Register.PersonRegister;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utilities {

    public static void PrintToJson(MeetingRegister meetingRegister, String path){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(path), meetingRegister.getMeetings());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static List<Meeting> ReadFromMeetingJSON(String path){
        List<Meeting> meetings = new ArrayList<>();

        File f = new File(path);
        if(f.exists() && f.length() != 0) {

            Path of = Path.of(path);
            try{
                ObjectMapper mapper = new ObjectMapper();

                List<Meeting> temp = Arrays.asList(mapper.readValue(of.toFile(), Meeting[].class));

                meetings.addAll(temp);

            } catch (Exception e){
                try {
                    ObjectMapper mapper = new ObjectMapper();

                    Meeting meeting = mapper.readValue(of.toFile(), Meeting.class);

                    meetings.add(meeting);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }

        return meetings;
    }

    public static void PrintToJson(PersonRegister personRegister, String path){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(path), personRegister.getPersonList());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static List<Person> ReadFromPersonJSON(String path){
        List<Person> personList = new ArrayList<>();

        File f = new File(path);
        if(f.exists() && f.length() != 0) {

            Path of = Path.of(path);
            try{
                ObjectMapper mapper = new ObjectMapper();

                List<Person> temp = Arrays.asList(mapper.readValue(of.toFile(), Person[].class));

                personList.addAll(temp);

            } catch (Exception e){
                try {
                    ObjectMapper mapper = new ObjectMapper();

                    Person person = mapper.readValue(of.toFile(), Person.class);

                    personList.add(person);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }

        return personList;
    }
}
