package me.joserego.interview.calendar.api.repository.impl;

import me.joserego.interview.calendar.api.entity.TimeSlot;
import me.joserego.interview.calendar.api.entity.User;
import me.joserego.interview.calendar.api.repository.UsersRepository;
import me.joserego.interview.calendar.api.repository.dao.TimeSlotRepositoryDao;
import me.joserego.interview.calendar.api.repository.dao.UsersRepositoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
public class DefaultUsersRepository implements UsersRepository {

    @Autowired
    private UsersRepositoryDao dao;

    @Autowired
    private TimeSlotRepositoryDao timeSlotDao;

    @Override
    public Optional<User> getUser(Long id) {
        return dao.findById(id);
    }

    @Override
    public Stream<User> getUsers(List<Long> ids) {
        return StreamSupport
                .stream(dao.findAllById(ids).spliterator(), false);
    }

    @Override
    public User createUser(User user) {
        return dao.save(user);
    }

    @Override
    public User setUserTimeSlots(User user, List<TimeSlot> timeSlots) {
        user.getTimeslots()
                .forEach(timeSlotDao::delete);

        user.setTimeslots(timeSlots);
        return dao.save(user);
    }
}
