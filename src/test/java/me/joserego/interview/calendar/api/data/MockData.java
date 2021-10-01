package me.joserego.interview.calendar.api.data;

import lombok.experimental.UtilityClass;
import me.joserego.interview.calendar.api.entity.TimeSlot;
import me.joserego.interview.calendar.api.entity.User;
import me.joserego.interview.calendar.api.model.TimeSlotDto;
import me.joserego.interview.calendar.api.model.UserDto;
import me.joserego.interview.calendar.api.model.response.InterviewPossibilitiesResponseDto;
import me.joserego.interview.calendar.api.util.DateUtils;

import java.util.List;
import java.util.stream.Stream;

@UtilityClass
public class MockData {

    public final List<Long> MOCK_INTERVIEWERS_IDS = List.of(1L, 2L);
    public final Long MOCK_CANDIDATE_ID = 3L;

    public final List<TimeSlot> MOCK_INTERVIEWER_1_TIME_SLOTS = List.of(
            new TimeSlot(DateUtils.fromString("2021-10-04 09:00"),
                    DateUtils.fromString("2021-10-04 16:00")),
            new TimeSlot(DateUtils.fromString("2021-10-05 09:00"),
                    DateUtils.fromString("2021-10-05 16:00")),
            new TimeSlot(DateUtils.fromString("2021-10-06 09:00"),
                    DateUtils.fromString("2021-10-06 16:00")),
            new TimeSlot(DateUtils.fromString("2021-10-07 09:00"),
                    DateUtils.fromString("2021-10-07 16:00")),
            new TimeSlot(DateUtils.fromString("2021-10-08 09:00"),
                    DateUtils.fromString("2021-10-08 16:00"))
    );

    public final List<TimeSlot> MOCK_INTERVIEWER_2_TIME_SLOTS = List.of(
            new TimeSlot(DateUtils.fromString("2021-10-06 12:00"),
                    DateUtils.fromString("2021-10-06 18:00")),
            new TimeSlot(DateUtils.fromString("2021-10-04 12:00"),
                    DateUtils.fromString("2021-10-04 18:00")),
            new TimeSlot(DateUtils.fromString("2021-10-05 09:00"),
                    DateUtils.fromString("2021-10-05 12:00")),
            new TimeSlot(DateUtils.fromString("2021-10-07 09:00"),
                    DateUtils.fromString("2021-10-07 12:00"))
    );

    public final List<TimeSlot> MOCK_CANDIDATE_TIME_SLOTS = List.of(
            new TimeSlot(DateUtils.fromString("2021-10-04 09:00"),
                    DateUtils.fromString("2021-10-04 10:00")),
            new TimeSlot(DateUtils.fromString("2021-10-05 09:00"),
                    DateUtils.fromString("2021-10-05 10:00")),
            new TimeSlot(DateUtils.fromString("2021-10-06 10:00"),
                    DateUtils.fromString("2021-10-06 12:00")),
            new TimeSlot(DateUtils.fromString("2021-10-07 09:00"),
                    DateUtils.fromString("2021-10-07 10:00")),
            new TimeSlot(DateUtils.fromString("2021-10-08 09:00"),
                    DateUtils.fromString("2021-10-08 10:00"))
    );

    public final Stream<User> MOCK_INTERVIEWERS = Stream.of(
            new User("David", UserDto.Role.INTERVIEWER.name(), MOCK_INTERVIEWER_1_TIME_SLOTS),
            new User("Ingrid", UserDto.Role.INTERVIEWER.name(), MOCK_INTERVIEWER_2_TIME_SLOTS)
    );

    public final User MOCK_CANDIDATE = new User("Carl", UserDto.Role.CANDIDATE.name(),
            MOCK_CANDIDATE_TIME_SLOTS);

    public final InterviewPossibilitiesResponseDto EXPECTED_RESPONSE =
            new InterviewPossibilitiesResponseDto(
                    "Carl",
                    List.of(
                            new TimeSlotDto(DateUtils.fromString("2021-10-05 09:00"),
                                    DateUtils.fromString("2021-10-05 10:00")),
                            new TimeSlotDto(DateUtils.fromString("2021-10-07 09:00"),
                                    DateUtils.fromString("2021-10-07 10:00"))
                    ));

    public final String INTERVIEWER_1_TIME_SLOTS_JSON = "{\n" +
            "    \"availableSlots\": [\n" +
            "        {\n" +
            "            \"starting_time\": \"2021-10-04 09:00\",\n" +
            "            \"ending_time\": \"2021-10-04 16:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"starting_time\": \"2021-10-05 09:00\",\n" +
            "            \"ending_time\": \"2021-10-05 16:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"starting_time\": \"2021-10-06 09:00\",\n" +
            "            \"ending_time\": \"2021-10-06 16:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"starting_time\": \"2021-10-07 09:00\",\n" +
            "            \"ending_time\": \"2021-10-07 16:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"starting_time\": \"2021-10-08 09:00\",\n" +
            "            \"ending_time\": \"2021-10-08 16:00\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";


    public final String INTERVIEWER_2_TIME_SLOTS_JSON = "{\n" +
            "    \"availableSlots\": [\n" +
            "        {\n" +
            "            \"starting_time\": \"2021-10-06 12:00\",\n" +
            "            \"ending_time\": \"2021-10-06 18:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"starting_time\": \"2021-10-04 12:00\",\n" +
            "            \"ending_time\": \"2021-10-04 18:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"starting_time\": \"2021-10-05 09:00\",\n" +
            "            \"ending_time\": \"2021-10-05 12:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"starting_time\": \"2021-10-07 09:00\",\n" +
            "            \"ending_time\": \"2021-10-07 12:00\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    public final String CANDIDATE_TIME_SLOTS_JSON = "{\n" +
            "    \"availableSlots\": [\n" +
            "        {\n" +
            "            \"starting_time\": \"2021-10-04 09:00\",\n" +
            "            \"ending_time\": \"2021-10-04 10:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"starting_time\": \"2021-10-05 09:00\",\n" +
            "            \"ending_time\": \"2021-10-05 10:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"starting_time\": \"2021-10-06 10:00\",\n" +
            "            \"ending_time\": \"2021-10-06 12:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"starting_time\": \"2021-10-07 09:00\",\n" +
            "            \"ending_time\": \"2021-10-07 10:00\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"starting_time\": \"2021-10-08 09:00\",\n" +
            "            \"ending_time\": \"2021-10-08 10:00\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}
