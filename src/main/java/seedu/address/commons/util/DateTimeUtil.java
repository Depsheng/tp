package seedu.address.commons.util;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

/**
 * Helper functions for handling date and time parsing with flexible formats.
 */
public class DateTimeUtil {

    public static final String MESSAGE_DATE_TIME_PAST = "Date and time cannot be in the past";
    public static final String MESSAGE_INVALID_DATE_TIME_FORMAT = "Invalid date/time format."
            + " Supported formats include:\n"
            + "- 15 Mar 2025 4pm\n"
            + "- 15 Mar 2025 4:30pm\n"
            + "- 15 Mar 2025 4.30pm\n"
            + "- 15 Mar 2025 1600\n"
            + "- 15 Mar 4pm (assumes current year)\n"
            + "- 15 March 2025 4:30pm\n"
            + "- 15/3/2025 16:30\n"
            + "- 15-3-2025 4:30pm\n"
            + "- 15.3.2025 1630\n"
            + "- Today 4pm\n"
            + "- Tomorrow 9am\n"
            + "- Monday 2pm (or Mon 2pm)\n"
            + "- Tuesday 2pm (or Tue 2pm)\n"
            + "- Wednesday 2pm (or Wed 2pm)\n"
            + "- Thursday 2pm (or Thu 2pm)\n"
            + "- Friday 2pm (or Fri 2pm)\n"
            + "- Saturday 2pm (or Sat 2pm)\n"
            + "- Sunday 2pm (or Sun 2pm)";

    private static final DateTimeFormatter[] FORMATS = {
        // d MMM yyyy + time (4pm, 4:30pm, 4.30pm, 1600)
        new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("d MMM yyyy")
                .optionalStart()
                .appendLiteral(' ')
                .appendPattern("[h[:mm][.mm]a][HHmm]")
                .optionalEnd()
                .toFormatter(Locale.ENGLISH),

        // d MMM + time (default year)
        new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("d MMM")
                .parseDefaulting(ChronoField.YEAR, LocalDate.now().getYear())
                .optionalStart()
                .appendLiteral(' ')
                .appendPattern("[h[:mm][.mm]a][HHmm]")
                .optionalEnd()
                .toFormatter(Locale.ENGLISH),

        // d MMMM yyyy + time
        new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("d MMMM yyyy")
                .optionalStart()
                .appendLiteral(' ')
                .appendPattern("[h[:mm][.mm]a][HHmm]")
                .optionalEnd()
                .toFormatter(Locale.ENGLISH),

        // d MMMM + time (default year)
        new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("d MMMM")
                .parseDefaulting(ChronoField.YEAR, LocalDate.now().getYear())
                .optionalStart()
                .appendLiteral(' ')
                .appendPattern("[h[:mm][.mm]a][HHmm]")
                .optionalEnd()
                .toFormatter(Locale.ENGLISH),

        // d/M/yyyy + time
        new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("d/M/yyyy")
                .optionalStart()
                .appendLiteral(' ')
                .appendPattern("[h:mm a][h.mm a][h a][h:mma][h.mma][ha][H:mm][HHmm]")
                .optionalEnd()
                .toFormatter(Locale.ENGLISH),

        // d-M-yyyy + time
        new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("d-M-yyyy")
                .optionalStart()
                .appendLiteral(' ')
                .appendPattern("[h:mm a][h.mm a][h a][h:mma][h.mma][ha][H:mm][HHmm]")
                .optionalEnd()
                .toFormatter(Locale.ENGLISH),

        // d.M.yyyy + time
        new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("d.M.yyyy")
                .optionalStart()
                .appendLiteral(' ')
                .appendPattern("[h:mm a][h.mm a][h a][h:mma][h.mma][ha][H:mm][HHmm]")
                .optionalEnd()
                .toFormatter(Locale.ENGLISH),

        // dd/MM/yyyy + time
        new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd/MM/yyyy")
                .optionalStart()
                .appendLiteral(' ')
                .appendPattern("[h:mm a][h.mm a][h a][h:mma][h.mma][ha][H:mm][HHmm]")
                .optionalEnd()
                .toFormatter(Locale.ENGLISH),

        // dd-MM-yyyy + time
        new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd-MM-yyyy")
                .optionalStart()
                .appendLiteral(' ')
                .appendPattern("[h:mm a][h.mm a][h a][h:mma][h.mma][ha][H:mm][HHmm]")
                .optionalEnd()
                .toFormatter(Locale.ENGLISH),

        // dd.MM.yyyy + time
        new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd.MM.yyyy")
                .optionalStart()
                .appendLiteral(' ')
                .appendPattern("[h:mm a][h.mm a][h a][h:mma][h.mma][ha][H:mm][HHmm]")
                .optionalEnd()
                .toFormatter(Locale.ENGLISH)
    };

    /**
     * Parses a date/time string and returns the date and time components.
     * Validates that the parsed date/time is not in the past.
     *
     * @param dateTimeStr The date/time string to parse
     * @return A DateTimeParseResult containing the parsed date and time
     * @throws IllegalArgumentException if the string cannot be parsed or is in the past
     */
    public static DateTimeParseResult parseDateTime(String dateTimeStr) {
        checkArgument(dateTimeStr != null && !dateTimeStr.trim().isEmpty(), "Date/time string cannot be null or empty");

        String trimmedStr = dateTimeStr.trim();

        // Handle relative dates first
        if (trimmedStr.toLowerCase().startsWith("today")) {
            return parseRelativeDate(trimmedStr, 0);
        } else if (trimmedStr.toLowerCase().startsWith("tomorrow")) {
            return parseRelativeDate(trimmedStr, 1);
        } else if (isWeekday(trimmedStr)) {
            return parseWeekday(trimmedStr);
        }

        // Try all other formats
        for (DateTimeFormatter formatter : FORMATS) {
            try {
                TemporalAccessor temporal = formatter.parse(trimmedStr);
                LocalDate date = LocalDate.from(temporal);
                LocalTime time = extractTime(temporal, trimmedStr.contains("am") || trimmedStr.contains("pm"));

                LocalDateTime dateTime = LocalDateTime.of(date, time);

                // Validate not in the past
                if (dateTime.isBefore(LocalDateTime.now())) {
                    throw new IllegalArgumentException(MESSAGE_DATE_TIME_PAST);
                }

                return new DateTimeParseResult(date, time);
            } catch (DateTimeParseException e) {
                // Continue to next format
            }
        }

        throw new IllegalArgumentException(MESSAGE_INVALID_DATE_TIME_FORMAT);
    }

    /**
     * Parses a date string (time component is optional).
     *
     * @param dateStr The date string to parse
     * @return The parsed LocalDate
     * @throws IllegalArgumentException if the string cannot be parsed or is in the past
     */
    public static LocalDate parseDate(String dateStr) {
        checkArgument(dateStr != null && !dateStr.trim().isEmpty(), "Date string cannot be null or empty");

        String trimmedStr = dateStr.trim();

        // Handle relative dates
        if (trimmedStr.equalsIgnoreCase("today")) {
            return LocalDate.now();
        } else if (trimmedStr.equalsIgnoreCase("tomorrow")) {
            return LocalDate.now().plusDays(1);
        } else if (isWeekday(trimmedStr)) {
            return parseWeekdayDate(trimmedStr);
        }

        // Try date-only formats with year first
        String[] dateWithYearFormats = {"d MMM yyyy", "d MMMM yyyy", "d/M/yyyy", "d-M-yyyy", "d.M.yyyy",
            "dd/MM/yyyy", "dd-MM-yyyy", "dd.MM.yyyy"};

        for (String pattern : dateWithYearFormats) {
            try {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern(pattern)
                        .toFormatter(Locale.ENGLISH);

                LocalDate date = LocalDate.parse(trimmedStr, formatter);

                // Validate not in the past
                if (date.isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException(MESSAGE_DATE_TIME_PAST);
                }

                return date;
            } catch (DateTimeParseException e) {
                // Continue to next format
            }
        }

        // Try date-only formats without year (default to current year)
        String[] dateWithoutYearFormats = {"d MMM", "d MMMM"};

        for (String pattern : dateWithoutYearFormats) {
            try {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern(pattern)
                        .parseDefaulting(ChronoField.YEAR, LocalDate.now().getYear())
                        .toFormatter(Locale.ENGLISH);

                LocalDate date = LocalDate.parse(trimmedStr, formatter);

                // Validate not in the past
                if (date.isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException(MESSAGE_DATE_TIME_PAST);
                }

                return date;
            } catch (DateTimeParseException e) {
                // Continue to next format
            }
        }

        throw new IllegalArgumentException(MESSAGE_INVALID_DATE_TIME_FORMAT);
    }

    /**
     * Parses a time string.
     *
     * @param timeStr The time string to parse
     * @return The parsed LocalTime
     * @throws IllegalArgumentException if the string cannot be parsed
     */
    public static LocalTime parseTime(String timeStr) {
        checkArgument(timeStr != null && !timeStr.trim().isEmpty(), "Time string cannot be null or empty");

        String trimmedStr = timeStr.trim();
        String[] timeFormats = {
            "h:mm a", // 1:30 pm
            "h.mm a", // 1.30 pm
            "h a", // 1 pm
            "h:mma", // 1:30pm
            "h.mma", // 1.30pm
            "ha", // 1pm
            "HH:mm", // 13:30
            "HHmm" // 1330
        };

        for (String pattern : timeFormats) {
            try {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern(pattern)
                        .toFormatter(Locale.ENGLISH);

                return LocalTime.parse(trimmedStr, formatter);
            } catch (DateTimeParseException e) {
                // Continue to next format
            }
        }

        throw new IllegalArgumentException("Invalid time format. Supported formats: 4pm, 4:30pm, 4.30pm, 16:30, 1630");
    }

    private static boolean isWeekday(String input) {
        String lowerInput = input.toLowerCase().trim();
        String[] weekdays = {"monday", "mon", "tuesday", "tue", "wednesday", "wed",
            "thursday", "thu", "friday", "fri", "saturday", "sat", "sunday", "sun"};

        for (String weekday : weekdays) {
            if (lowerInput.startsWith(weekday)) {
                return true;
            }
        }
        return false;
    }

    private static DateTimeParseResult parseWeekday(String input) {
        String[] parts = input.split("\\s+", 2);
        if (parts.length < 1) {
            throw new IllegalArgumentException("Invalid weekday format");
        }

        DayOfWeek dayOfWeek = parseDayOfWeek(parts[0].toLowerCase());
        LocalDate targetDate = LocalDate.now().with(TemporalAdjusters.next(dayOfWeek));

        if (parts.length == 1) {
            // Only weekday, default to 12:00 AM (midnight)
            return new DateTimeParseResult(targetDate, LocalTime.of(0, 0));
        } else {
            // Weekday + time
            LocalTime time = parseTime(parts[1]);
            return new DateTimeParseResult(targetDate, time);
        }
    }

    private static DateTimeParseResult parseRelativeDate(String input, int daysToAdd) {
        String[] parts = input.split("\\s+", 2);
        LocalDate date = LocalDate.now().plusDays(daysToAdd);

        if (parts.length == 1) {
            // Only date, default to 12:00 AM (midnight)
            return new DateTimeParseResult(date, LocalTime.of(0, 0));
        } else {
            // Date + time
            LocalTime time = parseTime(parts[1]);
            LocalDateTime dateTime = LocalDateTime.of(date, time);

            if (dateTime.isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException(MESSAGE_DATE_TIME_PAST);
            }

            return new DateTimeParseResult(date, time);
        }
    }

    private static LocalDate parseWeekdayDate(String input) {
        String[] parts = input.split("\\s+", 2);
        if (parts.length < 1) {
            throw new IllegalArgumentException("Invalid weekday format");
        }

        DayOfWeek dayOfWeek = parseDayOfWeek(parts[0].toLowerCase());
        return LocalDate.now().with(TemporalAdjusters.next(dayOfWeek));
    }

    private static DayOfWeek parseDayOfWeek(String weekday) {
        switch (weekday) {
        case "monday":
        case "mon":
            return DayOfWeek.MONDAY;
        case "tuesday":
        case "tue":
            return DayOfWeek.TUESDAY;
        case "wednesday":
        case "wed":
            return DayOfWeek.WEDNESDAY;
        case "thursday":
        case "thu":
            return DayOfWeek.THURSDAY;
        case "friday":
        case "fri":
            return DayOfWeek.FRIDAY;
        case "saturday":
        case "sat":
            return DayOfWeek.SATURDAY;
        case "sunday":
        case "sun":
            return DayOfWeek.SUNDAY;
        default:
            throw new IllegalArgumentException("Invalid weekday: " + weekday);
        }
    }

    private static LocalTime extractTime(TemporalAccessor temporal, boolean isAmPmFormat) {
        if (temporal.isSupported(ChronoField.HOUR_OF_DAY)) {
            return LocalTime.from(temporal);
        } else if (isAmPmFormat && temporal.isSupported(ChronoField.HOUR_OF_AMPM)) {
            // Handle AM/PM format
            int hour = temporal.get(ChronoField.HOUR_OF_AMPM);
            int minute = temporal.isSupported(ChronoField.MINUTE_OF_HOUR)
                        ? temporal.get(ChronoField.MINUTE_OF_HOUR) : 0;

            // Check if it's PM (this is a simplified approach)
            String amPm = temporal.query(t -> {
                // This is a bit tricky with DateTimeFormatterBuilder,
                // so we'll default to reasonable times
                return null;
            });

            // Default to reasonable times (this could be enhanced)
            return LocalTime.of(hour % 12 + (hour >= 12 ? 12 : 0), minute);
        }

        // Default to 12:00 AM (midnight) if no time specified
        return LocalTime.of(0, 0);
    }

    /**
     * Container class for parsed date and time results.
     */
    public static class DateTimeParseResult {
        private final LocalDate date;
        private final LocalTime time;

        /**
         * Constructor for DateTimeParseResult.
         */
        public DateTimeParseResult(LocalDate date, LocalTime time) {
            this.date = date;
            this.time = time;
        }

        public LocalDate getDate() {
            return date;
        }

        public LocalTime getTime() {
            return time;
        }

        public LocalDateTime getDateTime() {
            return LocalDateTime.of(date, time);
        }
    }
}
