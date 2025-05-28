package main;

import dao.PetDAO;
import model.Pet;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class ChartViewer {
    private static JPanel mainPanel = new JPanel(new BorderLayout());

    public static JPanel getPetSpeciesChartPanel() {
        mainPanel.removeAll(); // Dọn dẹp nội dung cũ

        // Lấy dữ liệu mới nhất
        HashMap<String, Integer> countBySpecies = getLatestPetSpeciesData();

        // Tạo biểu đồ
        ChartPanel chartPanel = createChartPanel(countBySpecies);

        // Tạo menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuOptions = new JMenu("⚙️ Tùy chọn");
        JMenuItem showChart = new JMenuItem("📊 Xem biểu đồ");
        JMenuItem showReport = new JMenuItem("📄 Xem báo cáo");

        menuOptions.add(showChart);
        menuOptions.add(showReport);
        menuBar.add(menuOptions);

        mainPanel.add(menuBar, BorderLayout.NORTH);
        mainPanel.add(chartPanel, BorderLayout.CENTER);

        // Sự kiện: Cập nhật lại biểu đồ từ dữ liệu mới
        showChart.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            mainPanel.removeAll();
            HashMap<String, Integer> updatedData = getLatestPetSpeciesData(); // Lấy lại dữ liệu mới
            mainPanel.add(menuBar, BorderLayout.NORTH);
            mainPanel.add(createChartPanel(updatedData), BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        }));

        // Sự kiện: Hiển thị báo cáo
        showReport.addActionListener(e -> {
            HashMap<String, Integer> updatedData = getLatestPetSpeciesData(); // Lấy lại dữ liệu mới
            showReportOnMainPanel(updatedData);
        });

        return mainPanel;
    }

    // Hàm lấy dữ liệu từ database
    private static HashMap<String, Integer> getLatestPetSpeciesData() {
        PetDAO dao = new PetDAO();
        List<Pet> pets = dao.getAllPets();
        HashMap<String, Integer> countBySpecies = new HashMap<>();

        for (Pet pet : pets) {
            String species = pet.getSpecies();
            countBySpecies.put(species, countBySpecies.getOrDefault(species, 0) + 1);
        }

        return countBySpecies;
    }

    // Hàm tạo biểu đồ PieChart với label có phần trăm
    private static ChartPanel createChartPanel(HashMap<String, Integer> countBySpecies) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (String species : countBySpecies.keySet()) {
            dataset.setValue(species, countBySpecies.get(species));
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Tỷ lệ thú cưng theo loài",
                dataset,
                true,  // legend
                true,  // tooltips
                false  // urls
        );

        PiePlot plot = (PiePlot) chart.getPlot();

        // Hiển thị label theo định dạng: Tên - số lượng - phần trăm
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0.00%"));
        plot.setLabelGenerator(labelGenerator);

        return new ChartPanel(chart);
    }

    // Hàm hiển thị báo cáo dạng văn bản trên mainPanel
    private static void showReportOnMainPanel(HashMap<String, Integer> countBySpecies) {
        mainPanel.removeAll();

        StringBuilder reportText = new StringBuilder("<html><h3>📄 Báo cáo số lượng thú cưng theo loài:</h3><ul>");
        for (String species : countBySpecies.keySet()) {
            reportText.append("<li>").append(species).append(": <b>")
                    .append(countBySpecies.get(species)).append("</b> thú cưng</li>");
        }
        reportText.append("</ul></html>");

        JLabel lblReport = new JLabel(reportText.toString(), SwingConstants.CENTER);
        mainPanel.add(lblReport, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
