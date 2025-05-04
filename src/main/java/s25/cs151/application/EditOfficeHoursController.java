package s25.cs151.application;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EditOfficeHoursController {
    @FXML private TextField searchField;
    @FXML private TableView<OfficeHoursScheduleEntry> table;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> colStudentName;
    @FXML private TableColumn<OfficeHoursScheduleEntry, LocalDate> colDate;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> colTimeSlot;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> colCourse;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> colReason;
    @FXML private TableColumn<OfficeHoursScheduleEntry, String> colComment;

    @FXML private VBox editForm;
    @FXML private TextField editNameField, editReasonField, editCommentField;
    @FXML private DatePicker editDatePicker;
    @FXML private ComboBox<String> editTimeSlotCombo, editCourseCombo;

    private ObservableList<OfficeHoursScheduleEntry> masterList;
    private int editingIndex = -1;

    @FXML
    public void initialize() {
        // Set up table columns
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("scheduleDate"));
        colTimeSlot.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        colReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        colComment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        editForm.setVisible(false);
        masterList = FXCollections.observableArrayList();
        table.setItems(masterList);

        loadEntriesFromFile();
    }

    private void loadEntriesFromFile() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("office_hours_schedule.txt"));
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",", -1);
                if (parts.length < 6) continue;

                String name = parts[0].trim();
                LocalDate date = LocalDate.parse(parts[1].trim());
                String timeSlot = parts[2].trim();
                String course = parts[3].trim();
                String reason = parts[4].trim();
                String comment = parts[5].trim();

                masterList.add(new OfficeHoursScheduleEntry(name, date, timeSlot, course, reason, comment));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            table.setItems(masterList);
            return;
        }

        List<OfficeHoursScheduleEntry> filtered = masterList.stream()
                .filter(entry -> entry.getName().toLowerCase().contains(query))
                .collect(Collectors.toList());

        table.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void handleEditSelected() {
        OfficeHoursScheduleEntry selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(AlertType.WARNING, "No selection", "Please select a row to edit.");
            return;
        }

        editingIndex = masterList.indexOf(selected);

        editNameField.setText(selected.getStudentName());
        editDatePicker.setValue(selected.getScheduleDate());
        editTimeSlotCombo.setValue(selected.getTimeSlot());
        editCourseCombo.setValue(selected.getCourse());
        editReasonField.setText(selected.getReason());
        editCommentField.setText(selected.getComment());

        editForm.setVisible(true);
    }

    @FXML
    private void handleSaveChanges() {
        if (editingIndex < 0) {
            showAlert(AlertType.ERROR, "No edit in progress", "No row is being edited.");
            return;
        }

        String name = editNameField.getText().trim();
        LocalDate date = editDatePicker.getValue();
        String timeSlot = editTimeSlotCombo.getValue();
        String course = editCourseCombo.getValue();
        String reason = editReasonField.getText().trim();
        String comment = editCommentField.getText().trim();

        if (name.isEmpty() || date == null || timeSlot == null || course == null) {
            showAlert(AlertType.ERROR, "Missing Fields", "Please fill in all required fields.");
            return;
        }

        OfficeHoursScheduleEntry updated = new OfficeHoursScheduleEntry(name, date, timeSlot, course, reason, comment);
        masterList.set(editingIndex, updated);
        table.refresh();
        saveAllEntries();
        editForm.setVisible(false);
    }

    private void saveAllEntries() {
        List<String> lines = masterList.stream()
                .map(entry -> String.join(",",
                        entry.getName(),
                        entry.getScheduleDate().toString(),
                        entry.getTimeSlot(),
                        entry.getCourse(),
                        entry.getReason(),
                        entry.getComment()))
                .toList();

        try {
            Files.write(Paths.get("office_hours_schedule.txt"), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
