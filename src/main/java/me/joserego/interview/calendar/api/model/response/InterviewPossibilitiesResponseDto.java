package me.joserego.interview.calendar.api.model.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;
import me.joserego.interview.calendar.api.entity.TimeSlot;
import me.joserego.interview.calendar.api.entity.User;
import me.joserego.interview.calendar.api.model.TimeSlotDto;

import java.util.List;
import java.util.stream.Collectors;

@JsonAutoDetect
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InterviewPossibilitiesResponseDto {
    private String candidate;

    private List<TimeSlotDto> possibleInterviewSlots;

    public static InterviewPossibilitiesResponseDto from(User candidate, List<TimeSlot> possibleTimeSlots) {
        return new InterviewPossibilitiesResponseDto(
                candidate.getName(),
                possibleTimeSlots.stream()
                        .map(TimeSlot::toDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }

        if (!o.getClass().isAssignableFrom(this.getClass())) {
            return false;
        }

        InterviewPossibilitiesResponseDto oResponse = (InterviewPossibilitiesResponseDto) o;
        return this.candidate.equals(oResponse.getCandidate())
                && oResponse.getPossibleInterviewSlots().containsAll(this.possibleInterviewSlots);
    }
}
