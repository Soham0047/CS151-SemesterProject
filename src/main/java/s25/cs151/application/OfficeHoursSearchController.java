package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
<<<<<<< HEAD
import java.util.List;
import java.util.stream.Collectors;
=======
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

>>>>>>> assignment-code-version-8-sb

public class OfficeHoursSearchController {
    @FXML private TextField nameField;
    @FXML private TableColumn<Course, String> colCourse;
    @FXML private TableColumn<Course, String> colScheduleDate;
    @FXML private TableColumn<Course, String> colTimeSlot;

    @FXML
    private TableView<OfficeHoursSearchEntry> officeHoursTable;

    @FXML
    public void initialize() {
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        scheduleDateCol.setCellValueFactory(new PropertyValueFactory<>("scheduleDate"));
        timeSlotCol.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));


        ObservableList<OfficeHoursScheduleEntry> entries = FXCollections.observableArrayList(loadOfficeHoursEntries());
        officeHoursTable.setItems(entries);
    }
    @FXML
    private void handleSearchSchedule() {
        String term = nameField.getText().toLowerCase().trim();
        List<OfficeHoursScheduleEntry> all = loadOfficeHoursEntries();
        List<OfficeHoursScheduleEntry> filtered = all.stream()
                .filter(e -> term.isEmpty() ||
                        e.getStudentName().toLowerCase().contains(term))
                .collect(Collectors.toList());

        // sort descending by date then slot:
        FXCollections.sort(
                FXCollections.observableArrayList(filtered),
                Comparator.comparing(OfficeHoursScheduleEntry::getScheduleDate).reversed()
                        .thenComparing(OfficeHoursScheduleEntry::getTimeSlot).reversed()
        );

        officeHoursTable.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void handleDeleteSchedule() {
        OfficeHoursSearchEntry selectedEntry = officeHoursTable.getSelectionModel().getSelectedItem();

        if (selectedEntry == null) {
            System.out.println("No entry selected to delete.");
            return;
        }

        // Remove from TableView
        officeHoursTable.getItems().remove(selectedEntry);

        try {
            //Read all lines from the file
            List<String> lines = Files.readAllLines(Paths.get("office_hours_schedule.txt"));

            //Rebuild the file without the deleted entry
            List<String> updatedLines = lines.stream()
                    .filter(line -> {
                        String[] parts = line.split(",", -1);
                        if (parts.length < 6) return true;

                        String studentName = parts[0].trim();
                        String scheduleDate = parts[1].trim();
                        String timeSlot = parts[2].trim();
                        String course = parts[3].trim();

                        // Match the same entry to delete
                        return !(studentName.equalsIgnoreCase(selectedEntry.getName()) &&
                                scheduleDate.equals(selectedEntry.getScheduleDate().toString()) &&
                                timeSlot.equals(selectedEntry.getTimeSlot()) &&
                                course.equals(selectedEntry.getCourse()));
                    })
                    .toList();

            //Write the updated lines back to the file
            Files.write(Paths.get("office_hours_schedule.txt"), updatedLines);

            System.out.println("Entry deleted successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

