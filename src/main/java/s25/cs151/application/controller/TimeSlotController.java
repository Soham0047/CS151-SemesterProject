package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.model.TimeSlotEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class TimeSlotController {
    private static final String DATA_FILE = "timeslots_data.txt";

    @FXML private TextField fromTimeField;
    @FXML private TextField toTimeField;
    @FXML private TableView<TimeSlotEntry> timeSlotTable;
    @FXML private TableColumn<TimeSlotEntry, String> colFromTime;
    @FXML private TableColumn<TimeSlotEntry, String> colToTime;

    private ObservableList<TimeSlotEntry> timeSlotList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up columns
        colFromTime.setCellValueFactory(new PropertyValueFactory<>("fromTime"));
        colToTime.setCellValueFactory(new PropertyValueFactory<>("toTime"));
        
        // Bind data
        timeSlotTable.setItems(timeSlotList);
        
        // Load existing time slots
        loadTimeSlots();
    }

    private void loadTimeSlots() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(DATA_FILE));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    timeSlotList.add(new TimeSlotEntry(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            // File might not exist yet, that's OK
            System.out.println("No time slot data file exists yet: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddTimeSlot() {
        String fromTime = fromTimeField.getText().trim();
        String toTime = toTimeField.getText().trim();
        
        if (fromTime.isEmpty() || toTime.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Both from time and to time are required.");
            return;
        }
        
        // Add new time slot
        TimeSlotEntry newEntry = new TimeSlotEntry(fromTime, toTime);
        timeSlotList.add(newEntry);
        
        // Save to file
        appendToFile(newEntry);
        
        // Clear fields
        fromTimeField.clear();
        toTimeField.clear();
        
        showAlert(Alert.AlertType.INFORMATION, "Success", "Time slot added successfully!");
    }

    private void appendToFile(TimeSlotEntry entry) {
        String line = entry.getFromTime() + "," + entry.getToTime();
        try {
            Files.write(
                    Paths.get(DATA_FILE),
                    (line + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "File Error", "Error writing to file: " + e.getMessage());
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