package s25.cs151.application;

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
    //Top Bar
    @FXML
    private Label dateLabel;

    //Center
    @FXML
    private AnchorPane centerContent;

    //Left Sidebar
    @FXML
    private Button btnHome, btnSemester, btnReports, btnTime, btnCourses;

    //Form Fields?
    @FXML
    private Label footerLabel;

    @FXML
    public void initialize() {
        loadCenter("HomePage.fxml"); //Load homepage as default

        btnHome.setOnAction(e -> loadCenter("HomePage.fxml"));
        btnSemester.setOnAction(e -> loadCenter("DefineSemester.fxml"));
        btnReports.setOnAction(e -> loadCenter("Reports.fxml"));
        btnTime.setOnAction(e -> loadCenter("DefineTimeSlot.fxml"));
        btnCourses.setOnAction(e -> loadCenter("DefineCourses.fxml"));

        // Set todays date in the dateLabel
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        dateLabel.setText("Today: " + today);

        // Dynamic footer year
        int currentYear = LocalDate.now().getYear();
        footerLabel.setText("© " + currentYear + " Faculty's Office Hours Manager. All rights reserved.");
    }

    private void loadCenter(String fxml) {
        try {
            Parent content = FXMLLoader.load(getClass().getResource("/" + fxml));
            centerContent.getChildren().setAll(content);

            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hover enter
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
     * Hover exit
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
}
