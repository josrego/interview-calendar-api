package me.joserego.interview.calendar.api.model.request;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import java.util.List;

@JsonAutoDetect
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InterviewPossibilitiesRequestDto {
    private Long candidate;
    private List<Long> interviewers;
}
