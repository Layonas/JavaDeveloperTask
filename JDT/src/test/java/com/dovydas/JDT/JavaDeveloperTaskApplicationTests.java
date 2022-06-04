package com.dovydas.JDT;

import com.dovydas.JDT.Models.*;
import com.dovydas.JDT.Register.MeetingRegister;
import com.dovydas.JDT.Register.PersonRegister;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JavaDeveloperTaskApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	MeetingRegister meetingRegister = new MeetingRegister();
	PersonRegister personRegister = new PersonRegister();

	@Test
	public void shouldCreateMeeting() throws Exception{
		Date st = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-02-02 11:00:00");
		Date en = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-02-02 13:00:00");
		Meeting meeting = new Meeting("Work", "Adomas", "Privaloma dalyvauti visiems!", Meeting.Category.TeamBuilding, Meeting.Type.Live, st, en);
		this.mockMvc.perform(post("/meeting").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(meeting)))
				.andExpect(status().isCreated())
				.andDo(print());
	}

	@Test
	public void shouldDeleteMeetingReturnWrongRPName() throws Exception{
		MeetingDelete delete = new MeetingDelete("LandMine", "Petras");
		this.mockMvc.perform(delete("/meeting").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(delete)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("Person requesting the delete is not responsible for the meeting!")))
				.andDo(print());
	}

	@Test
	public void shouldDeleteMeetingReturnWrongMeetingName() throws Exception{
		MeetingDelete delete = new MeetingDelete("WROKER", "Adomas");
		this.mockMvc.perform(delete("/meeting").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(delete)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("No meeting with that name!")))
				.andDo(print());
	}

	@Test
	public void shouldDeleteMeeting() throws Exception{
		MeetingDelete delete = new MeetingDelete("Work", "Adomas");
		this.mockMvc.perform(delete("/meeting").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(delete)))
				.andExpect(status().isAccepted()).andDo(print());
	}

	@Test
	public void shouldPutPersonToAMeeting() throws Exception{
		Date st = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-02-02 02:00:00");
		PersonMeetingAdd add = new PersonMeetingAdd("Petras", st);
		this.mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(add)))
				.andExpect(status().isAccepted())
				.andDo(print());
	}

	@Test
	public void shouldMeetingNotExistOnPersonPut() throws Exception {
		Date st = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-02-02 00:00:00");
		PersonMeetingAdd add = new PersonMeetingAdd("Petras", st);
		this.mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(add)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("No meeting starting in: " + st.toString())))
				.andDo(print());
	}

	@Test
	public void shouldMeetingIntersectWithPersonsMeetings() throws Exception{
		Date st = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-02-02 02:00:00");
		PersonMeetingAdd add = new PersonMeetingAdd("Petras", st);
		this.mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(add)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("Date intersects with current persons meetings!")))
				.andDo(print());
	}

	@Test
	public void shouldDeletePersonFromAMeeting() throws Exception{
		PersonMeetingRemove remove = new PersonMeetingRemove("JavaMeet", "Petras");
		this.mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(remove)))
				.andExpect(status().isAccepted())
				.andDo(print());
	}

	@Test
	public void shouldDeletePersonFromAMeetingReturnPersonNotExists() throws Exception{
		PersonMeetingRemove remove = new PersonMeetingRemove("JavaMeet", "Anonimas");
		this.mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(remove)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("Person: " + remove.getPersonName() + " does not exits!")))
				.andDo(print());
	}

	@Test
	public void shouldDeletePersonFromAMeetingReturnMeetingNotExists() throws Exception{
		PersonMeetingRemove remove = new PersonMeetingRemove("JavaMeetukas", "Petras");
		this.mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(remove)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("Meeting: " + remove.getMeetingName() + " does not exits!")))
				.andDo(print());
	}

	@Test
	public void shouldDeletePersonFromAMeetingReturnRPCannotBeRemoved() throws Exception{
		PersonMeetingRemove remove = new PersonMeetingRemove("JavaMeet", "Jonas");
		this.mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(remove)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString(remove.getPersonName() + " is responsible for the meeting!")))
				.andDo(print());
	}

	@Test
	public void shouldFilterByDescription() throws Exception{
		MeetingFilters filters = new MeetingFilters("java", null, null, null, null, null, 0);
		List<Meeting> list = meetingRegister.filterMeetings(personRegister.getPersonList(), filters);
		this.mockMvc.perform(get("/meeting").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(filters)))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.size()").value(list.size()))
				.andDo(print());
	}

	@Test
	public void shouldFilterByResponsiblePerson() throws Exception{
		MeetingFilters filters = new MeetingFilters(null, "Jonas", null, null, null, null, 0);
		List<Meeting> list = meetingRegister.filterMeetings(personRegister.getPersonList(), filters);
		this.mockMvc.perform(get("/meeting").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(filters)))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.size()").value(list.size()))
				.andDo(print());
	}

	@Test
	public void shouldFilterByCategory() throws Exception{
		MeetingFilters filters = new MeetingFilters(null, null, Meeting.Category.TeamBuilding, null, null, null, 0);
		List<Meeting> list = meetingRegister.filterMeetings(personRegister.getPersonList(), filters);
		this.mockMvc.perform(get("/meeting").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(filters)))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.size()").value(list.size()))
				.andDo(print());
	}

	@Test
	public void shouldFilterByType() throws Exception{
		MeetingFilters filters = new MeetingFilters(null, null, null, Meeting.Type.Live, null, null, 0);
		List<Meeting> list = meetingRegister.filterMeetings(personRegister.getPersonList(), filters);
		this.mockMvc.perform(get("/meeting").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(filters)))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.size()").value(list.size()))
				.andDo(print());
	}

	@Test
	public void shouldFilterByStartDate() throws Exception{
		Date st = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-02-02 02:00:00");
		MeetingFilters filters = new MeetingFilters(null, null, null, null, st, null, 0);
		List<Meeting> list = meetingRegister.filterMeetings(personRegister.getPersonList(), filters);
		this.mockMvc.perform(get("/meeting").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(filters)))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.size()").value(list.size()))
				.andDo(print());
	}

	@Test
	public void shouldFilterByEndDate() throws Exception{
		Date en = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-02-03 02:00:00");
		MeetingFilters filters = new MeetingFilters(null, null, null, null, null, en, 0);
		List<Meeting> list = meetingRegister.filterMeetings(personRegister.getPersonList(), filters);
		this.mockMvc.perform(get("/meeting").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(filters)))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.size()").value(list.size()))
				.andDo(print());
	}

	@Test
	public void shouldFilterByStartAndEndDates() throws Exception{
		Date st = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-02-01 02:00:00");
		Date en = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-02-03 02:00:00");

		MeetingFilters filters = new MeetingFilters(null, null, null, null, st, en, 0);
		List<Meeting> list = meetingRegister.filterMeetings(personRegister.getPersonList(), filters);
		this.mockMvc.perform(get("/meeting").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(filters)))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.size()").value(list.size()))
				.andDo(print());
	}

	@Test
	public void shouldFilterByAttendeeCount() throws Exception{
		MeetingFilters filters = new MeetingFilters(null, null, null, null, null, null, 2);
		List<Meeting> list = meetingRegister.filterMeetings(personRegister.getPersonList(), filters);
		this.mockMvc.perform(get("/meeting").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(filters)))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.size()").value(list.size()))
				.andDo(print());
	}

	@Test
	public void shouldFilterReturnsNull() throws Exception {
		MeetingFilters filters = new MeetingFilters(null, null, null, null, null, null, 0);
		List<Meeting> list = meetingRegister.filterMeetings(personRegister.getPersonList(), filters);
		this.mockMvc.perform(get("/meeting").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(filters)))
				.andExpect(status().isNoContent())
				.andDo(print());

		assertThat(list).isEqualTo(null);
	}





}
