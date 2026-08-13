package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.util.PhoneDocumentFilter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class KhachHangPanel extends JPanel {

    private final KhachHangBUS khachHangBUS = new KhachHangBUS();

    private JTextField txtMaKh;
    private JTextField txtTenKh;
    private JTextField txtSdt;
    private JTextField txtDiaChi;
    private JTextField txtTimKiem;
    private JTable table;
    private DefaultTableModel tableModel;

    public KhachHangPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);

        loadData();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Thong tin khach hang"));

        txtMaKh = new JTextField();
        txtMaKh.setEditable(false);
        txtTenKh = new JTextField();
        txtSdt = new JTextField();
        // Gan bo loc chi cho nhap so, toi da 10 ky tu
        ((AbstractDocument) txtSdt.getDocument()).setDocumentFilter(new PhoneDocumentFilter());
        txtDiaChi = new JTextField();
        txtTimKiem = new JTextField();

        panel.add(new JLabel("Ma KH:"));
        panel.add(txtMaKh);
        panel.add(new JLabel("Ten KH:"));
        panel.add(txtTenKh);

        panel.add(new JLabel("So dien thoai:"));
        panel.add(txtSdt);
        panel.add(new JLabel("Dia chi:"));
        panel.add(txtDiaChi);

        return panel;
    }

    private JScrollPane buildTablePanel() {
        String[] columns = {"Ma KH", "Ten KH", "SDT", "Dia chi"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                fillFormFromSelectedRow();
            }
        });
        return new JScrollPane(table);
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnThem = new JButton("Them");
        JButton btnSua = new JButton("Sua");
        JButton btnXoa = new JButton("Xoa");
        JButton btnLamMoi = new JButton("Lam moi");
        JButton btnTimKiem = new JButton("Tim kiem");

        btnThem.addActionListener(e -> themKhachHang());
        btnSua.addActionListener(e -> suaKhachHang());
        btnXoa.addActionListener(e -> xoaKhachHang());
        btnLamMoi.addActionListener(e -> { clearForm(); loadData(); });
        btnTimKiem.addActionListener(e -> timKiem());

        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);
        panel.add(btnLamMoi);
        panel.add(new JLabel("   Tim ten:"));
        panel.add(txtTimKiem);
        panel.add(btnTimKiem);

        return panel;
    }

    private void loadData() {
        try {
            hienThiLenBang(khachHangBUS.findAll());
        } catch (SQLException e) {
            showError(e);
        }
    }

    private void timKiem() {
        try {
            hienThiLenBang(khachHangBUS.searchByName(txtTimKiem.getText().trim()));
        } catch (SQLException e) {
            showError(e);
        }
    }

    private void hienThiLenBang(List<KhachHang> list) {
        tableModel.setRowCount(0);
        for (KhachHang kh : list) {
            tableModel.addRow(new Object[]{
                kh.getMaKh(), kh.getTenKh(), kh.getSdt(), kh.getDiaChi()
            });
        }
    }

    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        txtMaKh.setText(tableModel.getValueAt(row, 0).toString());
        txtTenKh.setText(tableModel.getValueAt(row, 1).toString());
        txtSdt.setText(tableModel.getValueAt(row, 2).toString());
        Object diaChi = tableModel.getValueAt(row, 3);
        txtDiaChi.setText(diaChi == null ? "" : diaChi.toString());
    }

    private void themKhachHang() {
        try {
            KhachHang kh = new KhachHang();
            kh.setTenKh(txtTenKh.getText().trim());
            kh.setSdt(txtSdt.getText().trim());
            kh.setDiaChi(txtDiaChi.getText().trim());

            khachHangBUS.save(kh);
            JOptionPane.showMessageDialog(this, "Them khach hang thanh cong!");
            clearForm();
            loadData();
        } catch (IllegalArgumentException | SQLException ex) {
            showError(ex);
        }
    }

    private void suaKhachHang() {
        if (txtMaKh.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chon mot khach hang trong bang truoc!");
            return;
        }
        try {
            KhachHang kh = new KhachHang();
            kh.setMaKh(Integer.parseInt(txtMaKh.getText()));
            kh.setTenKh(txtTenKh.getText().trim());
            kh.setSdt(txtSdt.getText().trim());
            kh.setDiaChi(txtDiaChi.getText().trim());

            khachHangBUS.save(kh);
            JOptionPane.showMessageDialog(this, "Cap nhat thanh cong!");
            clearForm();
            loadData();
        } catch (IllegalArgumentException | SQLException ex) {
            showError(ex);
        }
    }

    private void xoaKhachHang() {
        if (txtMaKh.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chon mot khach hang trong bang truoc!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Ban co chac muon xoa?", "Xac nhan", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            khachHangBUS.delete(Integer.parseInt(txtMaKh.getText()));
            JOptionPane.showMessageDialog(this, "Xoa thanh cong!");
            clearForm();
            loadData();
        } catch (IllegalArgumentException | SQLException ex) {
            showError(ex);
        }
    }

    private void clearForm() {
        txtMaKh.setText("");
        txtTenKh.setText("");
        txtSdt.setText("");
        txtDiaChi.setText("");
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
    }
}