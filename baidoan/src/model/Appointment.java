package model;

public class Appointment {
    private int id;
    private String petName;
    private String owner;
    private java.sql.Date date;
    private java.sql.Time time;
    private String reason;

    public Appointment(int id, String petName, String owner, java.sql.Date date, java.sql.Time time, String reason) {
        this.id = id;
        this.petName = petName;
        this.owner = owner;
        this.date = date;
        this.time = time;
        this.reason = reason;
    }

    // Getters
    public int getId() { return id; }
    public String getPetName() { return petName; }
    public String getOwner() { return owner; }
    public java.sql.Date getDate() { return date; }
    public java.sql.Time getTime() { return time; }
    public String getReason() { return reason; }
}
