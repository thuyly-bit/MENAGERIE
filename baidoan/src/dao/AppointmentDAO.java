package dao;

import model.Appointment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // Lấy toàn bộ danh sách lịch hẹn
    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointment";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Appointment(
                        rs.getInt("id"),
                        rs.getString("petName"),       // tên cột đúng với DB
                        null,                          // nếu không có cột owner
                        rs.getDate("date"),
                        null,                          // nếu không có cột time
                        rs.getString("reason")
                ));
            }

        } catch (Exception e) {
            System.out.println("❌ Lỗi khi truy vấn lịch hẹn: " + e.getMessage());
        }

        return list;
    }

    // Thêm lịch hẹn mới
    public boolean addAppointment(Appointment ap) {
        String sql = "INSERT INTO appointment (petName, date, reason) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ap.getPetName());
            ps.setDate(2, ap.getDate());
            ps.setString(3, ap.getReason());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi thêm lịch hẹn: " + e.getMessage());
            return false;
        }
    }
}
