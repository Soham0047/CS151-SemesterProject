package s25.cs151.model;

import javafx.beans.property.SimpleStringProperty;

public class SemesterEntry {
    private final SimpleStringProperty semester;
    private final SimpleStringProperty year;
    private final SimpleStringProperty days;

    //Constructor
    public SemesterEntry(String semester, String year, String days) {
        this.semester = new SimpleStringProperty(semester);
        this.year = new SimpleStringProperty(year);
        this.days = new SimpleStringProperty(days);
    }

    //Getters
    public String getSemester() { return semester.get(); }
    public String getYear() { return year.get(); }
    public String getDays() { return days.get(); }
}
