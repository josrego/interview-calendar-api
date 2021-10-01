package me.joserego.interview.calendar.api.service.impl.unittest;

import me.joserego.interview.calendar.api.model.response.InterviewPossibilitiesResponseDto;
import me.joserego.interview.calendar.api.repository.impl.DefaultUsersRepository;
import me.joserego.interview.calendar.api.service.impl.DefaultUsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static me.joserego.interview.calendar.api.data.MockData.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    DefaultUsersRepository repository;

    @InjectMocks
    DefaultUsersService service;

    @Test
    void findPossibleInterviews() {
        when(repository.getUser(MOCK_CANDIDATE_ID))
                .thenReturn(Optional.of(MOCK_CANDIDATE));
        when(repository.getUsers(MOCK_INTERVIEWERS_IDS))
                .thenReturn(MOCK_INTERVIEWERS);

        InterviewPossibilitiesResponseDto responseDto
                = service.findPossibleInterviews(MOCK_CANDIDATE_ID, MOCK_INTERVIEWERS_IDS);

        assert EXPECTED_RESPONSE.equals(responseDto);
    }
}