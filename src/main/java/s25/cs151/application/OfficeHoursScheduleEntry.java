package s25.cs151.application;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;
import java.util.Objects;

public class OfficeHoursScheduleEntry extends ScheduleEntry{
    private final String reason;
    private final String comment;

    public OfficeHoursScheduleEntry(String studentName, LocalDate scheduleDate, String timeSlot,
                                    String course, String reason, String comment) {
        super(studentName, course, scheduleDate, timeSlot);
        this.reason = Objects.requireNonNullElse(reason, "");
        this.comment = Objects.requireNonNullElse(comment, "");
    }

    public String getReason() {
        return reason;
    }

    public String getComment() {
        return comment;
    }
}
