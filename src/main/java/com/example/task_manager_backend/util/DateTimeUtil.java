package com.example.task_manager_backend.util;
import com.example.task_manager_backend.model.enums.TimeStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateTimeUtil {
    public static LocalDateTime convertToDateTime(String dateStr, String timeStr) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate date = LocalDate.parse(dateStr, dateFormatter);
        LocalTime time = LocalTime.parse(timeStr, timeFormatter);

        return LocalDateTime.of(date, time);
    }

    public static TimeStatus getTimeStatus(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(startDateTime)) {
            return TimeStatus.UPCOMING;
        } else if (!now.isAfter(endDateTime)) {
            return TimeStatus.IN_PROGRESS;
        } else {
            return TimeStatus.ENDED;
        }
    }

}


//{
//        "title": "test",
//        "description": "test test test",
//        "startDate": "2025-06-10",
//        "startTime": "08:00",
//        "endDate": "2025-06-20",
//        "endTime": "17:00",
//        "userId": 1
//        }