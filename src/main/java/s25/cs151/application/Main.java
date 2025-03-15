package s25.cs151.application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("Office Hours Manager");

        // --------------------------------
        // 1) CREATE THE ROOT LAYOUT
        // --------------------------------
        BorderPane root = new BorderPane();
        // Light grayish-blue background for the entire window
        root.setStyle("-fx-background-color: #F4F7FA;");

        // --------------------------------
        // 2) TOP BAR
        // --------------------------------
        HBox topBar = createTopBar();
        root.setTop(topBar);

        // --------------------------------
        // 3) LEFT SIDEBAR (NAVIGATION)
        // --------------------------------
        VBox leftSidebar = createLeftSidebar();
        root.setLeft(leftSidebar);

        // --------------------------------
        // 4) MAIN CONTENT (CENTER)
        // --------------------------------
        // We'll show the schedule form by default
        ScrollPane mainContent = createScheduleForm();
        root.setCenter(mainContent);

        // --------------------------------
        // 5) FOOTER
        // --------------------------------
        HBox footer = createFooter();
        root.setBottom(footer);

        // --------------------------------
        // SCENE SETUP
        // --------------------------------
        Scene scene = new Scene(root, 1080, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a top bar with a title/logo and a date label on the right side.
     */
    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10, 20, 10, 20));
        topBar.setAlignment(Pos.CENTER_LEFT);
        // White background for the top bar
        topBar.setStyle("-fx-background-color: #FFFFFF;");

        // Example: brand logo icon
        ImageView logoView = new ImageView(new Image(getClass().getResourceAsStream("/icons/schedule.png")));
        logoView.setFitWidth(40);
        logoView.setFitHeight(40);

        Label appTitle = new Label("Faculty's Office Hours Manager");
        appTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
        appTitle.setPadding(new Insets(0, 0, 0, 10));

        // Spacer to push date label to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Date label on the right
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        Label dateLabel = new Label("Today: " + today);
        dateLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 13px;");

        topBar.getChildren().addAll(logoView, appTitle, spacer, dateLabel);

        // Subtle drop shadow for a layered effect
        DropShadow ds = new DropShadow();
        ds.setRadius(3.0);
        ds.setOffsetY(2.0);
        ds.setColor(Color.color(0, 0, 0, 0.2));
        topBar.setEffect(ds);

        return topBar;
    }

    /**
     * Creates a left sidebar with nav buttons and icons.
     */
    private VBox createLeftSidebar() {
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #FFFFFF;"); // White "card"
        sidebar.setPrefWidth(220);

        // Navigation items
        Button btnDashboard = createNavButton("Dashboard", "/icons/dashboard.png");
        Button btnSemester = createNavButton("Semester's Office Hours", "/icons/calendar.png");
        Button btnAppointment = createNavButton("Schedule Appointment", "/icons/appointment.png");
        Button btnSearch = createNavButton("Search Schedules", "/icons/search.png");
        Button btnReports = createNavButton("Reports", "/icons/report.png");

        // Show the schedule form when "Semester's Office Hours" is clicked
        btnSemester.setOnAction(e -> {
            ScrollPane form = createScheduleForm();
            ((BorderPane) primaryStage.getScene().getRoot()).setCenter(form);
        });

        sidebar.getChildren().addAll(
                btnDashboard,
                btnSemester,
                btnAppointment,
                btnSearch,
                btnReports
        );

        // Subtle drop shadow
        DropShadow ds = new DropShadow();
        ds.setRadius(3.0);
        ds.setOffsetX(0);
        ds.setOffsetY(2.0);
        ds.setColor(Color.color(0, 0, 0, 0.1));
        sidebar.setEffect(ds);

        return sidebar;
    }

    /**
     * Helper method to create a nav button with an icon.
     */
    private Button createNavButton(String text, String iconPath) {
        ImageView iconView = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
        iconView.setFitWidth(18);
        iconView.setFitHeight(18);

        Button btn = new Button(text, iconView);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: transparent; " +
                "-fx-text-fill: #444; " +
                "-fx-alignment: center-left; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 8 16; " +
                "-fx-border-radius: 8; -fx-background-radius: 8;");

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #EEF3FF; " +
                "-fx-text-fill: #444; " +
                "-fx-alignment: center-left; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 8 16; " +
                "-fx-border-radius: 8; -fx-background-radius: 8;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; " +
                "-fx-text-fill: #444; " +
                "-fx-alignment: center-left; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 8 16; " +
                "-fx-border-radius: 8; -fx-background-radius: 8;"));
        return btn;
    }

    /**
     * Creates the main "Schedule Semester’s Office Hours" form content.
     */
    private ScrollPane createScheduleForm() {
        // Container for all content
        VBox container = new VBox(10);
        container.setPadding(new Insets(30));
        // White card-like background with rounded corners
        container.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10;");
        container.setAlignment(Pos.TOP_LEFT);

        Label heading = new Label("Schedule Semester’s Office Hours");
        heading.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label subHeading = new Label("Fill out the form below to set your office hours for the current semester.");
        subHeading.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");

        // Form container
        VBox formBox = new VBox(15);
        formBox.setPadding(new Insets(20, 0, 0, 0));

        // Semester
        Label semesterLabel = new Label("Semester");
        semesterLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #333;");
        TextField semesterField = new TextField("Spring");
        semesterField.setStyle("-fx-font-size: 14px; -fx-padding: 8; -fx-text-fill: #333;");

        // Year
        Label yearLabel = new Label("Year");
        yearLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #333;");
        TextField yearField = new TextField("2025");
        yearField.setStyle("-fx-font-size: 14px; -fx-padding: 8; -fx-text-fill: #333;");

        // Days
        Label daysLabel = new Label("Select Days");
        daysLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #333;");
        CheckBox monCheck = new CheckBox("Monday");
        CheckBox tueCheck = new CheckBox("Tuesday");
        CheckBox wedCheck = new CheckBox("Wednesday");
        CheckBox thuCheck = new CheckBox("Thursday");
        CheckBox friCheck = new CheckBox("Friday");

        // Ensure checkboxes have visible text
        for (CheckBox cb : new CheckBox[] {monCheck, tueCheck, wedCheck, thuCheck, friCheck}) {
            cb.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
        }

        // FlowPane for days
        FlowPane daysPane = new FlowPane(20, 10, monCheck, tueCheck, wedCheck, thuCheck, friCheck);
        daysPane.setPadding(new Insets(0, 0, 0, 0));
        daysPane.setAlignment(Pos.CENTER_LEFT);
        // If you want them in one row without wrapping, set:
        // daysPane.setPrefWrapLength(9999);

        // Save button
        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #506EF9; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 14px; " +
                "-fx-padding: 10 20; -fx-border-radius: 5; -fx-background-radius: 5;");
        saveButton.setOnAction(e -> {
            System.out.println("Semester: " + semesterField.getText());
            System.out.println("Year: " + yearField.getText());
            StringBuilder days = new StringBuilder("Days: ");
            if (monCheck.isSelected()) days.append("Monday ");
            if (tueCheck.isSelected()) days.append("Tuesday ");
            if (wedCheck.isSelected()) days.append("Wednesday ");
            if (thuCheck.isSelected()) days.append("Thursday ");
            if (friCheck.isSelected()) days.append("Friday ");
            System.out.println(days);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Semester details saved!");
            alert.showAndWait();
        });

        // Add form controls
        formBox.getChildren().addAll(
                semesterLabel, semesterField,
                yearLabel, yearField,
                daysLabel, daysPane,
                saveButton
        );

        container.getChildren().addAll(heading, subHeading, formBox);

        // Put container in a ScrollPane for safety on smaller windows
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background:transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        return scrollPane;
    }

    /**
     * Creates a footer with placeholder text.
     */
    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setPadding(new Insets(10));
        footer.setAlignment(Pos.CENTER);
        footer.setStyle("-fx-background-color: transparent;");

        Label footerLabel = new Label("© 2025 Faculty's Office Hours Manager. All rights reserved.");
        footerLabel.setStyle("-fx-text-fill: #888; -fx-font-size: 12px;");

        footer.getChildren().add(footerLabel);
        return footer;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
