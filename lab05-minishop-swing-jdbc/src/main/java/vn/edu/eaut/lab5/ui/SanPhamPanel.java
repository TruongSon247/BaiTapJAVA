package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.SanPham;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class SanPhamPanel extends JPanel {

    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();

    private JTextField txtMaSp;
    private JTextField txtTenSp;
    private JTextField txtDonGia;
    private JTextField txtSoLuong;
    private JTextField txtTimKiem;
    private JTextField txtGiaTu, txtGiaDen, txtSlTu, txtSlDen;
    private JLabel lblTrangHienTai;
    private int trangHienTai = 1;
    private final int SO_DONG_MOI_TRANG = 10;
    private int tongSoTrang = 1;
    private JTable table;
    private DefaultTableModel tableModel;

    public SanPhamPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);

        loadData();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Thong tin san pham"));

        txtMaSp = new JTextField();
        txtMaSp.setEditable(false);
        txtTenSp = new JTextField();
        txtDonGia = new JTextField();
        txtSoLuong = new JTextField();
        txtTimKiem = new JTextField();

        panel.add(new JLabel("Ma SP:"));
        panel.add(txtMaSp);
        panel.add(new JLabel("Ten SP:"));
        panel.add(txtTenSp);

        panel.add(new JLabel("Don gia:"));
        panel.add(txtDonGia);
        panel.add(new JLabel("So luong:"));
        panel.add(txtSoLuong);

        return panel;
    }

    private JScrollPane buildTablePanel() {
        String[] columns = {"Ma SP", "Ten SP", "Don gia", "So luong"};
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
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnThem = new JButton("Them");
        JButton btnSua = new JButton("Sua");
        JButton btnXoa = new JButton("Xoa");
        JButton btnLamMoi = new JButton("Lam moi");

        btnThem.addActionListener(e -> themSanPham());
        btnSua.addActionListener(e -> suaSanPham());
        btnXoa.addActionListener(e -> xoaSanPham());
        btnLamMoi.addActionListener(e -> { clearForm(); clearBoLoc(); trangHienTai = 1; loadData(); });

        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);
        panel.add(btnLamMoi);

        JPanel locPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        locPanel.setBorder(BorderFactory.createTitledBorder("Tim kiem nang cao"));
        txtTimKiem = new JTextField(10);
        txtGiaTu = new JTextField(6);
        txtGiaDen = new JTextField(6);
        txtSlTu = new JTextField(4);
        txtSlDen = new JTextField(4);
        JButton btnTimKiem = new JButton("Tim kiem");
        btnTimKiem.addActionListener(e -> { trangHienTai = 1; loadData(); });

        locPanel.add(new JLabel("Ten:"));
        locPanel.add(txtTimKiem);
        locPanel.add(new JLabel("Gia tu:"));
        locPanel.add(txtGiaTu);
        locPanel.add(new JLabel("den:"));
        locPanel.add(txtGiaDen);
        locPanel.add(new JLabel("SL tu:"));
        locPanel.add(txtSlTu);
        locPanel.add(new JLabel("den:"));
        locPanel.add(txtSlDen);
        locPanel.add(btnTimKiem);

        JPanel phanTrangPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnDau = new JButton("<< Dau");
        JButton btnTruoc = new JButton("< Truoc");
        JButton btnSau = new JButton("Sau >");
        JButton btnCuoi = new JButton("Cuoi >>");
        lblTrangHienTai = new JLabel("Trang 1/1");

        btnDau.addActionListener(e -> { trangHienTai = 1; loadData(); });
        btnTruoc.addActionListener(e -> { if (trangHienTai > 1) { trangHienTai--; loadData(); } });
        btnSau.addActionListener(e -> { if (trangHienTai < tongSoTrang) { trangHienTai++; loadData(); } });
        btnCuoi.addActionListener(e -> { trangHienTai = tongSoTrang; loadData(); });

        phanTrangPanel.add(btnDau);
        phanTrangPanel.add(btnTruoc);
        phanTrangPanel.add(lblTrangHienTai);
        phanTrangPanel.add(btnSau);
        phanTrangPanel.add(btnCuoi);

        wrapper.add(panel);
        wrapper.add(locPanel);
        wrapper.add(phanTrangPanel);
        return wrapper;
    }

    private void clearBoLoc() {
        txtTimKiem.setText("");
        txtGiaTu.setText("");
        txtGiaDen.setText("");
        txtSlTu.setText("");
        txtSlDen.setText("");
    }

    private void loadData() {
        try {
            String ten = txtTimKiem.getText().trim();
            BigDecimal giaTu = parseBigDecimalOrNull(txtGiaTu.getText());
            BigDecimal giaDen = parseBigDecimalOrNull(txtGiaDen.getText());
            Integer slTu = parseIntOrNull(txtSlTu.getText());
            Integer slDen = parseIntOrNull(txtSlDen.getText());

            int tongSoDong = sanPhamBUS.demTongSoDong(ten, giaTu, giaDen, slTu, slDen);
            tongSoTrang = Math.max(1, (int) Math.ceil((double) tongSoDong / SO_DONG_MOI_TRANG));
            if (trangHienTai > tongSoTrang) trangHienTai = tongSoTrang;
            if (trangHienTai < 1) trangHienTai = 1;

            List<SanPham> list = sanPhamBUS.searchAdvanced(ten, giaTu, giaDen, slTu, slDen, trangHienTai, SO_DONG_MOI_TRANG);
            hienThiLenBang(list);
            lblTrangHienTai.setText("Trang " + trangHienTai + "/" + tongSoTrang);
        } catch (SQLException e) {
            showError(e);
        }
    }

    private BigDecimal parseBigDecimalOrNull(String text) {
        text = text.trim();
        if (text.isEmpty()) return null;
        try {
            return new BigDecimal(text);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private Integer parseIntOrNull(String text) {
        text = text.trim();
        if (text.isEmpty()) return null;
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private void hienThiLenBang(List<SanPham> list) {
        tableModel.setRowCount(0);
        for (SanPham sp : list) {
            tableModel.addRow(new Object[]{
                sp.getMaSp(), sp.getTenSp(), sp.getDonGia(), sp.getSoLuong()
            });
        }
    }

    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        txtMaSp.setText(tableModel.getValueAt(row, 0).toString());
        txtTenSp.setText(tableModel.getValueAt(row, 1).toString());
        txtDonGia.setText(tableModel.getValueAt(row, 2).toString());
        txtSoLuong.setText(tableModel.getValueAt(row, 3).toString());
    }

    private void themSanPham() {
        try {
            SanPham sp = new SanPham();
            sp.setTenSp(txtTenSp.getText().trim());
            sp.setDonGia(new BigDecimal(txtDonGia.getText().trim()));
            sp.setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));

            sanPhamBUS.save(sp);
            JOptionPane.showMessageDialog(this, "Them san pham thanh cong!");
            clearForm();
            loadData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Don gia / so luong khong hop le!");
        } catch (IllegalArgumentException | SQLException ex) {
            showError(ex);
        }
    }

    private void suaSanPham() {
        if (txtMaSp.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chon mot san pham trong bang truoc!");
            return;
        }
        try {
            SanPham sp = new SanPham();
            sp.setMaSp(Integer.parseInt(txtMaSp.getText()));
            sp.setTenSp(txtTenSp.getText().trim());
            sp.setDonGia(new BigDecimal(txtDonGia.getText().trim()));
            sp.setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));

            sanPhamBUS.save(sp);
            JOptionPane.showMessageDialog(this, "Cap nhat thanh cong!");
            clearForm();
            loadData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Don gia / so luong khong hop le!");
        } catch (IllegalArgumentException | SQLException ex) {
            showError(ex);
        }
    }

    private void xoaSanPham() {
        if (txtMaSp.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chon mot san pham trong bang truoc!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Ban co chac muon xoa?", "Xac nhan", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            sanPhamBUS.delete(Integer.parseInt(txtMaSp.getText()));
            JOptionPane.showMessageDialog(this, "Xoa thanh cong!");
            clearForm();
            loadData();
        } catch (IllegalArgumentException | SQLException ex) {
            showError(ex);
        }
    }

    private void clearForm() {
        txtMaSp.setText("");
        txtTenSp.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("");
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
    }
}