package com.dovydas.JDT.Controller;

import com.dovydas.JDT.Models.*;
import com.dovydas.JDT.Register.MeetingRegister;
import com.dovydas.JDT.Register.PersonRegister;
import com.dovydas.JDT.Utils.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@RestController
public class JavaDeveloperTaskController {

    private static PersonRegister personRegister = new PersonRegister();

    private static MeetingRegister meetingRegister = new MeetingRegister();



    @RequestMapping
    public String meetings(){
        return meetingRegister.toString();
    }

    @GetMapping("/meeting")
    public ResponseEntity<String> getFilteredMeetings(@RequestBody MeetingFilters filters){
        try{
            List<Meeting> meetingList = meetingRegister.filterMeetings(personRegister.getPersonList(), filters);
            if(meetingList == null)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(meetingList);
            return new ResponseEntity<>(json, HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity<>("Unknown error occurred!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/meeting")
    public ResponseEntity<Meeting> createMeeting(@RequestBody Meeting meeting){

        try{
            meetingRegister.add(meeting);
            Utilities.PrintToJson(meetingRegister, "meetings.json");
            return new ResponseEntity<>(meeting, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/meeting")
    public ResponseEntity<String> deleteMeeting(@RequestBody MeetingDelete meetingDelete){
        Meeting meeting = meetingRegister.getByName(meetingDelete.getName());
        if(meeting == null){
            return new ResponseEntity<>("No meeting with that name!", HttpStatus.BAD_REQUEST);
        }
        try{

            if(meeting.getResponsiblePerson().equals(meetingDelete.getSender())){
                meetingRegister.deleteMeeting(meeting);
                Utilities.PrintToJson(meetingRegister, "meetings.json");
                return new ResponseEntity<>("Meeting deleted!", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("Person requesting the delete is not responsible for the meeting!", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            return new ResponseEntity<>("Unknown error occurred!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/person")
    public ResponseEntity<String> addPerson(@RequestBody PersonMeetingAdd personMeetingAdd){

        try{
            Person person = personRegister.getByName(personMeetingAdd.getName());
            if(person == null){
                person = new Person(personMeetingAdd.getName());
            }

            Meeting meeting = meetingRegister.getByDate(personMeetingAdd.getDate());
            if(meeting == null){
                return new ResponseEntity<>("No meeting starting in: " + personMeetingAdd.getDate().toString(), HttpStatus.BAD_REQUEST);
            }

            if(person.meetingsIntersect(meeting)){
                return new ResponseEntity<>("Date intersects with current persons meetings!", HttpStatus.BAD_REQUEST);
            } else {
                person.addMeeting(meeting);
                personRegister.add(person);
                Utilities.PrintToJson(personRegister, "persons.json");
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = ow.writeValueAsString(person);
                return new ResponseEntity<>(json, HttpStatus.ACCEPTED);
            }
        } catch (Exception e){
            return new ResponseEntity<>("Unknown error occurred!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/person")
    public ResponseEntity<String> deletePerson (@RequestBody PersonMeetingRemove personMeetingRemove){

        try{

            Person person = personRegister.getByName(personMeetingRemove.getPersonName());
            if(person == null){
                return new ResponseEntity<>("Person: " + personMeetingRemove.getPersonName() + " does not exits!", HttpStatus.BAD_REQUEST);
            }

            Meeting meeting = meetingRegister.getByName(personMeetingRemove.getMeetingName());
            if(meeting == null){
                return new ResponseEntity<>("Meeting: " + personMeetingRemove.getMeetingName() + " does not exits!", HttpStatus.BAD_REQUEST);
            }

            if(personRegister.removeMeetingForPerson(person, meeting)){
                Utilities.PrintToJson(personRegister, "persons.json");
            } else {
                return new ResponseEntity<>(person.getName() + " is responsible for the meeting!", HttpStatus.BAD_REQUEST);
            }

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(person);

            return new ResponseEntity<>(json, HttpStatus.ACCEPTED);

        }catch (Exception e){
            return new ResponseEntity<>("Uknown error occurred!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




}
