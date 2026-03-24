package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class DateTimeUtilTest {

    @Test
    public void parseDateTime_validFormats_success() {
        // Test various date formats with time
        DateTimeUtil.DateTimeParseResult result;

        result = DateTimeUtil.parseDateTime("15 Mar 2030 4pm");
        assertEquals(LocalDate.of(2030, 3, 15), result.getDate());
        assertEquals(LocalTime.of(16, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("15 Mar 2030 4:30pm");
        assertEquals(LocalDate.of(2030, 3, 15), result.getDate());
        assertEquals(LocalTime.of(16, 30), result.getTime());

        result = DateTimeUtil.parseDateTime("15 Mar 2030 4.30pm");
        assertEquals(LocalDate.of(2030, 3, 15), result.getDate());
        assertEquals(LocalTime.of(16, 30), result.getTime());

        result = DateTimeUtil.parseDateTime("15 Mar 2030 1600");
        assertEquals(LocalDate.of(2030, 3, 15), result.getDate());
        assertEquals(LocalTime.of(16, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("15 March 2030 4pm");
        assertEquals(LocalDate.of(2030, 3, 15), result.getDate());
        assertEquals(LocalTime.of(16, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("15/3/2030 16:30");
        assertEquals(LocalDate.of(2030, 3, 15), result.getDate());
        assertEquals(LocalTime.of(16, 30), result.getTime());

        result = DateTimeUtil.parseDateTime("15-3-2030 4:30pm");
        assertEquals(LocalDate.of(2030, 3, 15), result.getDate());
        assertEquals(LocalTime.of(16, 30), result.getTime());

        result = DateTimeUtil.parseDateTime("15.3.2030 1630");
        assertEquals(LocalDate.of(2030, 3, 15), result.getDate());
        assertEquals(LocalTime.of(16, 30), result.getTime());
    }

    @Test
    public void parseDateTime_currentYearFormats_success() {
        // Test formats that assume current year
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();

        DateTimeUtil.DateTimeParseResult result = DateTimeUtil.parseDateTime("31 Dec 4pm");
        assertEquals(LocalDate.of(currentYear, 12, 31), result.getDate());
        assertEquals(LocalTime.of(16, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("31 December 2026 2359");
        assertEquals(LocalDate.of(currentYear, 12, 31), result.getDate());
        assertEquals(LocalTime.of(23, 59), result.getTime());
    }

    @Test
    public void parseDateTime_relativeDates_success() {
        // Test relative dates
        DateTimeUtil.DateTimeParseResult result;

        result = DateTimeUtil.parseDateTime("Today 2359");
        assertEquals(LocalDate.now(), result.getDate());
        assertEquals(LocalTime.of(23, 59), result.getTime());

        result = DateTimeUtil.parseDateTime("Tomorrow 9am");
        assertEquals(LocalDate.now().plusDays(1), result.getDate());
        assertEquals(LocalTime.of(9, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Tomorrow");
        assertEquals(LocalDate.now().plusDays(1), result.getDate());
        assertEquals(LocalTime.of(0, 0), result.getTime());
    }

    @Test
    public void parseDateTime_weekdayFormats_success() {
        // Test weekday formats (without "next" prefix)
        DateTimeUtil.DateTimeParseResult result;
        LocalDate today = LocalDate.now();

        // Test all weekday variations with time
        result = DateTimeUtil.parseDateTime("Monday 2pm");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.MONDAY)), result.getDate());
        assertEquals(LocalTime.of(14, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Mon 4pm");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.MONDAY)), result.getDate());
        assertEquals(LocalTime.of(16, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Tuesday 9am");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.TUESDAY)), result.getDate());
        assertEquals(LocalTime.of(9, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Tue 10:30am");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.TUESDAY)), result.getDate());
        assertEquals(LocalTime.of(10, 30), result.getTime());

        result = DateTimeUtil.parseDateTime("Wednesday 11pm");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.WEDNESDAY)), result.getDate());
        assertEquals(LocalTime.of(23, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Wed 8:30pm");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.WEDNESDAY)), result.getDate());
        assertEquals(LocalTime.of(20, 30), result.getTime());

        result = DateTimeUtil.parseDateTime("Thursday 3pm");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.THURSDAY)), result.getDate());
        assertEquals(LocalTime.of(15, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Thu 7am");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.THURSDAY)), result.getDate());
        assertEquals(LocalTime.of(7, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Friday 6pm");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.FRIDAY)), result.getDate());
        assertEquals(LocalTime.of(18, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Fri 5pm");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.FRIDAY)), result.getDate());
        assertEquals(LocalTime.of(17, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Saturday 1pm");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.SATURDAY)), result.getDate());
        assertEquals(LocalTime.of(13, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Sat 12pm");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.SATURDAY)), result.getDate());
        assertEquals(LocalTime.of(12, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Sunday 10am");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.SUNDAY)), result.getDate());
        assertEquals(LocalTime.of(10, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Sun 11am");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.SUNDAY)), result.getDate());
        assertEquals(LocalTime.of(11, 0), result.getTime());
    }

    @Test
    public void parseDateTime_weekdayWithoutTime_success() {
        // Test weekday without time (should default to 12:00 AM)
        DateTimeUtil.DateTimeParseResult result;
        LocalDate today = LocalDate.now();

        result = DateTimeUtil.parseDateTime("Monday");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.MONDAY)), result.getDate());
        assertEquals(LocalTime.of(0, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Tue");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.TUESDAY)), result.getDate());
        assertEquals(LocalTime.of(0, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Friday");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.FRIDAY)), result.getDate());
        assertEquals(LocalTime.of(0, 0), result.getTime());
    }

    @Test
    public void parseDateTime_newTimeFormats_success() {
        // Test new time formats added by user
        DateTimeUtil.DateTimeParseResult result;

        result = DateTimeUtil.parseDateTime("15 Mar 2030 1:30pm");
        assertEquals(LocalDate.of(2030, 3, 15), result.getDate());
        assertEquals(LocalTime.of(13, 30), result.getTime());

        result = DateTimeUtil.parseDateTime("15 Mar 2030 1.30pm");
        assertEquals(LocalDate.of(2030, 3, 15), result.getDate());
        assertEquals(LocalTime.of(13, 30), result.getTime());
    }

    @Test
    public void parseDateTime_relativeDatesDefaultTime_success() {
        // Test relative dates without time (should default to 12:00 AM)
        DateTimeUtil.DateTimeParseResult result;

        result = DateTimeUtil.parseDateTime("Today 2359");
        assertEquals(LocalDate.now(), result.getDate());
        assertEquals(LocalTime.of(23, 59), result.getTime());

        result = DateTimeUtil.parseDateTime("Tomorrow 2359");
        assertEquals(LocalDate.now().plusDays(1), result.getDate());
        assertEquals(LocalTime.of(23, 59), result.getTime());
    }

    @Test
    public void parseTime_newFormats_success() {
        // Test new time formats
        LocalTime result;

        result = DateTimeUtil.parseTime("1:30pm");
        assertEquals(LocalTime.of(13, 30), result);

        result = DateTimeUtil.parseTime("1.30pm");
        assertEquals(LocalTime.of(13, 30), result);

        result = DateTimeUtil.parseTime("1:30pm");
        assertEquals(LocalTime.of(13, 30), result);

        result = DateTimeUtil.parseTime("1pm");
        assertEquals(LocalTime.of(13, 0), result);

        result = DateTimeUtil.parseTime("1am");
        assertEquals(LocalTime.of(1, 0), result);
    }

    @Test
    public void parseDateTime_edgeCases_success() {
        // Test edge cases and boundary conditions
        DateTimeUtil.DateTimeParseResult result;

        // Test midnight times
        result = DateTimeUtil.parseDateTime("Monday 12am");
        assertEquals(LocalTime.of(0, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Monday 12:00am");
        assertEquals(LocalTime.of(0, 0), result.getTime());

        // Test noon times
        result = DateTimeUtil.parseDateTime("Tuesday 12pm");
        assertEquals(LocalTime.of(12, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Tuesday 12:00pm");
        assertEquals(LocalTime.of(12, 0), result.getTime());

        // Test late night times
        result = DateTimeUtil.parseDateTime("Wednesday 11:59pm");
        assertEquals(LocalTime.of(23, 59), result.getTime());

        result = DateTimeUtil.parseDateTime("Thursday 2359");
        assertEquals(LocalTime.of(23, 59), result.getTime());
    }

    @Test
    public void parseDateTime_mixedCaseFormats_success() {
        // Test case insensitive parsing
        DateTimeUtil.DateTimeParseResult result;

        result = DateTimeUtil.parseDateTime("MONDAY 2PM");
        assertEquals(LocalTime.of(14, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("mon 4pm");
        assertEquals(LocalTime.of(16, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("TODAY 2359");
        assertEquals(LocalDate.now(), result.getDate());
        assertEquals(LocalTime.of(23, 59), result.getTime());

        result = DateTimeUtil.parseDateTime("tomorrow 9AM");
        assertEquals(LocalDate.now().plusDays(1), result.getDate());
        assertEquals(LocalTime.of(9, 0), result.getTime());
    }

    @Test
    public void parseDateTime_extraWhitespace_success() {
        // Test inputs with extra whitespace
        DateTimeUtil.DateTimeParseResult result;

        result = DateTimeUtil.parseDateTime("  Monday 4pm  ");
        assertEquals(LocalTime.of(16, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Today   2359");
        assertEquals(LocalDate.now(), result.getDate());
        assertEquals(LocalTime.of(23, 59), result.getTime());
    }

    @Test
    public void parseDateTime_weekdayTimeFormats_success() {
        // Test all time formats with weekdays
        DateTimeUtil.DateTimeParseResult result;
        LocalDate today = LocalDate.now();

        result = DateTimeUtil.parseDateTime("Mon 1600");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.MONDAY)), result.getDate());
        assertEquals(LocalTime.of(16, 0), result.getTime());

        result = DateTimeUtil.parseDateTime("Tue 4:30pm");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.TUESDAY)), result.getDate());
        assertEquals(LocalTime.of(16, 30), result.getTime());

        result = DateTimeUtil.parseDateTime("Wed 4.30pm");
        assertEquals(today.plusDays(getDaysUntilNext(DayOfWeek.WEDNESDAY)), result.getDate());
        assertEquals(LocalTime.of(16, 30), result.getTime());
    }

    // Helper method to calculate days until next occurrence of a weekday
    private int getDaysUntilNext(java.time.DayOfWeek dayOfWeek) {
        LocalDate today = LocalDate.now();
        int currentDayOfWeek = today.getDayOfWeek().getValue();
        int targetDayOfWeek = dayOfWeek.getValue();
        int daysToAdd = (targetDayOfWeek - currentDayOfWeek + 7) % 7;
        return daysToAdd == 0 ? 7 : daysToAdd;
    }

    @Test
    public void parseTime_edgeCases_success() {
        // Test edge cases for time parsing
        LocalTime result;

        // Test single digit hours
        result = DateTimeUtil.parseTime("1pm");
        assertEquals(LocalTime.of(13, 0), result);

        result = DateTimeUtil.parseTime("9am");
        assertEquals(LocalTime.of(9, 0), result);

        // Test midnight variations
        result = DateTimeUtil.parseTime("12am");
        assertEquals(LocalTime.of(0, 0), result);

        result = DateTimeUtil.parseTime("12:00am");
        assertEquals(LocalTime.of(0, 0), result);

        // Test noon variations
        result = DateTimeUtil.parseTime("12pm");
        assertEquals(LocalTime.of(12, 0), result);

        result = DateTimeUtil.parseTime("12:00pm");
        assertEquals(LocalTime.of(12, 0), result);
    }

    @Test
    public void parseDateTime_pastDate_throwsException() {
        // Test that past dates throw exceptions
        LocalDate pastDate = LocalDate.now().minusDays(1);
        String pastDateStr = pastDate.getDayOfMonth() + " "
                           + pastDate.getMonth().toString().substring(0, 3) + " "
                           + pastDate.getYear() + " 4pm";

        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseDateTime(pastDateStr));
    }

    @Test
    public void parseDateTime_invalidFormat_throwsException() {
        // Test invalid formats
        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseDateTime("invalid date"));
        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseDateTime("32 Mar 2025 4pm"));
        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseDateTime("15 Foo 2025 4pm"));
        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseDateTime("15 Mar 2025 25:00"));
    }

    @Test
    public void parseDateTime_nullOrEmpty_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseDateTime(null));
        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseDateTime(""));
        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseDateTime("   "));
    }

    @Test
    public void parseDate_validFormats_success() {
        // Test date parsing without time
        LocalDate result;

        result = DateTimeUtil.parseDate("15 Mar 2030");
        assertEquals(LocalDate.of(2030, 3, 15), result);

        result = DateTimeUtil.parseDate("15/3/2030");
        assertEquals(LocalDate.of(2030, 3, 15), result);

        result = DateTimeUtil.parseDate("15-3-2030");
        assertEquals(LocalDate.of(2030, 3, 15), result);

        result = DateTimeUtil.parseDate("15.3.2030");
        assertEquals(LocalDate.of(2030, 3, 15), result);

        result = DateTimeUtil.parseDate("Tomorrow");
        assertEquals(LocalDate.now().plusDays(1), result);
    }

    @Test
    public void parseDate_currentYearFormats_success() {
        // Test date formats that assume current year
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();

        LocalDate result = DateTimeUtil.parseDate("31 Dec");
        assertEquals(LocalDate.of(currentYear, 12, 31), result);

        result = DateTimeUtil.parseDate("31 December");
        assertEquals(LocalDate.of(currentYear, 12, 31), result);
    }

    @Test
    public void parseDate_pastDate_throwsException() {
        LocalDate pastDate = LocalDate.now().minusDays(1);
        String pastDateStr = pastDate.getDayOfMonth() + " "
                           + pastDate.getMonth().toString().substring(0, 3) + " "
                           + pastDate.getYear();

        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseDate(pastDateStr));
    }

    @Test
    public void parseTime_validFormats_success() {
        // Test time parsing
        LocalTime result;

        result = DateTimeUtil.parseTime("4pm");
        assertEquals(LocalTime.of(16, 0), result);

        result = DateTimeUtil.parseTime("4:30pm");
        assertEquals(LocalTime.of(16, 30), result);

        result = DateTimeUtil.parseTime("4.30pm");
        assertEquals(LocalTime.of(16, 30), result);

        result = DateTimeUtil.parseTime("16:30");
        assertEquals(LocalTime.of(16, 30), result);

        result = DateTimeUtil.parseTime("1630");
        assertEquals(LocalTime.of(16, 30), result);

        result = DateTimeUtil.parseTime("9am");
        assertEquals(LocalTime.of(9, 0), result);

        result = DateTimeUtil.parseTime("12pm");
        assertEquals(LocalTime.of(12, 0), result);

        result = DateTimeUtil.parseTime("12am");
        assertEquals(LocalTime.of(0, 0), result);
    }

    @Test
    public void parseTime_invalidFormat_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseTime("invalid time"));
        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseTime("25:00")); // Invalid hour
        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseTime("4:60")); // Invalid minute
    }

    @Test
    public void parseTime_nullOrEmpty_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseTime(null));
        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseTime(""));
        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseTime("   "));
    }

    @Test
    public void dateTimeParseResult_getters_success() {
        LocalDate date = LocalDate.of(2025, 3, 15);
        LocalTime time = LocalTime.of(16, 30);
        DateTimeUtil.DateTimeParseResult result = new DateTimeUtil.DateTimeParseResult(date, time);

        assertEquals(date, result.getDate());
        assertEquals(time, result.getTime());
        assertEquals(date.atTime(time), result.getDateTime());
    }
}
