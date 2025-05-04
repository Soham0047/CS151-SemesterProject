package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.model.OfficeHoursScheduleEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class OfficeHoursScheduleReportController {
    @FXML private TableView<OfficeHoursScheduleEntry> officeHoursTable;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> studentNameCol;
    @FXML private TableColumn<OfficeHoursScheduleEntry, LocalDate> scheduleDateCol;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> timeSlotCol;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> courseCol;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> reasonCol;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> commentCol;
    
    private static final String DATA_FILE = "office_hours_schedule.txt";

    @FXML
    public void initialize() {
        // Configure the table columns
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        scheduleDateCol.setCellValueFactory(new PropertyValueFactory<>("scheduleDate"));
        timeSlotCol.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
        
        // Load data
        ObservableList<OfficeHoursScheduleEntry> entries = loadOfficeHoursEntries();
        officeHoursTable.setItems(entries);
    }
    
    private ObservableList<OfficeHoursScheduleEntry> loadOfficeHoursEntries() {
        ObservableList<OfficeHoursScheduleEntry> list = FXCollections.observableArrayList();
        try {
            List<String> lines = Files.readAllLines(Paths.get(DATA_FILE));
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;  // skip any empty lines
                // Expecting: studentName,scheduleDate,timeSlot,course,reason,comment
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    String studentName = parts[0].trim();
                    // Parse the schedule date string into a LocalDate.
                    LocalDate scheduleDate = LocalDate.parse(parts[1].trim());
                    String timeSlot = parts[2].trim();
                    String course = parts[3].trim();
                    String reason = parts[4].trim();
                    String comment = parts[5].trim();
                    OfficeHoursScheduleEntry entry = new OfficeHoursScheduleEntry(studentName, scheduleDate,
                            timeSlot, course, reason, comment);
                    list.add(entry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
} 