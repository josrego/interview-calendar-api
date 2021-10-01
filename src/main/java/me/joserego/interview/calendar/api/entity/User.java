package me.joserego.interview.calendar.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.joserego.interview.calendar.api.model.TimeSlotDto;
import me.joserego.interview.calendar.api.model.UserDto;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<TimeSlot> timeslots;

    public User(String name, String role) {
        this.name = name;
        this.role = role;
        this.timeslots = Collections.emptyList();
    }

    public User(String name, String role, List<TimeSlot> timeslots) {
        this(name, role);
        this.timeslots = timeslots;
    }

    public List<TimeSlot> getBasicTimeSlots() {
        return this.timeslots.stream()
                .map(timeSlotEntity -> (TimeSlot) timeSlotEntity)
                .collect(Collectors.toList());
    }

    public UserDto toDto() {
        List<TimeSlotDto> timeslotsDtos = timeslots.stream()
                .map(TimeSlot::toDto)
                .collect(Collectors.toList());

        return new UserDto(
                this.id,
                this.name,
                UserDto.Role.valueOf(this.role),
                timeslotsDtos);
    }
}
