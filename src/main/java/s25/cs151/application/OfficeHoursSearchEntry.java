package s25.cs151.application;

import java.time.LocalDate;

public class OfficeHoursSearchEntry extends ScheduleEntry{

    public OfficeHoursSearchEntry(String studentName, LocalDate scheduleDate, String course, String timeSlot) {
        super(studentName, course, scheduleDate, timeSlot);
    }
}
