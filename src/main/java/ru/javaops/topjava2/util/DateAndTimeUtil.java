package ru.javaops.topjava2.util;

import java.time.LocalDate;
import java.util.Optional;

public class DateAndTimeUtil {

    public static LocalDate getDateForRequest(Optional<LocalDate> localDate) {
        return localDate.orElse(LocalDate.now());
    }
}