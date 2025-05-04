package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.model.SemesterEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class DefineSemesterController {
    private static final String DATA_FILE = "semesters_data.txt";

    @FXML private ComboBox<String> semesterComboBox;
    @FXML private TextField yearField;
    @FXML private CheckBox mondayCheck;
    @FXML private CheckBox tuesdayCheck;
    @FXML private CheckBox wednesdayCheck;
    @FXML private CheckBox thursdayCheck;
    @FXML private CheckBox fridayCheck;
    @FXML private TableView<SemesterEntry> semesterTable;
    @FXML private TableColumn<SemesterEntry, String> colSemester;
    @FXML private TableColumn<SemesterEntry, String> colYear;
    @FXML private TableColumn<SemesterEntry, String> colDays;

    private ObservableList<SemesterEntry> semesterList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize semester dropdown
        semesterComboBox.setItems(FXCollections.observableArrayList(
                "Spring", "Summer", "Fall", "Winter"));
        
        // Set up columns
        colSemester.setCellValueFactory(new PropertyValueFactory<>("semester"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colDays.setCellValueFactory(new PropertyValueFactory<>("days"));
        
        // Bind data
        semesterTable.setItems(semesterList);
        
        // Load existing semesters
        loadSemesters();
    }

    private void loadSemesters() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(DATA_FILE));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    semesterList.add(new SemesterEntry(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            // File might not exist yet, that's OK
            System.out.println("No semester data file exists yet: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddSemester() {
        String semester = semesterComboBox.getValue();
        String year = yearField.getText().trim();
        
        if (semester == null || year.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Semester and year are required.");
            return;
        }
        
        // Get selected days
        StringBuilder days = new StringBuilder();
        if (mondayCheck.isSelected()) days.append("M");
        if (tuesdayCheck.isSelected()) days.append("T");
        if (wednesdayCheck.isSelected()) days.append("W");
        if (thursdayCheck.isSelected()) days.append("Th");
        if (fridayCheck.isSelected()) days.append("F");
        
        if (days.length() == 0) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "At least one day must be selected.");
            return;
        }

        // Add new semester entry
        SemesterEntry newEntry = new SemesterEntry(semester, year, days.toString());
        semesterList.add(newEntry);
        
        // Save to file
        appendToFile(newEntry);
        
        // Clear fields
        semesterComboBox.setValue(null);
        yearField.clear();
        mondayCheck.setSelected(false);
        tuesdayCheck.setSelected(false);
        wednesdayCheck.setSelected(false);
        thursdayCheck.setSelected(false);
        fridayCheck.setSelected(false);
        
        showAlert(Alert.AlertType.INFORMATION, "Success", "Semester added successfully!");
    }

    private void appendToFile(SemesterEntry entry) {
        String line = entry.getSemester() + "," + entry.getYear() + "," + entry.getDays();
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