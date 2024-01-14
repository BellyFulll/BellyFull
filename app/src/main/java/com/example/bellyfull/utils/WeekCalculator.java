package com.example.bellyfull.utils;

import java.time.Instant;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WeekCalculator {
    private static long dateOfConception;

    public WeekCalculator(long dateOfConception) {
        this.dateOfConception = dateOfConception;
    }

    public static int calculateWeeksDifference() {
        long currentTime = System.currentTimeMillis();

        Instant startInstant = Instant.ofEpochMilli(dateOfConception);
        Instant endInstant = Instant.ofEpochMilli(currentTime);

        Duration duration = Duration.between(startInstant, endInstant);

        long weeks = TimeUnit.MILLISECONDS.toDays(duration.toMillis()) / 7;
        int roundedWeeks = (int) weeks;

        return roundedWeeks;
    }
}
