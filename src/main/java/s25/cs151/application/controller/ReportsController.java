package s25.cs151.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportsController {
    @FXML private ComboBox<String> semesterSelector;
    @FXML private BarChart<String, Number> officeHoursBarChart;
    @FXML private PieChart courseDistributionPieChart;

    @FXML
    public void initialize() {
        // Initialize semester selector
        ObservableList<String> semesters = FXCollections.observableArrayList();
        try {
            List<String> semesterEntries = Files.readAllLines(Paths.get("semesters_data.txt"));
            for (String entry : semesterEntries) {
                String[] parts = entry.split(",");
                if (parts.length >= 2) {
                    semesters.add(parts[0] + " " + parts[1]);
                }
            }
        } catch (IOException e) {
            // Handle case where file doesn't exist
            semesters.add("Current Semester");
        }
        
        semesterSelector.setItems(semesters);
        semesterSelector.getSelectionModel().selectFirst();
        
        // Set up listeners for semester changes
        semesterSelector.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateCharts(newVal);
            }
        });
        
        // Initial chart update
        if (!semesters.isEmpty()) {
            updateCharts(semesterSelector.getValue());
        }
    }
    
    private void updateCharts(String semester) {
        updateOfficeHoursBarChart(semester);
        updateCourseDistributionPieChart(semester);
    }
    
    private void updateOfficeHoursBarChart(String semester) {
        // Clear existing data
        officeHoursBarChart.getData().clear();
        
        // In a real app, this would load actual data for the selected semester
        // For this example, we'll use sample data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Hours per Day");
        
        series.getData().add(new XYChart.Data<>("Monday", 2));
        series.getData().add(new XYChart.Data<>("Tuesday", 3));
        series.getData().add(new XYChart.Data<>("Wednesday", 1.5));
        series.getData().add(new XYChart.Data<>("Thursday", 4));
        series.getData().add(new XYChart.Data<>("Friday", 1));
        
        officeHoursBarChart.getData().add(series);
    }
    
    private void updateCourseDistributionPieChart(String semester) {
        // Clear existing data
        courseDistributionPieChart.getData().clear();
        
        // In a real app, this would load actual course data for the selected semester
        // For this example, we'll use sample data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("CS 151", 5),
                new PieChart.Data("CS 146", 3),
                new PieChart.Data("CS 149", 2),
                new PieChart.Data("CS 166", 4)
        );
        
        courseDistributionPieChart.setData(pieChartData);
    }
} 