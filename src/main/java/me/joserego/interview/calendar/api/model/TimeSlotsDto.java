package me.joserego.interview.calendar.api.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.joserego.interview.calendar.api.entity.TimeSlot;
import me.joserego.interview.calendar.api.entity.User;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonAutoDetect
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotsDto {

    @JsonProperty
    @Valid
    private List<TimeSlotDto> availableSlots;

    public List<TimeSlot> toTimeSlotsEntities(User user) {
        return availableSlots.stream()
                .map(timeslot -> timeslot.toTimeSlotEntity(user))
                .collect(Collectors.toList());
    }

    @JsonIgnore
    @AssertTrue
    public boolean isValidSlots() {
        return this.availableSlots.stream()
                .flatMap(slot -> Stream.of(slot.getStartingTime(), slot.getEndingTime()))
                .allMatch(slotTime -> LocalDateTime.ofInstant(slotTime, ZoneOffset.UTC)
                        .getMinute()== 0);
    }
}
