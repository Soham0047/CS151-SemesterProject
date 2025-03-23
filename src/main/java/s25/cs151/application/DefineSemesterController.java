package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DefineSemesterController {

    // Top bar
    @FXML
    private HBox topBar;
    @FXML
    private ImageView logoView;
    @FXML
    private Label appTitle;
    @FXML
    private Label dateLabel;
    @FXML
    private Region topSpacer;

    // Left sidebar
    @FXML
    private VBox leftSidebar;
    @FXML
    private Button btnHome;       // Home button
    @FXML
    private Button btnSemester;
    @FXML
    private Button btnAppointment;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnReports;

    // Form fields
    @FXML
    private TextField semesterField;
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
    @FXML
    private Button saveButton;
    @FXML
    private Label footerLabel;

    public void initialize() {
        // Set todays date in the dateLabel
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        dateLabel.setText("Today: " + today);

        // Home button to go back to HomePage
        btnHome.setOnAction(e -> Main.setRoot("HomePage.fxml"));

        // Go to reports page
        btnReports.setOnAction(e -> Main.setRoot("Reports.fxml"));

        // Dynamic footer year
        int currentYear = LocalDate.now().getYear();
        footerLabel.setText("© " + currentYear + " Faculty's Office Hours Manager. All rights reserved.");
    }

    @FXML
    private void handleSave() {
        //Gather values from the UI
        String semester = semesterField.getText();
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
