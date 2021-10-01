package me.joserego.interview.calendar.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("me.joserego.interview.calendar.api")
public class InterviewCalendarApplicationAPI {

    public static void main(String[] args) {
        SpringApplication.run(InterviewCalendarApplicationAPI.class, args);
    }

}
