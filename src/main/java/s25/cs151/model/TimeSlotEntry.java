package s25.cs151.model;

public class TimeSlotEntry {
    private String fromTime;
    private String toTime;

    public TimeSlotEntry(String fromTime, String toTime) {
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public String getFromTime() { return fromTime; }
    public String getToTime()   { return toTime; }
}