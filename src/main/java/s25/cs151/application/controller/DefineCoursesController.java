package s25.cs151.application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import s25.cs151.application.model.Course;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DefineCoursesController {
    // ... existing code ...
    
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

        // ... rest of the method ...
    }
    
    // ... rest of the class ...
} 