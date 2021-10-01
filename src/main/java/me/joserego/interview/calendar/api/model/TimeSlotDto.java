package me.joserego.interview.calendar.api.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.joserego.interview.calendar.api.entity.TimeSlot;
import me.joserego.interview.calendar.api.entity.User;

import javax.validation.constraints.AssertTrue;
import java.time.Instant;

@JsonAutoDetect
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "UTC")
    @JsonProperty(value = "starting_time")
    private Instant startingTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "UTC")
    @JsonProperty(value = "ending_time")
    private Instant endingTime;

    public TimeSlot toTimeSlotEntity(User user) {
        return new TimeSlot(user, startingTime, endingTime);
    }

    @JsonIgnore
    @AssertTrue(message = "starting time has to be before ending time")
    public boolean isEndingTimeAfterStartingTime() {
        return startingTime.isBefore(endingTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }

        TimeSlotDto oTimeSlotDto = (TimeSlotDto) o;
        return this.startingTime.equals(oTimeSlotDto.getStartingTime())
                && this.endingTime.equals(oTimeSlotDto.getEndingTime());
    }
}
