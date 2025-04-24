package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefineCoursesController {

    @FXML private TextField codeField;
    @FXML private TextField nameField;
    @FXML private TextField sectionField;
    @FXML private TableView<Course> coursesTable;
    @FXML private TableColumn<Course, String> colCode;
    @FXML private TableColumn<Course, String> colName;
    @FXML private TableColumn<Course, String> colSection;

    private ObservableList<Course> courseList;

    private final String DATA_FILE = "office_hours_schedule.txt";

    @FXML
    public void initialize() {
        // Prepare columns
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));

        courseList = FXCollections.observableArrayList();
        coursesTable.setItems(courseList);

        // Sort ascending on course code
        colCode.setSortType(TableColumn.SortType.ASCENDING);
        coursesTable.getSortOrder().add(colCode);

        loadFromFile();
        // Sort now that we've loaded
        coursesTable.sort();
    }

    @FXML
    private void handleAddCourse() {
        String code = codeField.getText().trim();
        String name = nameField.getText().trim();
        String section = sectionField.getText().trim();

        if (code.isEmpty() || name.isEmpty() || section.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields are required.");
            return;
        }

        // Create a new Course
        Course newCourse = new Course(code, name, section);

        // Check for duplicates
        if (isDuplicate(newCourse)) {
            showAlert(Alert.AlertType.ERROR, "Duplicate Entry",
                    "This course (code, name, section) already exists.");
            return;
        }

        // Add to the list
        courseList.add(newCourse);
        coursesTable.sort();

        // Save to file
        appendToFile(newCourse);

        // Clear fields
        codeField.clear();
        nameField.clear();
        sectionField.clear();

        showAlert(Alert.AlertType.INFORMATION, "Success", "Course added successfully!");
    }

    private boolean isDuplicate(Course c) {
        // Check if c already in courseList
        return courseList.contains(c);
    }

    private void appendToFile(Course c) {
        String line = c.getCode() + "," + c.getName() + "," + c.getSection();
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

    private void loadFromFile() {
        try {
            if (Files.exists(Paths.get(DATA_FILE))) {
                List<String> lines = Files.readAllLines(Paths.get(DATA_FILE));
                for (String line : lines) {
                    String[] parts = line.split(",", 3);
                    if (parts.length == 3) {
                        Course c = new Course(parts[0].trim(), parts[1].trim(), parts[2].trim());
                        courseList.add(c);
                    }
                }
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "File Error", "Error reading from file: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
