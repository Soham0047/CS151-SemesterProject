package s25.cs151.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.model.TimeSlotEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TimeSlotController {
    @FXML private ComboBox<String> fromHour;
    @FXML private ComboBox<String> toHour;
    @FXML private TableView<TimeSlotEntry> timeSlotTable;
    @FXML private TableColumn<TimeSlotEntry, String> fromCol;
    @FXML private TableColumn<TimeSlotEntry, String> toCol;

    @FXML
    public void initialize() {
        //Generate time of 15min intervals for combobox
        for(int hour = 0; hour < 24; hour++) {
            for(int min = 0; min < 60; min += 15) {
                //Converting to 12HR format
                LocalTime time = LocalTime.of(hour, min);
                String display = time.format(DateTimeFormatter.ofPattern("h:mm a"));
                fromHour.getItems().add(display);
                toHour.getItems().add(display);
            }
        }

        //Load Timeslot Table
        fromCol.setCellValueFactory(new PropertyValueFactory<TimeSlotEntry, String>("fromTime"));
        toCol.setCellValueFactory(new PropertyValueFactory<>("toTime"));

        //Load timeslot data from file into the table
        loadTimeSlots();
    }
    @FXML
    private void handleSave() {
        String fromTime = fromHour.getValue();
        String toTime = toHour.getValue();

        //Checks if both times are selected
        if (fromTime == null || toTime == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select both times");
            alert.showAndWait();
            return;
        }

        //Defines the formatter that matches ComboBox values
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");

        //Parse the strings into LocalTime
        LocalTime from = LocalTime.parse(fromTime, formatter);
        LocalTime to = LocalTime.parse(toTime, formatter);

        //Error if 'from' is after or equals 'to'
        if (from.isAfter(to) || from.equals(to)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "'From' time must be after 'to' time");
            alert.showAndWait();
            return;
        }

        //Format the data as "9:00 AM,5:00 PM"
        String data = fromTime + "," + toTime;

        try{
            //Append new timeslot to file
            Files.write(Paths.get("timeslots.txt"),
                    (data+ System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Saved");
            alert.showAndWait();

            //Refresh table
            loadTimeSlots();
        }catch (IOException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error saving time slot");
            alert.showAndWait();
        }
    }

    //Generate the timeSlots
    private void loadTimeSlots() {
        ObservableList<TimeSlotEntry> data = FXCollections.observableArrayList();
        try {
            List<String> lines = Files.readAllLines(Paths.get("timeslots.txt"));
            for(String line : lines){
                String[] parts = line.split(",", 2);
                if (parts.length == 2){
                    //Remove extra spaces
                    String from = parts[0].trim();
                    String to = parts[1].trim();
                    data.add(new TimeSlotEntry(from, to));
                }
            }
        } catch (IOException e) {
            System.out.println("No timeslot data file or error reading file: " + e.getMessage());
        }
        timeSlotTable.setItems(data);

        //Custom comparator for sorting
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        fromCol.setComparator((s1, s2) -> {
            LocalTime t1 = LocalTime.parse(s1, formatter);
            LocalTime t2 = LocalTime.parse(s2, formatter);
            return t1.compareTo(t2);
        });

        //Sort the table in ascending order
        timeSlotTable.getSortOrder().clear();
        timeSlotTable.getSortOrder().add(fromCol);
        fromCol.setSortType(TableColumn.SortType.ASCENDING);
        timeSlotTable.sort();
    }
}
