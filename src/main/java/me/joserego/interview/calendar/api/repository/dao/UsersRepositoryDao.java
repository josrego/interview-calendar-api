package me.joserego.interview.calendar.api.repository.dao;

import me.joserego.interview.calendar.api.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UsersRepositoryDao extends CrudRepository<User, Long> {
}
