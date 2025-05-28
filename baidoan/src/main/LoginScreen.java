package main;

import dao.AppointmentDAO;
import dao.EventDAO;
import dao.PetDAO;
import dao.UserDAO;
import dao.VaccinationDAO;
import model.Event;
import model.Pet;
import model.User;
import model.Appointment;
import model.Vaccination;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;

public class LoginScreen {
    private static JFrame frame;
    private static JPanel panelLogin = new JPanel(new GridBagLayout());
    private static JPanel panelEvent = new JPanel(new BorderLayout());
    private static JPanel panelPet = new JPanel(new BorderLayout());
    private static JPanel panelAppointment = new JPanel(new BorderLayout());
    private static JPanel panelVaccination = new JPanel(new BorderLayout());
    private static JPanel panelMain = new JPanel(new BorderLayout());

    public static void main(String[] args) {
        frame = new JFrame("\uD83D\uDD10 Đăng nhập hệ thống");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        setupLoginPanel();
        frame.setVisible(true);
    }

    private static void setupLoginPanel() {
        panelLogin.removeAll();
        panelLogin.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelLogin.setBackground(new Color(173, 216, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        panelLogin.setLayout(new GridBagLayout());

        JLabel lblTitle = new JLabel("MENAGERIE", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 0;
        panelLogin.add(lblTitle, gbc);

        JLabel lblUser = new JLabel("\uD83D\uDC64 Tên đăng nhập:");
        gbc.gridy++;
        panelLogin.add(lblUser, gbc);

        JTextField txtUsername = new JTextField(15);
        gbc.gridy++;
        panelLogin.add(txtUsername, gbc);

        JLabel lblPass = new JLabel("\uD83D\uDD11 Mật khẩu:");
        gbc.gridy++;
        panelLogin.add(lblPass, gbc);

        JPasswordField txtPassword = new JPasswordField(15);
        gbc.gridy++;
        panelLogin.add(txtPassword, gbc);

        JButton btnLogin = new JButton("\uD83D\uDD10 Đăng nhập");
        JButton btnRegister = new JButton("\uD83D\uDCDD Đăng ký");
        gbc.gridy++;
        panelLogin.add(btnLogin, gbc);
        gbc.gridy++;
        panelLogin.add(btnRegister, gbc);

        frame.setContentPane(panelLogin);
        frame.revalidate();

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            UserDAO userDAO = new UserDAO();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "❗ Vui lòng nhập đầy đủ tài khoản và mật khẩu!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            } else if (userDAO.login(username, password)) {
                showMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "❌ Sai tài khoản hoặc mật khẩu!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRegister.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            UserDAO userDAO = new UserDAO();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "⚠️ Vui lòng nhập đầy đủ thông tin để đăng ký!");
            } else if (userDAO.register(new User(username, password))) {
                JOptionPane.showMessageDialog(frame, "🟢 Đăng ký thành công! Bạn có thể đăng nhập.");
            } else {
                JOptionPane.showMessageDialog(frame, "⚠️ Tên người dùng đã tồn tại!");
            }
        });
    }

    private static void showMenu() {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.revalidate();
        frame.setTitle("\uD83C\uDFAF Quản lý hệ thống");
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("\uD83D\uDD0D Quản lý chính");

        JMenuItem manageEvents = new JMenuItem("\uD83D\uDEA0 Quản lý sự kiện");
        JMenuItem managePets = new JMenuItem("\uD83D\uDC36 Quản lý thú cưng");
        JMenuItem manageAppointments = new JMenuItem("\uD83D\uDCC5 Quản lý lịch hẹn");
        JMenuItem manageVaccinations = new JMenuItem("\uD83D\uDC89 Quản lý tiêm chủng");
        JMenuItem visualizePets = new JMenuItem("\uD83D\uDCC8 Trực quan thú cưng");
        JMenuItem exit = new JMenuItem("\uD83D\uDEAA Đăng xuất");

        menuFile.add(manageEvents);
        menuFile.add(managePets);
        menuFile.add(manageAppointments);
        menuFile.add(manageVaccinations);
        menuFile.add(visualizePets);
        menuFile.add(exit);

        menuBar.add(menuFile);
        frame.setJMenuBar(menuBar);

        panelMain.removeAll();
        panelMain.setBackground(new Color(230, 245, 255));
        panelMain.add(new JLabel("\uD83C\uDFAF Chào mừng đến hệ thống quản lý!", SwingConstants.CENTER), BorderLayout.CENTER);
        frame.add(panelMain, BorderLayout.CENTER);
        frame.repaint();
        frame.revalidate();

        manageEvents.addActionListener(e -> {
            setupEventPanel();
            frame.setContentPane(panelEvent);
            frame.revalidate();
        });

        managePets.addActionListener(e -> {
            setupPetPanel();
            frame.setContentPane(panelPet);
            frame.revalidate();
        });

        manageAppointments.addActionListener(e -> {
            setupAppointmentPanel();
            frame.setContentPane(panelAppointment);
            frame.revalidate();
        });

        manageVaccinations.addActionListener(e -> {
            setupVaccinationPanel();
            frame.setContentPane(panelVaccination);
            frame.revalidate();
        });

        visualizePets.addActionListener(e -> {
            panelMain.removeAll();
            panelMain.setLayout(new BorderLayout());
            panelMain.setBackground(new Color(230, 245, 255));
            panelMain.add(ChartViewer.getPetSpeciesChartPanel(), BorderLayout.CENTER);
            frame.setContentPane(panelMain);
            frame.revalidate();
        });

        exit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Bạn có chắc muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                setupLoginPanel();
            }
        });
    }

    private static void setupEventPanel() {
        panelEvent.removeAll();
        panelEvent.setBackground(new Color(230, 245, 255));

        String[] columns = {"Tên", "Ngày", "Loại", "Ghi chú"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columns);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panelEvent.add(scrollPane, BorderLayout.CENTER);

        JPanel panelButtons = new JPanel();
        JButton btnAdd = new JButton("➕ Thêm");
        JButton btnDelete = new JButton("❌ Xóa");
        JButton btnRefresh = new JButton("🔄 Làm mới");
        JButton btnBack = new JButton("🔙 Trở về");
        JButton btnSortAsc = new JButton("🔼 Sắp xếp tăng dần");

        panelButtons.add(btnAdd);
        panelButtons.add(btnDelete);
        panelButtons.add(btnRefresh);
        panelButtons.add(btnBack);
        panelButtons.add(btnSortAsc);
        panelEvent.add(panelButtons, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadEventData(model));
        btnRefresh.doClick();

        btnBack.addActionListener(e -> showMenu());

        btnAdd.addActionListener(e -> {
            model.addRow(new Object[]{"Sự kiện mới", new Date(System.currentTimeMillis()), "", ""});
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Bạn có chắc muốn xóa sự kiện này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(row);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Vui lòng chọn một sự kiện để xóa!");
            }
        });

        btnSortAsc.addActionListener(e -> {
            EventDAO dao = new EventDAO();
            ArrayList<Event> events = new ArrayList<>(dao.getAllEvents());

            // Bubble Sort theo ngày
            for (int i = 0; i < events.size() - 1; i++) {
                for (int j = 0; j < events.size() - i - 1; j++) {
                    if (events.get(j).getDate().compareTo(events.get(j + 1).getDate()) > 0) {
                        Event temp = events.get(j);
                        events.set(j, events.get(j + 1));
                        events.set(j + 1, temp);
                    }
                }
            }

            
            model.setRowCount(0);
            for (Event ev : events) {
                model.addRow(new Object[]{ev.getName(), ev.getDate(), ev.getType(), ev.getRemark()});
            }
        });

    }

    private static void loadEventData(DefaultTableModel model) {
        EventDAO dao = new EventDAO();
        ArrayList<Event> events = new ArrayList<>(dao.getAllEvents());

        events.sort((e1, e2) -> e2.getDate().compareTo(e1.getDate()));
        model.setRowCount(0);
        for (Event ev : events) {
            model.addRow(new Object[]{ev.getName(), ev.getDate(), ev.getType(), ev.getRemark()});
        }
    }

    private static void setupPetPanel() {
        panelPet.removeAll();
        panelPet.setBackground(new Color(230, 245, 255));

        String[] columns = {"Tên thú cưng", "Chủ", "Loài", "Giới tính", "Ngày sinh", "Ngày mất"};

        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columns);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panelPet.add(scrollPane, BorderLayout.CENTER);

        JPanel panelButtons = new JPanel();
        JButton btnAdd = new JButton("➕ Thêm");
        JButton btnDelete = new JButton("❌ Xóa");
        JButton btnRefresh = new JButton("🔄 Làm mới");
        JButton btnBack = new JButton("🔙 Trở về");
        JButton btnSortAsc = new JButton("🔼 Sắp xếp tăng dần");
        JButton btnUndoSort = new JButton("↩️ Hoàn tác sắp xếp");
        panelButtons.add(btnAdd);
        panelButtons.add(btnDelete);
        panelButtons.add(btnRefresh);
        panelButtons.add(btnBack);
        panelButtons.add(btnSortAsc);
        panelButtons.add(btnUndoSort);
        panelPet.add(panelButtons, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadPetData(model));
        btnRefresh.doClick();

        btnBack.addActionListener(e -> showMenu());

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Bạn có chắc muốn xóa thú cưng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(row);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Vui lòng chọn thú cưng để xóa!");
            }
        });

        btnAdd.addActionListener(e -> {
            model.addRow(new Object[]{"Tên mới", "Loài", new Date(System.currentTimeMillis()), "Chủ"});
        });
    }

    private static void loadPetData(DefaultTableModel model) {
        PetDAO dao = new PetDAO();
        ArrayList<Pet> pets = new ArrayList<>(dao.getAllPets());
        model.setRowCount(0);
        for (Pet pet : pets) {
        	model.addRow(new Object[]{pet.getName(), pet.getOwner(), pet.getSpecies(), pet.getSex(), pet.getBirth(), pet.getDeath()});

        }
    }

    //
    private static void setupAppointmentPanel() {
        panelAppointment.removeAll();
        panelAppointment.setBackground(new Color(230, 245, 255));

        String[] columns = {"ID", "Tên thú cưng", "Ngày hẹn", "Ghi chú"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columns);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panelAppointment.add(scrollPane, BorderLayout.CENTER);

        JPanel panelButtons = new JPanel();
        JButton btnAdd = new JButton("➕ Thêm");
        JButton btnDelete = new JButton("❌ Xóa");
        JButton btnRefresh = new JButton("🔄 Làm mới");
        JButton btnBack = new JButton("🔙 Trở về");
        JButton btnSortAsc = new JButton("🔼 Sắp xếp tăng dần");
        JButton btnUndoSort = new JButton("↩️ Hoàn tác sắp xếp");
       
        panelButtons.add(btnSortAsc);
        panelButtons.add(btnUndoSort);
        panelButtons.add(btnAdd);
        panelButtons.add(btnDelete);
        panelButtons.add(btnRefresh);
        panelButtons.add(btnBack);
        panelAppointment.add(panelButtons, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadAppointmentData(model));
        btnRefresh.doClick();

        btnBack.addActionListener(e -> showMenu());

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Bạn có chắc muốn xóa lịch hẹn này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(row);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Vui lòng chọn lịch hẹn để xóa!");
            }
        });

        btnAdd.addActionListener(e -> {
            model.addRow(new Object[]{null, "Tên thú cưng", new Date(System.currentTimeMillis()), "Ghi chú"});
        });
    }

    private static void loadAppointmentData(DefaultTableModel model) {
        AppointmentDAO dao = new AppointmentDAO();
        ArrayList<Appointment> list = new ArrayList<>(dao.getAllAppointments());
        model.setRowCount(0);
        for (Appointment ap : list) {
            model.addRow(new Object[]{ap.getId(), ap.getPetName(), ap.getDate(), ap.getReason()});
        }
    }

    
    private static void setupVaccinationPanel() {
        panelVaccination.removeAll();
        panelVaccination.setBackground(new Color(230, 245, 255));

        String[] columns = {"ID", "Tên thú cưng", "Tên vacxin", "Ngày tiêm", "Ghi chú"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columns);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panelVaccination.add(scrollPane, BorderLayout.CENTER);

        JPanel panelButtons = new JPanel();
        JButton btnAdd = new JButton("➕ Thêm");
        JButton btnDelete = new JButton("❌ Xóa");
        JButton btnRefresh = new JButton("🔄 Làm mới");
        JButton btnBack = new JButton("🔙 Trở về");
        JButton btnSortAsc = new JButton("🔼 Sắp xếp tăng dần");
        JButton btnUndoSort = new JButton("↩️ Hoàn tác sắp xếp");
       
        panelButtons.add(btnSortAsc);
        panelButtons.add(btnUndoSort);
        panelButtons.add(btnAdd);
        panelButtons.add(btnDelete);
        panelButtons.add(btnRefresh);
        panelButtons.add(btnBack);
        panelVaccination.add(panelButtons, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadVaccinationData(model));
        btnRefresh.doClick();

        btnBack.addActionListener(e -> showMenu());

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Bạn có chắc muốn xóa bản ghi tiêm chủng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(row);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Vui lòng chọn bản ghi tiêm chủng để xóa!");
            }
        });

        btnAdd.addActionListener(e -> {
            model.addRow(new Object[]{null, "Tên thú cưng", "Tên vacxin", new Date(System.currentTimeMillis()), "Ghi chú"});
        });
    }

    private static void loadVaccinationData(DefaultTableModel model) {
        VaccinationDAO dao = new VaccinationDAO();
        ArrayList<Vaccination> list = new ArrayList<>(dao.getAllVaccinations());
        model.setRowCount(0);
        for (Vaccination v : list) {
            model.addRow(new Object[]{v.getId(), v.getPetName(), v.getVaccineName(), v.getDate(), v.getNotes()});
        }
    }
}
