package s25.cs151.application;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;

public class OfficeHoursScheduleEntry {
    private final SimpleStringProperty studentName;
    private final SimpleObjectProperty<LocalDate> scheduleDate;
    private final SimpleStringProperty timeSlot;
    private final SimpleStringProperty course;
    private final SimpleStringProperty reason;
    private final SimpleStringProperty comment;

    public OfficeHoursScheduleEntry(String studentName, LocalDate scheduleDate, String timeSlot,
                                    String course, String reason, String comment) {
        this.studentName = new SimpleStringProperty(studentName);
        this.scheduleDate = new SimpleObjectProperty<>(scheduleDate);
        this.timeSlot = new SimpleStringProperty(timeSlot);
        this.course = new SimpleStringProperty(course);
        this.reason = new SimpleStringProperty(reason);
        this.comment = new SimpleStringProperty(comment);
    }

    public String getStudentName() {
        return studentName.get();
    }

    public LocalDate getScheduleDate() {
        return scheduleDate.get();
    }

    public String getTimeSlot() {
        return timeSlot.get();
    }

    public String getCourse() {
        return course.get();
    }

    public String getReason() {
        return reason.get();
    }

    public String getComment() {
        return comment.get();
    }
}
