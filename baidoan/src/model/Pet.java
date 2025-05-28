package model;

public class Pet {
    private String name;
    private String owner;
    private String species;
    private String sex;
    private java.sql.Date birth;
    private java.sql.Date death;

    // Constructor
    public Pet(String name, String owner, String species, String sex, java.sql.Date birth, java.sql.Date death) {
        
        this.name = name;
        this.owner = owner;
        this.species = species;
        this.sex = sex;
        this.birth = birth;
        this.death = death;
    }

 // Constructor không tham số (nếu cần)
    public Pet() {}

    // Getters
    public String getName() { return name; }
    public String getOwner() { return owner; }
    public String getSpecies() { return species; }
    public String getSex() { return sex; }
    public java.sql.Date getBirth() { return birth; }
    public java.sql.Date getDeath() { return death; }

    // Setters (nếu muốn cho phép thay đổi)
    public void setName(String name) { this.name = name; }
    public void setOwner(String owner) { this.owner = owner; }
    public void setSpecies(String species) { this.species = species; }
    public void setSex(String sex) { this.sex = sex; }
    public void setBirth(java.sql.Date birth) { this.birth = birth; }
    public void setDeath(java.sql.Date death) { this.death = death; }

    @Override
    public String toString() {
        return "Pet [name=" + name + ", owner=" + owner + ", species=" + species +
               ", sex=" + sex + ", birth=" + birth + ", death=" + death + "]";
    }
}