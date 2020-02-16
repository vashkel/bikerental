package by.training.vashkevichyura.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataFormatter {

    private final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.S";
    private final static DateTimeFormatter FORMATTER_DATA_TIME = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private final static String DATE_TIME_PATTERN_TO_VIEW = "dd.MM.yyyy HH:mm:ss";
    private final static DateTimeFormatter FORMATTER_DATA_TIME_TO_VIEW = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_TO_VIEW);

    public static String getCurrentDateTimeToDB() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.format(FORMATTER_DATA_TIME);
    }



}
