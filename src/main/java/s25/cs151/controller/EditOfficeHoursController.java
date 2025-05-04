package s25.cs151.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;


public class EditOfficeHoursController {
    @FXML private TextField searchField;
    @FXML private TableView<OfficeHoursScheduleEntry> table;
    @FXML private TableColumn<OfficeHoursScheduleEntry,String> colStudentName;
    @FXML private TableColumn<OfficeHoursScheduleEntry,LocalDate> colDate;
    @FXML private TableColumn<OfficeHoursScheduleEntry,String> colTimeSlot;
    @FXML private TableColumn<OfficeHoursScheduleEntry,String> colCourse;
    @FXML private TableColumn<OfficeHoursScheduleEntry,String> colReason;
    @FXML private TableColumn<OfficeHoursScheduleEntry,String> colComment;

    @FXML private VBox editForm;
    @FXML private TextField editNameField, editReasonField, editCommentField;
    @FXML private DatePicker editDatePicker;
    @FXML private ComboBox<String> editTimeSlotCombo, editCourseCombo;

    private ObservableList<OfficeHoursScheduleEntry> masterList;
    private int editingIndex = -1;

    @FXML
    public void initialize() {
        // wire up columns
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("scheduleDate"));
        colTimeSlot.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        colReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        colComment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        // load & sort data
        masterList = loadEntries();
        sortDescending(masterList);
        table.setItems(masterList);

        // populate edit-time-slot and edit-course combos
        populateTimeSlots();
        populateCourses();

        editForm.setVisible(false);
    }

    private void populateTimeSlots() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("h:mm a");
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m += 15) {
                LocalTime start = LocalTime.of(h, m);
                LocalTime end   = start.plusMinutes(15);
                String label    = start.format(fmt) + " – " + end.format(fmt);
                editTimeSlotCombo.getItems().add(label);
            }
        }
    }


    private void populateCourses() {
        try {
            Files.readAllLines(Paths.get("courses_data.txt"))
                    .forEach(line -> editCourseCombo.getItems().add(line.split(",",3)[0].trim()));
        } catch (IOException e) {
            // fallback
            editCourseCombo.getItems().add("CS151-04");
        }
    }

    @FXML
    private void handleSearch() {
        String term = searchField.getText().toLowerCase();
        List<OfficeHoursScheduleEntry> filtered = masterList.stream()
                .filter(e -> term.isEmpty() ||
                        e.getStudentName().toLowerCase().contains(term))
                .collect(Collectors.toList());
        sortDescending(filtered);
        table.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void handleEditSelected() {
        OfficeHoursScheduleEntry sel = table.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert("Please select a row to edit.");
            return;
        }
        editingIndex = masterList.indexOf(sel);

        // prefill form
        editNameField.setText(sel.getStudentName());
        editDatePicker.setValue(sel.getScheduleDate());
        editTimeSlotCombo.setValue(sel.getTimeSlot());
        editCourseCombo.setValue(sel.getCourse());
        editReasonField.setText(sel.getReason());
        editCommentField.setText(sel.getComment());

        editForm.setVisible(true);
    }

    @FXML
    private void handleSaveChanges() {
        if (editingIndex < 0) return;

        // gather edited values
        String name    = editNameField.getText().trim();
        LocalDate dt   = editDatePicker.getValue();
        String slot    = editTimeSlotCombo.getValue();
        String course  = editCourseCombo.getValue();
        String reason  = editReasonField.getText().trim();
        String comment = editCommentField.getText().trim();

        // replace in masterList
        OfficeHoursScheduleEntry updated =
                new OfficeHoursScheduleEntry(name, dt, slot, course, reason, comment);
        masterList.set(editingIndex, updated);

        // persist all back to file
        saveAll(masterList);

        // refresh table
        sortDescending(masterList);
        table.setItems(masterList);

        editForm.setVisible(false);
        showAlert("Changes saved successfully.");
    }

    private ObservableList<OfficeHoursScheduleEntry> loadEntries() {
        ObservableList<OfficeHoursScheduleEntry> list = FXCollections.observableArrayList();
        try {
            List<String> lines = Files.readAllLines(Paths.get("office_hours_schedule.txt"));
            for (String ln : lines) {
                if (ln.isBlank()) continue;
                String[] p = ln.split(",", -1);
                if (p.length>=6) {
                    list.add(new OfficeHoursScheduleEntry(
                            p[0].trim(),
                            LocalDate.parse(p[1].trim(), DateTimeFormatter.ISO_LOCAL_DATE),
                            p[2].trim(), p[3].trim(), p[4].trim(), p[5].trim()
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void saveAll(List<OfficeHoursScheduleEntry> list) {
        try {
            List<String> lines = list.stream()
                    .map(e -> String.join(",",
                            e.getStudentName(),
                            e.getScheduleDate().toString(),
                            e.getTimeSlot(),
                            e.getCourse(),
                            e.getReason(),
                            e.getComment()))
                    .collect(Collectors.toList());
            Files.write(Paths.get("office_hours_schedule.txt"), lines,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sortDescending(List<OfficeHoursScheduleEntry> list) {
        list.sort(Comparator
                .comparing(OfficeHoursScheduleEntry::getScheduleDate).reversed()
                .thenComparing(OfficeHoursScheduleEntry::getTimeSlot).reversed());
    }

    private void showAlert(String msg) {
        new Alert(AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }
}
