package me.joserego.interview.calendar.api.repository.dao;

import me.joserego.interview.calendar.api.entity.TimeSlot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface TimeSlotRepositoryDao extends CrudRepository<TimeSlot, Long> {
}
