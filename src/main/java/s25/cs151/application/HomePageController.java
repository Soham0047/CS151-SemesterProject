package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomePageController {

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
    private Button btnHome;
    @FXML
    private Button btnSemester;
    @FXML
    private Button btnAppointment;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnReports;

    // Center
    @FXML
    private AnchorPane homeCenterPane;
    @FXML
    private Label welcomeLabel;
    @FXML
    private ImageView userPhotoView;
    @FXML
    private Label instructionLabel;

    @FXML
    private Label footerLabel;

    @FXML
    public void initialize() {
        // Setting today's date in the dateLabel
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        dateLabel.setText("Today: " + today);
        btnHome.setOnAction(e -> handleHome());
        btnSemester.setOnAction(e -> handleSemester());

        // Dynamic footer year
        int currentYear = LocalDate.now().getYear();
        footerLabel.setText("© " + currentYear + " Faculty's Office Hours Manager. All rights reserved.");

    }

    private void handleHome() {
        // Already on Home page
    }

    private void handleSemester() {
        // Navigate to DefineSemester.fxml or some other page
        Main.setRoot("DefineSemester.fxml");
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
