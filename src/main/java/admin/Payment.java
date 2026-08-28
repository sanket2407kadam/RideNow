package admin;

import java.sql.Timestamp;

public class Payment {
    private int paymentId;
    private int rideId;
    private String rider;
    private String driver;
    private double amount;
    private String method;
    private String status;
    private Timestamp time;

    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }

    public int getRideId() { return rideId; }
    public void setRideId(int rideId) { this.rideId = rideId; }

    public String getRider() { return rider; }
    public void setRider(String rider) { this.rider = rider; }

    public String getDriver() { return driver; }
    public void setDriver(String driver) { this.driver = driver; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getTime() { return time; }
    public void setTime(Timestamp time) { this.time = time; }
}
