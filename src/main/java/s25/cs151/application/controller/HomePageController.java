package s25.cs151.application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomePageController {
    @FXML private Label welcomeLabel;
    @FXML private Label dateLabel;
    @FXML private Button startButton;

    @FXML
    public void initialize() {
        // Set the current date
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
        dateLabel.setText(today.format(formatter));
        
        // Set welcome message
        welcomeLabel.setText("Welcome to the Office Hours Manager");
        
        // Add action for start button if needed
        startButton.setOnAction(e -> handleStartButtonClick());
    }
    
    private void handleStartButtonClick() {
        // Handle button click - perhaps navigate to a getting started page
        System.out.println("Start button clicked");
    }
} 