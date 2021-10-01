package me.joserego.interview.calendar.api.repository;

import me.joserego.interview.calendar.api.entity.TimeSlot;
import me.joserego.interview.calendar.api.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface UsersRepository {
    Optional<User> getUser(Long id);

    Stream<User> getUsers(List<Long> ids);

    User createUser(User user);

    User setUserTimeSlots(User user, List<TimeSlot> timeSlots);

}
