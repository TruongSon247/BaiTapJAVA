package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class B10ProductManagerFrame extends JFrame {
    private JTextField txtMaSp;
    private JTextField txtTenSp;
    private JTextField txtDonGia;
    private JTable table;
    private DefaultTableModel tableModel;
    private final List<b10Product> danhSach = new ArrayList<>();

    public B10ProductManagerFrame() {
        setTitle("Bài 10 - Quản lý sản phẩm bằng CSV");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        txtMaSp = new JTextField();
        txtTenSp = new JTextField();
        txtDonGia = new JTextField();

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        formPanel.add(new JLabel("Mã SP:"));
        formPanel.add(txtMaSp);
        formPanel.add(new JLabel("Tên SP:"));
        formPanel.add(txtTenSp);
        formPanel.add(new JLabel("Đơn giá:"));
        formPanel.add(txtDonGia);

        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnSave = new JButton("Lưu CSV");
        JButton btnLoad = new JButton("Đọc CSV");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnLoad);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new Object[]{"Mã SP", "Tên SP", "Đơn giá"}, 0);
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(e -> chonDong());

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> themSanPham());
        btnEdit.addActionListener(e -> suaSanPham());
        btnDelete.addActionListener(e -> xoaSanPham());
        btnSave.addActionListener(e -> luuFile());
        btnLoad.addActionListener(e -> docFile());
    }

    private void themSanPham() {
        try {
            String ma = txtMaSp.getText().trim();
            String ten = txtTenSp.getText().trim();
            double gia = Double.parseDouble(txtDonGia.getText().trim());
            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ mã và tên sản phẩm!");
                return;
            }
            danhSach.add(new b10Product(ma, ten, gia));
            capNhatBang();
            lamMoi();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số hợp lệ!");
        }
    }

    private void suaSanPham() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để sửa!");
            return;
        }
        try {
            b10Product sp = danhSach.get(row);
            sp.setTenSp(txtTenSp.getText().trim());
            sp.setDonGia(Double.parseDouble(txtDonGia.getText().trim()));
            capNhatBang();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số hợp lệ!");
        }
    }

    private void xoaSanPham() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để xóa!");
            return;
        }
        danhSach.remove(row);
        capNhatBang();
        lamMoi();
    }

    private void chonDong() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            b10Product sp = danhSach.get(row);
            txtMaSp.setText(sp.getMaSp());
            txtTenSp.setText(sp.getTenSp());
            txtDonGia.setText(String.valueOf(sp.getDonGia()));
        }
    }

    private void capNhatBang() {
        tableModel.setRowCount(0);
        for (b10Product sp : danhSach) {
            tableModel.addRow(new Object[]{sp.getMaSp(), sp.getTenSp(), sp.getDonGia()});
        }
    }

    private void lamMoi() {
        txtMaSp.setText("");
        txtTenSp.setText("");
        txtDonGia.setText("");
        table.clearSelection();
    }

    private void luuFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try (BufferedWriter writer = Files.newBufferedWriter(
                        file.toPath(), StandardCharsets.UTF_8)) {
                    writer.write("MaSP,TenSP,DonGia\n");
                    for (b10Product sp : danhSach) {
                        writer.write(sp.getMaSp() + "," + sp.getTenSp() + "," + sp.getDonGia() + "\n");
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                JOptionPane.showMessageDialog(B10ProductManagerFrame.this, "Đã lưu file thành công!");
            }
        };
        worker.execute();
    }

    private void docFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();

        SwingWorker<List<b10Product>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<b10Product> doInBackground() throws Exception {
                List<b10Product> list = new ArrayList<>();
                try (BufferedReader reader = Files.newBufferedReader(
                        file.toPath(), StandardCharsets.UTF_8)) {
                    String line = reader.readLine(); // bỏ header
                    while ((line = reader.readLine()) != null) {
                        if (line.trim().isEmpty()) continue;
                        String[] parts = line.split(",");
                        if (parts.length < 3) continue;
                        list.add(new b10Product(parts[0].trim(), parts[1].trim(),
                                Double.parseDouble(parts[2].trim())));
                    }
                }
                return list;
            }

            @Override
            protected void done() {
                try {
                    danhSach.clear();
                    danhSach.addAll(get());
                    capNhatBang();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(B10ProductManagerFrame.this, "Lỗi khi đọc file!");
                }
            }
        };
        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new B10ProductManagerFrame().setVisible(true));
    }
}