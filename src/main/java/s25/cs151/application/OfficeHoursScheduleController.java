package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OfficeHoursScheduleController {

    @FXML
    private TextField studentNameField;       // Student's full name
    @FXML
    private DatePicker scheduleDatePicker;      // Schedule date (default = today)
    @FXML
    private ComboBox<String> timeSlotCombo;     // Time Slot (generated for the entire day)
    @FXML
    private ComboBox<String> courseCombo;       // Course (populated from file)
    @FXML
    private TextField reasonField;              // Reason
    @FXML
    private TextField commentField;             // Comment
    @FXML
    private Button saveButton;                  // Save button

    @FXML
    public void initialize() {
        // Set DatePicker to today's date.
        scheduleDatePicker.setValue(LocalDate.now());

        // Generate time slots from 12:00 AM - 12:15 AM to 11:45 PM - 12:00 AM in 15-minute increments.
        timeSlotCombo.getItems().clear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute += 15) {
                LocalTime start = LocalTime.of(hour, minute);
                LocalTime end = start.plusMinutes(15);
                String slot = start.format(formatter) + " - " + end.format(formatter);
                timeSlotCombo.getItems().add(slot);
            }
        }
        if (!timeSlotCombo.getItems().isEmpty()) {
            timeSlotCombo.setValue(timeSlotCombo.getItems().get(0));
        }

        // Load Courses from file ("courses_data.txt")
        try {
            List<String> courses = Files.readAllLines(Paths.get("courses_data.txt"));
            if (!courses.isEmpty()) {
                // Assume each line is in the format: code,name,section
                for (String courseLine : courses) {
                    String[] parts = courseLine.split(",", 3);
                    if (parts.length >= 1) {
                        // Only the course code is displayed
                        courseCombo.getItems().add(parts[0].trim());
                    }
                }
                if (!courseCombo.getItems().isEmpty()) {
                    courseCombo.setValue(courseCombo.getItems().get(0));
                }
            }
        } catch (IOException e) {
            // Fallback course value
            courseCombo.getItems().add("CS151-04");
            courseCombo.setValue("CS151-04");
        }
    }

    @FXML
    private void handleSaveOfficeHours() {
        String studentName = studentNameField.getText().trim();
        LocalDate scheduleDate = scheduleDatePicker.getValue();
        String timeSlot = timeSlotCombo.getValue();
        String course = courseCombo.getValue();
        String reason = reasonField.getText().trim();
        String comment = commentField.getText().trim();

        // Validate required fields.
        if (studentName.isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "Student's full name is required.");
            return;
        }
        if (scheduleDate == null) {
            showAlert(AlertType.ERROR, "Validation Error", "Schedule date is required.");
            return;
        }
        if (timeSlot == null || timeSlot.isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "Time slot selection is required.");
            return;
        }
        if (course == null || course.isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "Course selection is required.");
            return;
        }

        // Create a CSV-formatted line
        // Format: studentName,scheduleDate,timeSlot,course,reason,comment
        String data = studentName + "," + scheduleDate.toString() + "," + timeSlot + "," + course + "," + reason + "," + comment;
        try {
            Files.write(Paths.get("office_hours_schedule.txt"),
                    (data + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
            showAlert(AlertType.INFORMATION, "Success", "Office Hours Schedule saved successfully.");

            // Optionally, clear the fields.
            studentNameField.clear();
            reasonField.clear();
            commentField.clear();
            scheduleDatePicker.setValue(LocalDate.now());
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "File Error", "Error saving schedule: " + e.getMessage());
        }
    }

    // Utility method for showing alerts
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
