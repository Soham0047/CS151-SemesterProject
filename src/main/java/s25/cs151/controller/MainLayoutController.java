package s25.cs151.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainLayoutController {
    // Top Bar
    @FXML
    private Label dateLabel;

    // Center content pane where different routes are displayed.
    @FXML
    private AnchorPane centerContent;

    // Left Sidebar buttons – ensure all IDs match those in your FXML
    @FXML
    private Button btnHome, btnDashboard, btnSemester, btnTime, btnAppointment, btnCourses, btnSearch, btnReports, btnOfficeHours;

    // Footer label (or any other form field)
    @FXML
    private Label footerLabel;

    @FXML
    public void initialize() {
        // Load the home page by default
        loadCenter("HomePage.fxml");

        // Set up navigation for each button
        btnHome.setOnAction(e -> loadCenter("HomePage.fxml"));
        btnDashboard.setOnAction(e -> loadCenter("Dashboard.fxml"));         // if defined
        btnSemester.setOnAction(e -> loadCenter("DefineSemester.fxml"));
        btnTime.setOnAction(e -> loadCenter("DefineTimeSlot.fxml"));
        btnCourses.setOnAction(e -> loadCenter("DefineCourses.fxml"));
        btnReports.setOnAction(e -> loadCenter("Reports.fxml"));
        btnOfficeHours.setOnAction(e -> loadCenter("EditOfficeHours.fxml"));
        btnAppointment.setOnAction(e -> loadCenter("OfficeHoursSchedule.fxml"));
        btnSearch.setOnAction(e -> loadCenter("OfficeHoursSearch.fxml"));

        // Set today's date on the top bar
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        dateLabel.setText("Today: " + today);

        // Set dynamic footer with the current year
        int currentYear = LocalDate.now().getYear();
        footerLabel.setText("© " + currentYear + " Faculty's Office Hours Manager. All rights reserved.");
    }

    // Utility method to load an FXML file into the centerContent pane.
    private void loadCenter(String fxml) {
        try {
            Parent content = FXMLLoader.load(getClass().getResource("/" + fxml));
            centerContent.getChildren().setAll(content);

            // Set anchor constraints so the loaded content fills the pane.
            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hover enter event – changes button style when mouse hovers over.
     */
    @FXML
    private void handleHoverEntered(MouseEvent event) {
        Button b = (Button) event.getSource();
        b.setStyle("-fx-background-color: #EEF3FF;"
                + "-fx-text-fill: #444;"
                + "-fx-alignment: center-left;"
                + "-fx-font-family: 'Segoe UI', sans-serif;"
                + "-fx-font-size: 14;"
                + "-fx-padding: 8 16;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;");
    }

    /**
     * Hover exit event – resets button style when mouse leaves.
     */
    @FXML
    private void handleHoverExited(MouseEvent event) {
        Button b = (Button) event.getSource();
        b.setStyle("-fx-background-color: transparent;"
                + "-fx-text-fill: #444;"
                + "-fx-alignment: center-left;"
                + "-fx-font-family: 'Segoe UI', sans-serif;"
                + "-fx-font-size: 14;"
                + "-fx-padding: 8 16;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;");
    }

    @FXML
    private void handleOfficeHours() {
        loadCenter("EditOfficeHours.fxml");
    }
}
