package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class B9ProductLoadFrame extends JFrame {
    private JButton btnLoad;
    private JProgressBar progressBar;
    private JLabel lblStatus;
    private JTable table;
    private DefaultTableModel tableModel;

    public B9ProductLoadFrame() {
        setTitle("Bài 9 - Mô phỏng tải danh sách sản phẩm");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        btnLoad = new JButton("Tải sản phẩm");
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        lblStatus = new JLabel("Chưa tải dữ liệu");

        JPanel headerPanel = new JPanel(new GridLayout(3, 1));
        headerPanel.add(btnLoad);
        headerPanel.add(progressBar);
        headerPanel.add(lblStatus);

        tableModel = new DefaultTableModel(new Object[]{"Mã SP", "Tên SP", "Đơn giá"}, 0);
        table = new JTable(tableModel);

        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnLoad.addActionListener(e -> loadProducts());
    }

    private List<Object[]> taoDuLieuGiaLap() {
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[]{"SP01", "Bàn phím", 250000});
        data.add(new Object[]{"SP02", "Chuột", 150000});
        data.add(new Object[]{"SP03", "Màn hình", 2500000});
        data.add(new Object[]{"SP04", "Tai nghe", 350000});
        data.add(new Object[]{"SP05", "Webcam", 450000});
        return data;
    }

    private void loadProducts() {
        btnLoad.setEnabled(false);
        progressBar.setValue(0);
        lblStatus.setText("Đang tải sản phẩm...");
        tableModel.setRowCount(0);

        SwingWorker<Void, Object[]> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                List<Object[]> data = taoDuLieuGiaLap();
                for (int i = 0; i < data.size(); i++) {
                    publish(data.get(i));
                    setProgress((int) ((i + 1) * 100.0 / data.size()));
                    Thread.sleep(500);
                }
                return null;
            }

            @Override
            protected void process(List<Object[]> chunks) {
                for (Object[] row : chunks) {
                    tableModel.addRow(row);
                }
            }

            @Override
            protected void done() {
                lblStatus.setText("Tải sản phẩm hoàn tất");
                btnLoad.setEnabled(true);
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((int) evt.getNewValue());
            }
        });

        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new B9ProductLoadFrame().setVisible(true));
    }
}