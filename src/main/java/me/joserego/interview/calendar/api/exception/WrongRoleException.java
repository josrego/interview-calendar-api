package me.joserego.interview.calendar.api.exception;

import me.joserego.interview.calendar.api.model.UserDto;

public class WrongRoleException extends RuntimeException {
    private static final long serialVersionUID = -3610618723139156691L;

    public WrongRoleException(Long id, UserDto.Role expectedRole) {
        super(String.format("User %s does not have expected role: %s", id, expectedRole));
    }
}
