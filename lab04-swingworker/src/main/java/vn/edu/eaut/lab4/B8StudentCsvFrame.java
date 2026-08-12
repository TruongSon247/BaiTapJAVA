package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class B8StudentCsvFrame extends JFrame {
    private JButton btnChoose;
    private JButton btnLoad;
    private JLabel lblFile;
    private JLabel lblStatistic;
    private JTable table;
    private DefaultTableModel tableModel;
    private File selectedFile;

    public B8StudentCsvFrame() {
        setTitle("Bài 8 - Đọc CSV điểm sinh viên");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        btnChoose = new JButton("Chọn file CSV");
        btnLoad = new JButton("Đọc dữ liệu");
        lblFile = new JLabel("Chưa chọn file");
        lblStatistic = new JLabel("Thống kê: ");

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(btnChoose);
        topPanel.add(btnLoad);

        JPanel headerPanel = new JPanel(new GridLayout(3, 1));
        headerPanel.add(topPanel);
        headerPanel.add(lblFile);
        headerPanel.add(lblStatistic);

        tableModel = new DefaultTableModel(new Object[]{"Mã SV", "Họ tên", "Điểm"}, 0);
        table = new JTable(tableModel);

        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnChoose.addActionListener(e -> chooseFile());
        btnLoad.addActionListener(e -> loadCsv());
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            lblFile.setText("File: " + selectedFile.getAbsolutePath());
        }
    }

    private void loadCsv() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn file trước");
            return;
        }

        btnLoad.setEnabled(false);
        lblStatistic.setText("Đang đọc dữ liệu...");

        SwingWorker<List<b8Student>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<b8Student> doInBackground() throws Exception {
                List<b8Student> list = new ArrayList<>();
                try (BufferedReader reader = Files.newBufferedReader(
                        selectedFile.toPath(), StandardCharsets.UTF_8)) {
                    String line = reader.readLine(); // bỏ dòng tiêu đề
                    while ((line = reader.readLine()) != null) {
                        if (line.trim().isEmpty()) continue;
                        String[] parts = line.split(",");
                        if (parts.length < 3) continue;
                        String id = parts[0].trim();
                        String name = parts[1].trim();
                        double diem = Double.parseDouble(parts[2].trim());
                        list.add(new b8Student(id, name, diem));
                    }
                }
                return list;
            }

            @Override
            protected void done() {
                try {
                    List<b8Student> list = get();
                    tableModel.setRowCount(0);
                    double tong = 0;
                    b8Student max = null;
                    for (b8Student sv : list) {
                        tableModel.addRow(new Object[]{sv.getStudentId(), sv.getFullName(), sv.getDiem()});
                        tong += sv.getDiem();
                        if (max == null || sv.getDiem() > max.getDiem()) {
                            max = sv;
                        }
                    }
                    double trungBinh = list.isEmpty() ? 0 : tong / list.size();
                    String caoNhat = max == null ? "" : max.getFullName() + " (" + max.getDiem() + ")";
                    lblStatistic.setText(String.format(
                            "Thống kê: %d sinh viên - Điểm TB: %.2f - Cao nhất: %s",
                            list.size(), trungBinh, caoNhat));
                } catch (Exception ex) {
                    lblStatistic.setText("Lỗi khi đọc file CSV");
                }
                btnLoad.setEnabled(true);
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new B8StudentCsvFrame().setVisible(true));
    }
}