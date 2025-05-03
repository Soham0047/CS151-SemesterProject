package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OfficeHoursSearchController {
    @FXML private TextField nameField;
    @FXML private TableView<OfficeHoursScheduleEntry> officeHoursTable;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> studentNameCol;
    @FXML private TableColumn<OfficeHoursScheduleEntry, LocalDate> scheduleDateCol;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> timeSlotCol;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> courseCol;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> reasonCol;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> commentCol;

    @FXML
    public void initialize() {
        // wire up the columns
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        scheduleDateCol.setCellValueFactory(new PropertyValueFactory<>("scheduleDate"));
        timeSlotCol.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));

        // load & sort all entries
        List<OfficeHoursScheduleEntry> all = loadEntries();
        all.sort(Comparator
                .comparing(OfficeHoursScheduleEntry::getScheduleDate).reversed()
                .thenComparing(OfficeHoursScheduleEntry::getTimeSlot).reversed()
        );
        officeHoursTable.setItems(FXCollections.observableArrayList(all));
    }

    @FXML
    private void handleSearchSchedule() {
        String term = nameField.getText().toLowerCase().trim();
        List<OfficeHoursScheduleEntry> all = loadEntries();
        List<OfficeHoursScheduleEntry> filtered = all.stream()
                .filter(e -> term.isEmpty() ||
                        e.getStudentName().toLowerCase().contains(term))
                .collect(Collectors.toList());
        filtered.sort(Comparator
                .comparing(OfficeHoursScheduleEntry::getScheduleDate).reversed()
                .thenComparing(OfficeHoursScheduleEntry::getTimeSlot).reversed()
        );
        officeHoursTable.setItems(FXCollections.observableArrayList(filtered));
    }

    private List<OfficeHoursScheduleEntry> loadEntries() {
        try {
            return Files.readAllLines(Paths.get("office_hours_schedule.txt")).stream()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(line -> {
                        String[] p = line.split(",", -1);
                        return new OfficeHoursScheduleEntry(
                                p[0].trim(),
                                LocalDate.parse(p[1].trim()),
                                p[2].trim(),
                                p[3].trim(),
                                p.length>4 ? p[4].trim() : "",
                                p.length>5 ? p[5].trim() : ""
                        );
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
