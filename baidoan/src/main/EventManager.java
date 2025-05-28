package main;
import dao.EventDAO;
import model.Event;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EventManager {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Danh sách sự kiện");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Định nghĩa các cột cho bảng
        String[] columns = {"Tên", "Ngày", "Loại", "Ghi chú"};
        JTable table = new JTable(new DefaultTableModel(new Object[0][0], columns));
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Nút "Làm mới" để tải danh sách sự kiện
        JButton btnRefresh = new JButton("🔄 Làm mới");
        frame.add(btnRefresh, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> {
            EventDAO dao = new EventDAO();
            List<Event> events = dao.getAllEvents();
            String[][] rows = new String[events.size()][4];

            // Gán dữ liệu từ danh sách sự kiện vào mảng
            for (int i = 0; i < events.size(); i++) {
                Event ev = events.get(i);
                rows[i][0] = ev.getName();
                rows[i][1] = ev.getDate();
                rows[i][2] = ev.getType();
                rows[i][3] = ev.getRemark();
            }

            table.setModel(new DefaultTableModel(rows, columns));
        });

        btnRefresh.doClick();  // Tự động tải danh sách khi khởi động
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
