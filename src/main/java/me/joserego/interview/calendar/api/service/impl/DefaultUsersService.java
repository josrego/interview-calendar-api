package me.joserego.interview.calendar.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.joserego.interview.calendar.api.entity.TimeSlot;
import me.joserego.interview.calendar.api.entity.User;
import me.joserego.interview.calendar.api.exception.WrongRoleException;
import me.joserego.interview.calendar.api.model.TimeSlotsDto;
import me.joserego.interview.calendar.api.model.UserDto;
import me.joserego.interview.calendar.api.model.response.InterviewPossibilitiesResponseDto;
import me.joserego.interview.calendar.api.repository.UsersRepository;
import me.joserego.interview.calendar.api.service.UsersService;
import me.joserego.interview.calendar.api.util.DateUtils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class DefaultUsersService implements UsersService {

    @Autowired
    private UsersRepository repository;

    @Override
    public UserDto findUser(Long id) {
        User user = getUserFromRepo(id);

        return user.toDto();
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        return repository.createUser(userDto.toEntity())
                .toDto();
    }

    @Override
    @Transactional
    public UserDto setTimeSlots(Long userId, TimeSlotsDto timeslots) {
        User user = getUserFromRepo(userId);

        return repository.setUserTimeSlots(user,
                        timeslots.toTimeSlotsEntities(user))
                .toDto();
    }

    @Override
    public InterviewPossibilitiesResponseDto findPossibleInterviews(Long candidateId,
                                                                    List<Long> interviewersIds) {
        User candidate = getCandidateUser(candidateId);
        List<User> interviewers = getInterviewersUsers(interviewersIds);

        List<TimeSlot> possibleInterviewTimeSlots = new ArrayList<>();

        for (TimeSlot candidateTimeSlot : candidate.getTimeslots()) {
            List<TimeSlot> allPossibleTimeSlots = getAllPossibleInterviewSlots(candidateTimeSlot);

            getPossibleInterviewTimeSlot(interviewers, allPossibleTimeSlots)
                    .ifPresent(possibleInterviewTimeSlots::add);
        }

        InterviewPossibilitiesResponseDto response = InterviewPossibilitiesResponseDto.from(candidate, possibleInterviewTimeSlots);
        logPossibleInterviewSlots(candidateId, interviewersIds, response);

        return response;
    }

    private User getUserFromRepo(Long id) {
        return repository.getUser(id)
                .orElseThrow(()
                        -> new EntityNotFoundException(String.format("No user found with id %s", id)));
    }


    private User getCandidateUser(Long candidateId) {
        User candidate = getUserFromRepo(candidateId);
        verifyRole(candidate, UserDto.Role.CANDIDATE);

        return candidate;
    }

    private List<User> getInterviewersUsers(List<Long> interviewersIds) {
        return repository.getUsers(interviewersIds)
                .peek(user -> verifyRole(user, UserDto.Role.INTERVIEWER))
                .collect(Collectors.toList());
    }

    private void verifyRole(User user, UserDto.Role expectedRole) {
        if (!user.getRole().equals(expectedRole.name())) {
            throw new WrongRoleException(user.getId(), expectedRole);
        }
    }

    /**
     * Gets all possible 1 hour time slots for the available time slot of the candidate
     *
     * @param candidateTimeSlot Time slot that the candidate is available
     * @return all 1 hour time slots from the given candidate time slot
     */
    private List<TimeSlot> getAllPossibleInterviewSlots(TimeSlot candidateTimeSlot) {
        long limit = ChronoUnit.HOURS.between(candidateTimeSlot.getStartingTime(), candidateTimeSlot.getEndingTime());
        return Stream.iterate(1, n -> n + 1)
                .limit(limit)
                .map(n -> new TimeSlot(
                        DateUtils.addHours(candidateTimeSlot.getStartingTime(), n - 1),
                        DateUtils.addHours(candidateTimeSlot.getStartingTime(), n)
                )).collect(Collectors.toList());
    }

    /**
     * Iterates through all 1 hour time slots and checks if the interviewers all have a matching time slot.
     * If so, the first one is returned.
     *
     * @param interviewers         list of interviewers entity objects
     * @param allPossibleTimeSlots list of all 1 hour time slots
     * @return optional matching time slot
     */
    private Optional<TimeSlot> getPossibleInterviewTimeSlot(List<User> interviewers, List<TimeSlot> allPossibleTimeSlots) {
        for (TimeSlot possibleTimeSlot : allPossibleTimeSlots) {
            boolean haveMatchingTimeSlot = interviewers.stream()
                    .map(User::getBasicTimeSlots)
                    .allMatch(interviewerTimeSlots
                            -> haveMatchingTimeSlot(interviewerTimeSlots, possibleTimeSlot));

            if (haveMatchingTimeSlot) {
                return Optional.of(possibleTimeSlot);
            }
        }

        return Optional.empty();
    }

    /**
     * Checks if any interviewer time slots are within range of the proposed 1 hour interview time slot
     *
     * @param interviewerTimeSlots all interviewer time slots
     * @param possibleTimeSlot     proposed 1 hour interview time slot
     * @return boolean if match was found or not
     */
    private boolean haveMatchingTimeSlot(List<TimeSlot> interviewerTimeSlots, TimeSlot possibleTimeSlot) {
        return interviewerTimeSlots
                .stream()
                .anyMatch(timeslot -> DateUtils.isWithinRange(possibleTimeSlot, timeslot));
    }

    private void logPossibleInterviewSlots(Long candidateId,
                                           List<Long> interviewersIds,
                                           InterviewPossibilitiesResponseDto possibleInterviewTimeSlots) {
        if (log.isDebugEnabled()) {
            String interviewIdsString = interviewersIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            log.debug(String.format("Interview time slot possibilities for candidate: %s and interviewers: %s"
                    + " with result %s", candidateId, interviewIdsString, possibleInterviewTimeSlots.toString()));
        }
    }
}
