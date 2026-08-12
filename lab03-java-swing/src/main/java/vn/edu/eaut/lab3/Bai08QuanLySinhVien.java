package vn.edu.eaut.lab3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bai08QuanLySinhVien extends JFrame {
    private final JTextField txtId = new JTextField();
    private final JTextField txtName = new JTextField();
    private final JTextField txtScore = new JTextField();
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final List<Student> danhSach = new ArrayList<>();

    public Bai08QuanLySinhVien() {
        setTitle("Bài 8 - Quản lý sinh viên");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        formPanel.add(new JLabel("Mã SV:"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Họ tên:"));
        formPanel.add(txtName);
        formPanel.add(new JLabel("Điểm TB:"));
        formPanel.add(txtScore);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Làm mới");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        tableModel = new DefaultTableModel(
                new Object[]{"Mã SV", "Họ tên", "Điểm TB", "Xếp loại"}, 0);
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(e -> chonDongTrongBang());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> themSinhVien());
        btnEdit.addActionListener(e -> suaSinhVien());
        btnDelete.addActionListener(e -> xoaSinhVien());
        btnClear.addActionListener(e -> lamMoi());

        setSize(600, 450);
        setLocationRelativeTo(null);
    }

    private void themSinhVien() {
        try {
            String id = txtId.getText().trim();
            String name = txtName.getText().trim();
            double diem = Double.parseDouble(txtScore.getText().trim());

            if (id.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ mã SV và họ tên!");
                return;
            }
            if (diem < 0 || diem > 10) {
                JOptionPane.showMessageDialog(this, "Điểm phải từ 0 đến 10!");
                return;
            }

            Student sv = new Student(id, name, diem);
            danhSach.add(sv);
            capNhatBang();
            lamMoi();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Điểm trung bình phải là số hợp lệ!");
        }
    }

    private void suaSinhVien() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng trong bảng để sửa!");
            return;
        }
        try {
            String name = txtName.getText().trim();
            double diem = Double.parseDouble(txtScore.getText().trim());

            Student sv = danhSach.get(row);
            sv.setFullName(name);
            sv.setDiemTrungBinh(diem);
            capNhatBang();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Điểm trung bình phải là số hợp lệ!");
        }
    }

    private void xoaSinhVien() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng trong bảng để xóa!");
            return;
        }
        danhSach.remove(row);
        capNhatBang();
        lamMoi();
    }

    private void chonDongTrongBang() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            Student sv = danhSach.get(row);
            txtId.setText(sv.getStudentId());
            txtName.setText(sv.getFullName());
            txtScore.setText(String.valueOf(sv.getDiemTrungBinh()));
        }
    }

    private void capNhatBang() {
        tableModel.setRowCount(0);
        for (Student sv : danhSach) {
            tableModel.addRow(new Object[]{
                    sv.getStudentId(), sv.getFullName(),
                    sv.getDiemTrungBinh(), sv.xepLoai()
            });
        }
    }

    private void lamMoi() {
        txtId.setText("");
        txtName.setText("");
        txtScore.setText("");
        table.clearSelection();
        txtId.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai08QuanLySinhVien().setVisible(true));
    }
}