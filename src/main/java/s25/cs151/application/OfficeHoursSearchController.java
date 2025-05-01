package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OfficeHoursSearchController {
    @FXML private TextField nameField;
    @FXML private TableColumn<Course, String> colCourse;
    @FXML private TableColumn<Course, String> colScheduleDate;
    @FXML private TableColumn<Course, String> colTimeSlot;

    @FXML
    private TableView<OfficeHoursSearchEntry> officeHoursTable;

    @FXML
    public void initialize() {
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        colScheduleDate.setCellValueFactory(new PropertyValueFactory<>("scheduleDate"));
        colTimeSlot.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));

    }
    @FXML
    private void handleSearchSchedule() {
        String studentNameInput = nameField.getText().trim().toLowerCase();

        if (studentNameInput.isEmpty()) {
            officeHoursTable.setItems(FXCollections.observableArrayList());
            return;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get("office_hours_schedule.txt"));

            List<OfficeHoursSearchEntry> matchingEntries = lines.stream()
                    .map(line -> line.split(",", -1))
                    .filter(parts -> parts.length >= 6)
                    .filter(parts -> parts[0].trim().toLowerCase().startsWith(studentNameInput))
                    .map(parts -> new OfficeHoursSearchEntry(
                            parts[0].trim(),
                            LocalDate.parse(parts[1].trim()),
                            parts[2].trim(),
                            parts[3].trim()
                    ))
                    .collect(Collectors.toList());

            //Sort by date descending, then time slot ascending
            matchingEntries.sort((e1, e2) -> {
                int dateComparison = e2.getScheduleDate().compareTo(e1.getScheduleDate());
                if (dateComparison != 0) {
                    return dateComparison;
                }
                return e1.getTimeSlot().compareToIgnoreCase(e2.getTimeSlot());
            });

            ObservableList<OfficeHoursSearchEntry> observableList = FXCollections.observableArrayList(matchingEntries);
            officeHoursTable.setItems(observableList);

        } catch (IOException e) {
            e.printStackTrace();
        }
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

