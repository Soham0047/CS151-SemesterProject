package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

//Reports.fxml controller
public class ReportsController {

    //Buttons
    @FXML
    private Button btnHome;
    @FXML
    private Button btnSemester;
    @FXML
    private Button btnAppointment;
    @FXML
    private Button btnSearch;

    @FXML private TableView<SemesterEntry> reportsTable;
    @FXML private TableColumn<SemesterEntry, String> yearCol;
    @FXML private TableColumn<SemesterEntry, String> daysCol;
    @FXML private TableColumn<SemesterEntry, String> semesterCol;

    @FXML
    public void initialize() {
        //Navigation
        btnHome.setOnAction(e -> Main.setRoot("HomePage.fxml"));
        btnSemester.setOnAction(e -> Main.setRoot("DefineSemester.fxml"));

        //Link table columns to SemesterEntry properties
        semesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        daysCol.setCellValueFactory(new PropertyValueFactory<>("days"));

        //Make year sort numerically
        yearCol.setComparator((y1, y2) -> {
            //Convert strings into integers
            int i1 = Integer.parseInt(y1);
            int i2 = Integer.parseInt(y2);

            return Integer.compare(i1, i2);
        });

        //Setting the desired sort type
        yearCol.setSortType(TableColumn.SortType.DESCENDING);
        semesterCol.setSortType(TableColumn.SortType.DESCENDING);

        //Load data from file
        ObservableList<SemesterEntry> dataList = FXCollections.observableArrayList();
        try{
            List<String> lines = Files.readAllLines(Paths.get("semester_data.txt"));
            for(String line : lines){
                String[] parts = line.split(",", 3);
                if(parts.length == 3){
                    String semester = parts[0]; //"Spring"
                    String year = parts[1]; //"2025"
                    String days = parts[2]; //"Monday Wednesday"

                    SemesterEntry entry = new SemesterEntry(semester, year, days);
                    dataList.add(entry);
                }
            }
        } catch (IOException e) {
            System.out.println("No data file or error reading file: " + e.getMessage());
        }

        //Put data into the table
        reportsTable.setItems(dataList);

        //Add columns to the sort order
        reportsTable.getSortOrder().addAll(yearCol, semesterCol);

        //Apply the sort immediately
        reportsTable.sort();

    }

}
