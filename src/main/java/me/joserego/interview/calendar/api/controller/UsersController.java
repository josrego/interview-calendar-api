package me.joserego.interview.calendar.api.controller;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import me.joserego.interview.calendar.api.model.TimeSlotsDto;
import me.joserego.interview.calendar.api.model.UserDto;
import me.joserego.interview.calendar.api.model.request.InterviewPossibilitiesRequestDto;
import me.joserego.interview.calendar.api.model.response.InterviewPossibilitiesResponseDto;
import me.joserego.interview.calendar.api.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@Controller
@RequestMapping(UsersController.URL_MAPPING)
public class UsersController extends AbstractController {

    public static final String URL_MAPPING = "/users";

    @Autowired
    private UsersService service;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserDto> create(@RequestBody @Valid @NotNull UserDto user) {
        log.debug("Creating user " + user.toString());
        UserDto createdUser = service.createUser(user);

        log.info("Created user " + createdUser.toString());

        return created(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable("id") Long id) {
        UserDto user = service.findUser(id);

        return successful(user);
    }

    @PutMapping(
            value = "/timeslots/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserDto> setTimeSlots(@PathVariable("id") Long id,
                                                @RequestBody @Valid @NotNull TimeSlotsDto timeslots) {
        log.debug("Setting timeout for user " + id + " and timeslots " + timeslots.toString());
        UserDto userWithTimeSlots = service.setTimeSlots(id, timeslots);

        log.info("Updated timeslots for user: " + userWithTimeSlots.toString());
        return successful(userWithTimeSlots);
    }


    @PostMapping(
            value = "/interviews_availability",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getPossibleInterviews(@RequestBody @Valid @NotNull
                                                            InterviewPossibilitiesRequestDto requestDto) {
        log.debug("Retrieving possible interviews for request " + requestDto.toString());

        InterviewPossibilitiesResponseDto responseDto = service.findPossibleInterviews(requestDto.getCandidate(),
                requestDto.getInterviewers());
        log.debug("Retrieved possible interviews for request " + requestDto
                + " with response " + responseDto.toString());
        return successful(responseDto);
    }
}
