package me.joserego.interview.calendar.api.integrationtests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.joserego.interview.calendar.api.controller.UsersController;
import me.joserego.interview.calendar.api.data.MockData;
import me.joserego.interview.calendar.api.model.UserDto;
import me.joserego.interview.calendar.api.model.request.InterviewPossibilitiesRequestDto;
import me.joserego.interview.calendar.api.model.response.InterviewPossibilitiesResponseDto;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.datasource.driver-class-name=org.postgresql.Driver",
                "spring.jpa.hibernate.ddl-auto=create-drop",
                "test.environment.url=http://localhost:8080"
        })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersControllerTest {

    @Value("${test.environment.url}")
    private String url;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("interview-calendar-api-test")
            .withUsername("icat")
            .withPassword("icat");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Test
    public void testACreateUser() throws Exception {
        UserDto jrego = new UserDto("Jose Rego", "CANDIDATE");
        testSendCreateUser(jrego);
    }

    @Test
    public void testBSetTimeSlots() throws Exception {
        testUpdateTimeSlotsUser(1L, MockData.INTERVIEWER_1_TIME_SLOTS_JSON);
    }

    @Test
    public void testCGetPossibleInterviewsSlots() throws Exception {
        UserDto david = new UserDto("David", "INTERVIEWER");
        UserDto ingrid = new UserDto("Ingrid", "INTERVIEWER");
        UserDto carl = new UserDto("Carl", "CANDIDATE");

        david = testSendCreateUser(david);
        ingrid = testSendCreateUser(ingrid);
        carl = testSendCreateUser(carl);

        testUpdateTimeSlotsUser(david.getId(), MockData.INTERVIEWER_1_TIME_SLOTS_JSON);
        testUpdateTimeSlotsUser(ingrid.getId(), MockData.INTERVIEWER_2_TIME_SLOTS_JSON);
        testUpdateTimeSlotsUser(carl.getId(), MockData.CANDIDATE_TIME_SLOTS_JSON);

        InterviewPossibilitiesRequestDto request = new InterviewPossibilitiesRequestDto(carl.getId(),
                List.of(david.getId(), ingrid.getId()));

        String response = this.mockMvc.perform(
                        post(url + UsersController.URL_MAPPING + "/interviews_availability")
                                .content(asJsonString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        InterviewPossibilitiesResponseDto responseDto =
                objectMapper.readValue(response, InterviewPossibilitiesResponseDto.class);

        assertEquals(MockData.EXPECTED_RESPONSE, responseDto);
    }

    private UserDto testSendCreateUser(UserDto userDto) throws Exception {
        String response = sendCreateUserRequest(asJsonString(userDto))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(response, UserDto.class);
    }

    private void testUpdateTimeSlotsUser(Long id, String timeslotsJson) throws Exception {
        sendUpdateTimeSlotsRequest(id, timeslotsJson)
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.availableSlots").isNotEmpty());
    }

    private ResultActions sendCreateUserRequest(String body) throws Exception {
        return this.mockMvc.perform(
                post(url + UsersController.URL_MAPPING)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions sendUpdateTimeSlotsRequest(Long id, String body) throws Exception {
        return this.mockMvc.perform(
                put(url + UsersController.URL_MAPPING + "/timeslots/" + id)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
