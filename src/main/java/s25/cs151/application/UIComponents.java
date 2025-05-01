package s25.cs151.application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UIComponents {

    public static HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10, 20, 10, 20));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #FFFFFF;");

        ImageView logoView = new ImageView(new Image(UIComponents.class.getResourceAsStream("/icons/schedule.png")));
        logoView.setFitWidth(40);
        logoView.setFitHeight(40);

        Label appTitle = new Label("Faculty's Office Hours Manager");
        appTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
        appTitle.setPadding(new Insets(0, 0, 0, 10));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        Label dateLabel = new Label("Today: " + today);
        dateLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 13px;");

        topBar.getChildren().addAll(logoView, appTitle, spacer, dateLabel);

        DropShadow ds = new DropShadow();
        ds.setRadius(3.0);
        ds.setOffsetY(2.0);
        ds.setColor(Color.color(0, 0, 0, 0.2));
        topBar.setEffect(ds);

        return topBar;
    }

    public static VBox createLeftSidebar() {
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #FFFFFF;");
        sidebar.setPrefWidth(220);

        // Create a simple nav button
        Button btnDashboard = new Button("Dashboard");
        btnDashboard.setMaxWidth(Double.MAX_VALUE);
        btnDashboard.setStyle("-fx-background-color: transparent; -fx-text-fill: #444; -fx-font-size: 14px; -fx-padding: 8 16;");

        // Add additional buttons as needed
        sidebar.getChildren().addAll(btnDashboard);

        DropShadow ds = new DropShadow();
        ds.setRadius(3.0);
        ds.setOffsetY(2.0);
        ds.setColor(Color.color(0, 0, 0, 0.1));
        sidebar.setEffect(ds);

        return sidebar;
    }

    public static ScrollPane createScheduleForm() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(30));
        container.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10;");
        container.setAlignment(Pos.TOP_LEFT);

        Label heading = new Label("Schedule Semester’s Office Hours");
        heading.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Simple form element
        TextField semesterField = new TextField("Spring");
        semesterField.setStyle("-fx-font-size: 14px; -fx-padding: 8;");

        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #506EF9; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 20;");
        saveButton.setOnAction(e -> {
            System.out.println("Semester: " + semesterField.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Semester details saved!");
            alert.showAndWait();
        });

        container.getChildren().addAll(heading, semesterField, saveButton);

        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background:transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        return scrollPane;
    }

    public static HBox createFooter() {
        HBox footer = new HBox();
        footer.setPadding(new Insets(10));
        footer.setAlignment(Pos.CENTER);
        Label footerLabel = new Label("© 2025 Faculty's Office Hours Manager. All rights reserved.");
        footerLabel.setStyle("-fx-text-fill: #888; -fx-font-size: 12px;");
        footer.getChildren().add(footerLabel);
        return footer;
    }
}
