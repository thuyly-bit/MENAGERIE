package dao;

import model.Vaccination;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VaccinationDAO {

    // Lấy tất cả các bản ghi tiêm chủng
    public List<Vaccination> getAllVaccinations() {
        List<Vaccination> list = new ArrayList<>();
        String sql = "SELECT * FROM vaccination";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Vaccination(
                        rs.getInt("id"),
                        rs.getString("pet_name"),
                        rs.getString("vaccine_name"),
                        rs.getDate("date"),
                        rs.getString("vet"),
                        rs.getString("notes")
                ));
            }
        } catch (Exception e) {
            System.out.println("❌ Lỗi lấy tiêm chủng: " + e.getMessage());
        }

        return list;
    }

    // Thêm bản ghi tiêm chủng mới
    public boolean addVaccination(Vaccination v) {
        String sql = "INSERT INTO vaccination (pet_name, vaccine_name, date, vet, notes) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getPetName());
            ps.setString(2, v.getVaccineName());
            ps.setDate(3, v.getDate());
            ps.setString(4, v.getVet());
            ps.setString(5, v.getNotes());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi thêm tiêm chủng: " + e.getMessage());
            return false;
        }
    }
}
