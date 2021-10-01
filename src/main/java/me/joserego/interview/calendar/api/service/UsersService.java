package me.joserego.interview.calendar.api.service;

import me.joserego.interview.calendar.api.model.TimeSlotsDto;
import me.joserego.interview.calendar.api.model.UserDto;
import me.joserego.interview.calendar.api.model.response.InterviewPossibilitiesResponseDto;

import java.util.List;

public interface UsersService {

    UserDto findUser(Long id);

    UserDto createUser(UserDto user);

    UserDto setTimeSlots(Long id, TimeSlotsDto timeslots);

    InterviewPossibilitiesResponseDto findPossibleInterviews(Long candidateId, List<Long> interviewers);
}
