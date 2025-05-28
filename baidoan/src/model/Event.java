package model;

public class Event {
    private String name;
    private String date;  // 🔹 Đổi từ Date sang String
    private String type;
    private String remark;

    public Event(String name, String date, String type, String remark) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.remark = remark;
    }

    public String getName() { return name; }
    public String getDate() { return date; }
    public String getType() { return type; }
    public String getRemark() { return remark; }
}
