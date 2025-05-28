package dao;

import model.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EventDAO {

    // Lấy danh sách tất cả sự kiện
    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        String sql = "SELECT name, DATE_FORMAT(date, '%Y-%m-%d') AS date, type, remark FROM event";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                String date = rs.getString("date");  // Chuỗi yyyy-MM-dd
                String type = rs.getString("type");
                String remark = rs.getString("remark");

                eventList.add(new Event(name, date, type, remark));
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi lấy danh sách sự kiện: " + e.getMessage());
        }

        return eventList;
    }

    // Thêm mới sự kiện
    public boolean insertEvent(Event event) {
        String sql = "INSERT INTO event (name, date, type, remark) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDate()); // yyyy-MM-dd
            stmt.setString(3, event.getType());
            stmt.setString(4, event.getRemark());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi thêm sự kiện: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật sự kiện theo tên và ngày (giả sử 2 trường này làm khóa chính)
    public boolean updateEvent(Event event) {
        String sql = "UPDATE event SET type = ?, remark = ? WHERE name = ? AND date = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, event.getType());
            stmt.setString(2, event.getRemark());
            stmt.setString(3, event.getName());
            stmt.setString(4, event.getDate());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi cập nhật sự kiện: " + e.getMessage());
            return false;
        }
    }

    // Xóa sự kiện theo tên và ngày
    public boolean deleteEvent(String name, String date) {
        String sql = "DELETE FROM event WHERE name = ? AND date = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, date);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xóa sự kiện: " + e.getMessage());
            return false;
        }
    }
}
