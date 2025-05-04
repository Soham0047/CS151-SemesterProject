package s25.cs151.application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainLayoutController {
    @FXML private BorderPane mainBorderPane;
    @FXML private AnchorPane centerContent;
    
    // Navigation buttons
    @FXML private Button btnHome;
    @FXML private Button btnDashboard;
    @FXML private Button btnSemester;
    @FXML private Button btnCourses;
    @FXML private Button btnSchedule;
    @FXML private Button btnSearch;
    @FXML private Button btnReports;
    @FXML private Button btnSettings;

    @FXML
    public void initialize() {
        // Load the home page by default
        loadCenter("HomePage.fxml");

        // Set up navigation for each button
        btnHome.setOnAction(e -> loadCenter("HomePage.fxml"));
        btnDashboard.setOnAction(e -> loadCenter("Dashboard.fxml"));
        btnSemester.setOnAction(e -> loadCenter("DefineSemester.fxml"));
        btnCourses.setOnAction(e -> loadCenter("DefineCourses.fxml"));
        btnSchedule.setOnAction(e -> loadCenter("OfficeHoursSchedule.fxml"));
        btnSearch.setOnAction(e -> loadCenter("OfficeHoursSearch.fxml"));
        btnReports.setOnAction(e -> loadCenter("Reports.fxml"));
        btnSettings.setOnAction(e -> loadCenter("Settings.fxml"));
    }

    /**
     * Loads FXML file into the center content area
     * @param fxmlFile Name of the FXML file to load (should be in resources)
     */
    private void loadCenter(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlFile));
            AnchorPane content = loader.load();
            centerContent.getChildren().setAll(content);
            
            // Make the loaded content fill the available space
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
    private void handleButtonHoverEnter() {
        // Implement hover effects if needed
    }

    /**
     * Hover exit event – returns button to normal state when mouse exits.
     */
    @FXML
    private void handleButtonHoverExit() {
        // Implement hover exit effects if needed
    }
} 