package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.model.OfficeHoursScheduleEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class OfficeHoursSearchController {
    @FXML private TextField searchField;
    @FXML private ComboBox<String> searchCriteriaCombo;
    @FXML private TableView<OfficeHoursScheduleEntry> resultsTable;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> studentNameCol;
    @FXML private TableColumn<OfficeHoursScheduleEntry, LocalDate> scheduleDateCol;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> timeSlotCol;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> courseCol;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> reasonCol;
    
    private ObservableList<OfficeHoursScheduleEntry> allEntries;
    private FilteredList<OfficeHoursScheduleEntry> filteredEntries;
    
    private static final String DATA_FILE = "office_hours_schedule.txt";

    @FXML
    public void initialize() {
        // Set up search criteria options
        searchCriteriaCombo.setItems(FXCollections.observableArrayList(
            "Student Name", "Course", "Date", "All Fields"
        ));
        searchCriteriaCombo.getSelectionModel().selectLast();
        
        // Configure table columns
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        scheduleDateCol.setCellValueFactory(new PropertyValueFactory<>("scheduleDate"));
        timeSlotCol.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));
        
        // Load all entries
        allEntries = loadAllEntries();
        
        // Create filtered list
        filteredEntries = new FilteredList<>(allEntries, p -> true);
        resultsTable.setItems(filteredEntries);
        
        // Set up search listener
        searchField.textProperty().addListener((observable, oldValue, newValue) -> 
            performSearch(newValue)
        );
    }
    
    private void performSearch(String searchText) {
        String criteria = searchCriteriaCombo.getValue();
        
        if (searchText == null || searchText.isEmpty()) {
            filteredEntries.setPredicate(entry -> true);
            return;
        }
        
        String searchLower = searchText.toLowerCase();
        
        filteredEntries.setPredicate(entry -> {
            if (criteria.equals("Student Name")) {
                return entry.getStudentName().toLowerCase().contains(searchLower);
            } else if (criteria.equals("Course")) {
                return entry.getCourse().toLowerCase().contains(searchLower);
            } else if (criteria.equals("Date")) {
                return entry.getScheduleDate().toString().contains(searchLower);
            } else {
                // All Fields
                return entry.getStudentName().toLowerCase().contains(searchLower) ||
                       entry.getCourse().toLowerCase().contains(searchLower) ||
                       entry.getScheduleDate().toString().contains(searchLower) ||
                       entry.getTimeSlot().toLowerCase().contains(searchLower) ||
                       entry.getReason().toLowerCase().contains(searchLower);
            }
        });
    }
    
    private ObservableList<OfficeHoursScheduleEntry> loadAllEntries() {
        ObservableList<OfficeHoursScheduleEntry> entries = FXCollections.observableArrayList();
        try {
            List<String> lines = Files.readAllLines(Paths.get(DATA_FILE));
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    String studentName = parts[0].trim();
                    LocalDate scheduleDate = LocalDate.parse(parts[1].trim());
                    String timeSlot = parts[2].trim();
                    String course = parts[3].trim();
                    String reason = parts[4].trim();
                    String comment = parts[5].trim();
                    entries.add(new OfficeHoursScheduleEntry(
                        studentName, scheduleDate, timeSlot, course, reason, comment
                    ));
                }
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Data Error", "Could not load office hours data: " + e.getMessage());
        }
        return entries;
    }
    
    @FXML
    private void handleClearSearch() {
        searchField.clear();
        filteredEntries.setPredicate(entry -> true);
    }
    
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 