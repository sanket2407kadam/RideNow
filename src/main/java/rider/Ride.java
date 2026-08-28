package rider;

import java.sql.Timestamp;

public class Ride {
    private int rideId;
    private String fromLocation;
    private String toLocation;
    private Timestamp rideDate;
    private double fare;
    private String rideStatus;

    public Ride(int rideId, String fromLocation, String toLocation, Timestamp rideDate, double fare, String rideStatus) {
        this.rideId = rideId;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.rideDate = rideDate;
        this.fare = fare;
        this.rideStatus = rideStatus;
    }

    public int getRideId() { return rideId; }
    public String getFromLocation() { return fromLocation; }
    public String getToLocation() { return toLocation; }
    public Timestamp getRideDate() { return rideDate; }
    public double getFare() { return fare; }
    public String getRideStatus() { return rideStatus; }
}
