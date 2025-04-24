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

public class OfficeHoursSearchController {
    @FXML private TextField nameField;
    @FXML private TableColumn<Course, String> colCourse;
    @FXML private TableColumn<Course, String> colScheduleDate;
    @FXML private TableColumn<Course, String> colTimeSlot;

    @FXML
    private TableView<OfficeHoursScheduleEntry> officeHoursTable;
    @FXML
    private TableColumn<OfficeHoursScheduleEntry, String> studentNameCol;
    @FXML
    private TableColumn<OfficeHoursScheduleEntry, LocalDate> scheduleDateCol;
    @FXML
    private TableColumn<OfficeHoursScheduleEntry, String> timeSlotCol;
    @FXML
    private TableColumn<OfficeHoursScheduleEntry, String> courseCol;
    @FXML
    private TableColumn<OfficeHoursScheduleEntry, String> reasonCol;
    @FXML
    private TableColumn<OfficeHoursScheduleEntry, String> commentCol;

    @FXML
    public void initialize() {
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        scheduleDateCol.setCellValueFactory(new PropertyValueFactory<>("scheduleDate"));
        timeSlotCol.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));


        ObservableList<OfficeHoursScheduleEntry> entries = ();

    }
    @FXML
    private void handleSearchSchedule() {

    }

    @FXML
    private void handleDeleteSchedule() {

    }

    private ObservableList<OfficeHoursScheduleEntry> loadOfficeHoursEntries() {
        ObservableList<OfficeHoursScheduleEntry> list = FXCollections.observableArrayList();
        try {
            List<String> lines = Files.readAllLines(Paths.get("office_hours_schedule.txt"));
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

