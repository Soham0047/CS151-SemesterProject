package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeSlotController {
    @FXML
    private ComboBox<String> fromHour;
    @FXML
    private ComboBox<String> toHour;
    @FXML
    public void initialize() {
        for(int hour = 0; hour < 24; hour++) {
            for(int min = 0; min < 60; min += 15) {
                //Converting to 12HR format
                LocalTime time = LocalTime.of(hour, min);
                String display = time.format(DateTimeFormatter.ofPattern("h:mm a"));
                fromHour.getItems().add(display);
                toHour.getItems().add(display);
            }
        }
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
        }catch (IOException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error saving time slot");
            alert.showAndWait();
        }
    }
}
