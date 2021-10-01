package me.joserego.interview.calendar.api.util;

import lombok.experimental.UtilityClass;
import me.joserego.interview.calendar.api.entity.TimeSlot;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class DateUtils {

    private static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";


    public boolean isWithinRange(TimeSlot timeSlot, TimeSlot rangeTimeSlot) {
        return isAfterOrEqual(timeSlot.getStartingTime(), rangeTimeSlot.getStartingTime())
                && isBeforeOrEqual(timeSlot.getEndingTime(), rangeTimeSlot.getEndingTime());
    }

    private boolean isBeforeOrEqual(Instant instant, Instant beforeInstant) {
        return instant.equals(beforeInstant) || instant.isBefore(beforeInstant);
    }

    private boolean isAfterOrEqual(Instant instant, Instant afterInstant) {
        return instant.equals(afterInstant) || instant.isAfter(afterInstant);
    }

    public Instant addHours(Instant instant, int hours) {
        return instant.plus(hours, ChronoUnit.HOURS);
    }

    public Instant fromString(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
                .atZone(ZoneOffset.UTC)
                .toInstant();
    }
}