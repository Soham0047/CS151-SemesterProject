package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.model.Course;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class DefineCoursesController {
    private static final String DATA_FILE = "courses_data.txt";

    @FXML
    private TextField codeField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField sectionField;
    @FXML
    private TableView<Course> coursesTable;
    @FXML
    private TableColumn<Course, String> colCode;
    @FXML
    private TableColumn<Course, String> colName;
    @FXML
    private TableColumn<Course, String> colSection;

    private ObservableList<Course> courseList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up columns
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));
        
        // Bind data
        coursesTable.setItems(courseList);
        
        // Load existing data
        loadCourses();
    }

    private void loadCourses() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(DATA_FILE));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    courseList.add(new Course(parts[0], parts[1], parts[2]));
                }
            }
            coursesTable.sort();
        } catch (IOException e) {
            // File might not exist yet, that's OK
            System.out.println("No data file exists yet: " + e.getMessage());
        }
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

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 