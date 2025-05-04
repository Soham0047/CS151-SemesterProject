package s25.cs151.application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import s25.cs151.application.model.OfficeHoursScheduleEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OfficeHoursScheduleController {
    @FXML private DatePicker scheduleDatePicker;
    @FXML private ComboBox<String> timeSlotCombo;
    @FXML private ComboBox<String> courseCombo;
    @FXML private TextField studentNameField;
    @FXML private TextArea reasonField;
    @FXML private TextArea commentField;
    
    private static final String DATA_FILE = "office_hours_schedule.txt";

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
    private void handleScheduleOfficeHours() {
        // Get values from form
        String studentName = studentNameField.getText().trim();
        LocalDate scheduleDate = scheduleDatePicker.getValue();
        String timeSlot = timeSlotCombo.getValue();
        String course = courseCombo.getValue();
        String reason = reasonField.getText().trim();
        String comment = commentField.getText().trim();
        
        // Validate fields
        if (studentName.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Student name is required");
            return;
        }
        
        if (scheduleDate == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Date is required");
            return;
        }
        
        if (timeSlot == null || timeSlot.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Time slot is required");
            return;
        }
        
        if (course == null || course.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Course is required");
            return;
        }
        
        if (reason.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Reason for meeting is required");
            return;
        }
        
        // Create entry and save to file
        OfficeHoursScheduleEntry entry = new OfficeHoursScheduleEntry(
                studentName, scheduleDate, timeSlot, course, reason, comment);
        
        saveEntryToFile(entry);
        
        // Clear form
        studentNameField.clear();
        reasonField.clear();
        commentField.clear();
        
        showAlert(Alert.AlertType.INFORMATION, "Success", "Office hours scheduled successfully!");
    }
    
    private void saveEntryToFile(OfficeHoursScheduleEntry entry) {
        // Format as CSV: studentName,scheduleDate,timeSlot,course,reason,comment
        String line = String.format("%s,%s,%s,%s,%s,%s",
                entry.getStudentName(),
                entry.getScheduleDate(),
                entry.getTimeSlot(),
                entry.getCourse(),
                entry.getReason(),
                entry.getComment());
        
        try {
            Files.write(
                    Paths.get(DATA_FILE),
                    (line + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "File Error", "Error saving schedule: " + e.getMessage());
        }
    }
    
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 