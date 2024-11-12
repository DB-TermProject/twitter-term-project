package org.example.util.converter;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

public class TimeConverter {

    public static String convertToString(Timestamp time) {
        LocalDateTime createdAt = time.toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(createdAt, now);

        if (duration.toMinutes() < 1) {
            return "방금 전";
        } else if (duration.toMinutes() < 60) {
            return duration.toMinutes() + "분 전";
        } else if (duration.toHours() < 24) {
            return duration.toHours() + "시간 전";
        } else if (duration.toDays() < 7) {
            return duration.toDays() + "일 전";
        } else {
            return createdAt.toLocalDate().toString(); // 예: "2024-11-05"
        }
    }

}
