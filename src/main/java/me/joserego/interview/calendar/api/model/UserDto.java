package me.joserego.interview.calendar.api.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import me.joserego.interview.calendar.api.entity.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@JsonAutoDetect
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @JsonProperty
    private Long id;

    @NotEmpty
    @JsonProperty
    private String name;

    @JsonProperty
    @NotNull
    private Role role;

    @JsonProperty
    private List<TimeSlotDto> availableSlots;

    public UserDto(String name, String role) {
        this.name = name;
        this.role = Role.valueOf(role);
    }

    public User toEntity() {
        return new User(this.getName(), this.getRole().toString());
    }

    public enum Role {
        CANDIDATE,
        INTERVIEWER
    }
}
