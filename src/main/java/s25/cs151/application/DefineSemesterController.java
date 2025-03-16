package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

        // Dynamic footer year
        int currentYear = LocalDate.now().getYear();
        footerLabel.setText("© " + currentYear + " Faculty's Office Hours Manager. All rights reserved.");
    }

    @FXML
    private void handleSave() {
        // print out the form data debug purpose
        System.out.println("Semester: " + semesterField.getText());
        System.out.println("Year: " + yearField.getText());

        StringBuilder days = new StringBuilder("Days: ");
        if (monCheck.isSelected()) days.append("Monday ");
        if (tueCheck.isSelected()) days.append("Tuesday ");
        if (wedCheck.isSelected()) days.append("Wednesday ");
        if (thuCheck.isSelected()) days.append("Thursday ");
        if (friCheck.isSelected()) days.append("Friday ");
        System.out.println(days);

        // confirmation message
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Semester details saved!");
        alert.showAndWait();
    }
}
