package dao;

import model.Pet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAO {

    // Lấy danh sách tất cả thú cưng
    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT name, owner, species, sex, birth, death FROM pet";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                String owner = rs.getString("owner");
                String species = rs.getString("species");
                String sex = rs.getString("sex");
                Date birth = rs.getDate("birth");
                Date death = rs.getDate("death");

                pets.add(new Pet(name, owner, species, sex, birth, death));
            }

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi truy xuất thú cưng: " + e.getMessage());
        }

        return pets;
    }

    // Thêm mới thú cưng
    public boolean addPet(Pet pet) {
        String sql = "INSERT INTO pet (name, owner, species, sex, birth, death) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pet.getName());
            ps.setString(2, pet.getOwner());
            ps.setString(3, pet.getSpecies());
            ps.setString(4, pet.getSex());
            ps.setDate(5, pet.getBirth());
            ps.setDate(6, pet.getDeath());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi thêm thú cưng: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật thú cưng theo tên cũ (có thể đổi tên)
    public boolean updatePetByOldName(String oldName, Pet pet) {
        String sql = "UPDATE pet SET name = ?, owner = ?, species = ?, sex = ?, birth = ?, death = ? WHERE name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pet.getName());
            ps.setString(2, pet.getOwner());
            ps.setString(3, pet.getSpecies());
            ps.setString(4, pet.getSex());
            ps.setDate(5, pet.getBirth());
            ps.setDate(6, pet.getDeath());
            ps.setString(7, oldName);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi cập nhật thú cưng: " + e.getMessage());
            return false;
        }
    }

    // Xóa thú cưng theo tên
    public boolean deletePetByName(String name) {
        String sql = "DELETE FROM pet WHERE name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            int affectedRows = ps.executeUpdate();
            System.out.println("🔥 Số dòng bị xóa: " + affectedRows);
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xóa thú cưng: " + e.getMessage());
            return false;
        }
    }
}
