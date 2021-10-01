package me.joserego.interview.calendar.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.joserego.interview.calendar.api.model.TimeSlotDto;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
@ToString
public class TimeSlot {
    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "starting_time")
    private Instant startingTime;


    @Column(name = "ending_time")
    private Instant endingTime;

    public TimeSlot(User user, Instant start, Instant end) {
        this.user = user;
        this.startingTime = start;
        this.endingTime = end;
    }

    public TimeSlot(Instant startingTime, Instant endingTime) {
        this.startingTime = startingTime;
        this.endingTime = endingTime;
    }

    public TimeSlotDto toDto() {
        return new TimeSlotDto(
                startingTime,
                endingTime
        );
    }
}
