package s25.cs151.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class DefineSemesterController {
    // Form fields
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ComboBox<String> semesterDropdown;
    @FXML
    private TextField yearField;
    @FXML
    private CheckBox monCheck;
    @FXML
    private CheckBox tueCheck;
    @FXML
    private CheckBox wedCheck;
    @FXML
    private CheckBox thuCheck;
    @FXML
    private CheckBox friCheck;

    public void initialize() {
        rootPane.setFocusTraversable(false);
    }

    @FXML
    private void handleSave() {
        //Gather values from the UI
        String semester = semesterDropdown.getValue();
        String year = yearField.getText();

        // print out the form data debug purpose
        System.out.println("Semester: " + semester);
        System.out.println("Year: " + year);

        //List of selected days
        List<String> days = new ArrayList<>();
        if (monCheck.isSelected()) days.add("Monday");
        if (tueCheck.isSelected()) days.add("Tuesday");
        if (wedCheck.isSelected()) days.add("Wednesday");
        if (thuCheck.isSelected()) days.add("Thursday");
        if (friCheck.isSelected()) days.add("Friday");
        System.out.println(days);

        //Format data into a single line
        //EX) "Spring,2025,Monday;Tuesday;Friday"
        String data = semester + "," + year + "," + String.join(" ", days);

        // confirmation message
        try{
            Files.write(
                    Paths.get("semester_data.txt"),
                    (data + System.lineSeparator()).getBytes(),     //add a new line
                    StandardOpenOption.CREATE,                      //create file if it doesn't exist
                    StandardOpenOption.APPEND                       //append to the end
            );
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Semester details saved!");
            alert.showAndWait();
        } catch (IOException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error saving data: " + e.getMessage());
            alert.showAndWait();

        }
    }
}
