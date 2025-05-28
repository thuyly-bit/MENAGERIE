package model;

public class Vaccination {
    private int id;
    private String petName;
    private String vaccineName;
    private java.sql.Date date;
    private String vet;
    private String notes;

    public Vaccination(int id, String petName, String vaccineName, java.sql.Date date, String vet, String notes) {
        this.id = id;
        this.petName = petName;
        this.vaccineName = vaccineName;
        this.date = date;
        this.vet = vet;
        this.notes = notes;
    }

    // Getters
    public int getId() { return id; }
    public String getPetName() { return petName; }
    public String getVaccineName() { return vaccineName; }
    public java.sql.Date getDate() { return date; }
    public String getVet() { return vet; }
    public String getNotes() { return notes; }
}
