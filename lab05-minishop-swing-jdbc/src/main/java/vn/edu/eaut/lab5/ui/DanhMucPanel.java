package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.DanhMucBUS;
import vn.edu.eaut.lab5.model.DanhMuc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DanhMucPanel extends JPanel {

    private final DanhMucBUS danhMucBUS = new DanhMucBUS();

    private JTextField txtMaDm;
    private JTextField txtTenDm;
    private JTable table;
    private DefaultTableModel tableModel;

    public DanhMucPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);

        loadData();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Thong tin danh muc"));

        txtMaDm = new JTextField();
        txtMaDm.setEditable(false);
        txtTenDm = new JTextField();

        panel.add(new JLabel("Ma DM:"));
        panel.add(txtMaDm);
        panel.add(new JLabel("Ten danh muc:"));
        panel.add(txtTenDm);

        return panel;
    }

    private JScrollPane buildTablePanel() {
        String[] columns = {"Ma DM", "Ten danh muc"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                txtMaDm.setText(tableModel.getValueAt(row, 0).toString());
                txtTenDm.setText(tableModel.getValueAt(row, 1).toString());
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

        btnThem.addActionListener(e -> them());
        btnSua.addActionListener(e -> sua());
        btnXoa.addActionListener(e -> xoa());
        btnLamMoi.addActionListener(e -> { clearForm(); loadData(); });

        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);
        panel.add(btnLamMoi);
        return panel;
    }

    private void loadData() {
        try {
            tableModel.setRowCount(0);
            List<DanhMuc> list = danhMucBUS.findAll();
            for (DanhMuc dm : list) {
                tableModel.addRow(new Object[]{dm.getMaDm(), dm.getTenDm()});
            }
        } catch (SQLException e) {
            showError(e);
        }
    }

    private void them() {
        try {
            DanhMuc dm = new DanhMuc();
            dm.setTenDm(txtTenDm.getText().trim());
            danhMucBUS.save(dm);
            JOptionPane.showMessageDialog(this, "Them danh muc thanh cong!");
            clearForm();
            loadData();
        } catch (IllegalArgumentException | SQLException ex) {
            showError(ex);
        }
    }

    private void sua() {
        if (txtMaDm.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chon mot danh muc trong bang truoc!");
            return;
        }
        try {
            DanhMuc dm = new DanhMuc();
            dm.setMaDm(Integer.parseInt(txtMaDm.getText()));
            dm.setTenDm(txtTenDm.getText().trim());
            danhMucBUS.save(dm);
            JOptionPane.showMessageDialog(this, "Cap nhat thanh cong!");
            clearForm();
            loadData();
        } catch (IllegalArgumentException | SQLException ex) {
            showError(ex);
        }
    }

    private void xoa() {
        if (txtMaDm.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chon mot danh muc trong bang truoc!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Ban co chac muon xoa?", "Xac nhan", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            danhMucBUS.delete(Integer.parseInt(txtMaDm.getText()));
            JOptionPane.showMessageDialog(this, "Xoa thanh cong!");
            clearForm();
            loadData();
        } catch (IllegalArgumentException | SQLException ex) {
            showError(ex);
        }
    }

    private void clearForm() {
        txtMaDm.setText("");
        txtTenDm.setText("");
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
    }
}