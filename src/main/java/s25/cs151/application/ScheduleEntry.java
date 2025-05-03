package s25.cs151.application;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public abstract class ScheduleEntry {
    private final String studentName;
    private final String course;
    private final LocalDate scheduleDate;
    private final String timeSlot;


    public ScheduleEntry(String name, String course, LocalDate scheduleDate, String timeSlot) {
        this.studentName = name;
        this.course = course;
        this.scheduleDate = scheduleDate;
        this.timeSlot = timeSlot;
    }

    public String getName() {
        return studentName;
    }

    public String getCourse() {
        return course;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }
}

