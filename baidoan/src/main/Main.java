package main;

import dao.PetDAO;
import model.Pet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final ArrayList<Pet> petList = new ArrayList<>();
    private static ArrayList<Pet> displayedList = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Danh sách thú cưng");
        frame.setSize(700, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        String[] columns = { "Tên", "Chủ", "Loài", "Giới tính", "Ngày sinh", "Ngày chết"};
        JTable table = new JTable(new DefaultTableModel(new Object[0][0], columns));
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton btnRefresh = new JButton("🔄 Làm mới");
        JButton btnSortAsc = new JButton("🔼 Sắp xếp tăng dần");
        JButton btnUndoSort = new JButton("↩️ Hoàn tác sắp xếp");
        JButton btnAddPet = new JButton("➕ Thêm thú cưng");

        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnSortAsc);
        buttonPanel.add(btnUndoSort);
        buttonPanel.add(btnAddPet);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> {
            PetDAO dao = new PetDAO();
            List<Pet> pets = dao.getAllPets();

            petList.clear();
            petList.addAll(pets);

            displayedList = new ArrayList<>(petList);

            updateTable(table, displayedList, columns);
        });

        btnSortAsc.addActionListener(e -> {
            if (displayedList == null || displayedList.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Vui lòng làm mới danh sách trước khi sắp xếp.");
                return;
            }
            selectionSortByName(displayedList);
            updateTable(table, displayedList, columns);
        });

        btnUndoSort.addActionListener(e -> {
            if (petList == null || petList.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Chưa có danh sách gốc để hoàn tác.");
                return;
            }
            displayedList = new ArrayList<>(petList);
            updateTable(table, displayedList, columns);
        });

        btnAddPet.addActionListener(e -> {
            JTextField txtName = new JTextField();
            JTextField txtOwner = new JTextField();
            JTextField txtSpecies = new JTextField();
            JTextField txtSex = new JTextField();
            JTextField txtBirth = new JTextField();
            JTextField txtDeath = new JTextField();

            Object[] message = {
                "Tên:", txtName,
                "Chủ:", txtOwner,
                "Loài:", txtSpecies,
                "Giới tính:", txtSex,
                "Ngày sinh (yyyy-MM-dd):", txtBirth,
                "Ngày chết (yyyy-MM-dd, để trống nếu chưa có):", txtDeath
            };

            int option = JOptionPane.showConfirmDialog(frame, message, "Thêm thú cưng mới", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String name = txtName.getText().trim();
                    String owner = txtOwner.getText().trim();
                    String species = txtSpecies.getText().trim();
                    String sex = txtSex.getText().trim();

                    if(name.isEmpty() || owner.isEmpty() || species.isEmpty() || sex.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Vui lòng nhập đầy đủ thông tin bắt buộc!");
                        return;
                    }

                    java.sql.Date birth = java.sql.Date.valueOf(txtBirth.getText().trim());
                    java.sql.Date death = null;
                    if (!txtDeath.getText().trim().isEmpty()) {
                        death = java.sql.Date.valueOf(txtDeath.getText().trim());
                    }

                    Pet newPet = new Pet(name, owner, species, sex, birth, death);

                    PetDAO dao = new PetDAO();
                    boolean success = dao.addPet(newPet);

                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Thêm thú cưng thành công!");
                        btnRefresh.doClick();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Thêm thú cưng thất bại!");
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, "Ngày nhập không hợp lệ! Vui lòng nhập đúng định dạng yyyy-MM-dd");
                }
            }
        });

        btnRefresh.doClick();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void updateTable(JTable table, List<Pet> list, String[] columns) {
        String[][] rows = new String[list.size()][columns.length];
        for (int i = 0; i < list.size(); i++) {
            Pet p = list.get(i);
            rows[i][0] = p.getName();
            rows[i][1] = p.getOwner();
            rows[i][2] = p.getSpecies();
            rows[i][3] = p.getSex() != null ? p.getSex() : "";
            rows[i][4] = p.getBirth() != null ? p.getBirth().toString() : "";
            rows[i][5] = p.getDeath() != null ? p.getDeath().toString() : "";
        }
        table.setModel(new DefaultTableModel(rows, columns));
    }

    private static void selectionSortByName(List<Pet> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (list.get(j).getName().compareToIgnoreCase(list.get(minIdx).getName()) < 0) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                Pet temp = list.get(i);
                list.set(i, list.get(minIdx));
                list.set(minIdx, temp);
            }
        }
    }
}
