package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.util.HoaDonFileUtil;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import vn.edu.eaut.lab5.bus.HoaDonBUS;
import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.model.SanPham;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoaDonPanel extends JPanel {

    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();
    private final KhachHangBUS khachHangBUS = new KhachHangBUS();
    private final HoaDonBUS hoaDonBUS = new HoaDonBUS();

    private JComboBox<KhachHang> cboKhachHang;
    private JComboBox<SanPham> cboSanPham;
    private JTextField txtSoLuong;
    private JTable tblChiTiet;
    private DefaultTableModel tableModel;
    private JLabel lblTongTien;

    private final List<ChiTietHoaDon> chiTietTam = new ArrayList<>();

    public HoaDonPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildBottomPanel(), BorderLayout.SOUTH);

        loadComboBoxData();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Lap hoa don"));

        cboKhachHang = new JComboBox<>();
        cboSanPham = new JComboBox<>();
        txtSoLuong = new JTextField();
        JButton btnThemDong = new JButton("Them dong");
        btnThemDong.addActionListener(e -> themDongChiTiet());

        panel.add(new JLabel("Khach hang:"));
        panel.add(cboKhachHang);
        panel.add(new JLabel("San pham:"));
        panel.add(cboSanPham);

        panel.add(new JLabel("So luong:"));
        panel.add(txtSoLuong);
        panel.add(new JLabel(""));
        panel.add(btnThemDong);

        return panel;
    }

    private JScrollPane buildTablePanel() {
        String[] columns = {"Ma SP", "Ten SP", "So luong", "Don gia", "Thanh tien"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblChiTiet = new JTable(tableModel);
        return new JScrollPane(tblChiTiet);
    }

    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnXoaDong = new JButton("Xoa dong da chon");
        JButton btnLuuHoaDon = new JButton("Luu hoa don");
        btnXoaDong.addActionListener(e -> xoaDongDaChon());
        btnLuuHoaDon.addActionListener(e -> luuHoaDon());
        actionPanel.add(btnXoaDong);
        actionPanel.add(btnLuuHoaDon);

        lblTongTien = new JLabel("Tong tien: 0");
        lblTongTien.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblTongTien.setFont(lblTongTien.getFont().deriveFont(Font.BOLD, 14f));

        panel.add(actionPanel, BorderLayout.WEST);
        panel.add(lblTongTien, BorderLayout.EAST);
        return panel;
    }

    private void loadComboBoxData() {
        try {
            cboKhachHang.removeAllItems();
            for (KhachHang kh : khachHangBUS.findAll()) {
                cboKhachHang.addItem(kh);
            }
            cboSanPham.removeAllItems();
            for (SanPham sp : sanPhamBUS.findAll()) {
                cboSanPham.addItem(sp);
            }
        } catch (SQLException e) {
            showError(e);
        }
    }

    private void themDongChiTiet() {
        SanPham sp = (SanPham) cboSanPham.getSelectedItem();
        if (sp == null) {
            JOptionPane.showMessageDialog(this, "Khong co san pham de chon!");
            return;
        }
        int soLuong;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "So luong khong hop le!");
            return;
        }
        if (soLuong <= 0) {
            JOptionPane.showMessageDialog(this, "So luong phai lon hon 0!");
            return;
        }
        if (soLuong > sp.getSoLuong()) {
            JOptionPane.showMessageDialog(this, "So luong ban vuot qua ton kho (con " + sp.getSoLuong() + ")!");
            return;
        }

        ChiTietHoaDon ct = new ChiTietHoaDon(sp.getMaSp(), sp.getTenSp(), soLuong, sp.getDonGia());
        chiTietTam.add(ct);
        tableModel.addRow(new Object[]{
            ct.getMaSp(), ct.getTenSp(), ct.getSoLuong(), ct.getDonGia(), ct.getThanhTien()
        });

        txtSoLuong.setText("");
        capNhatTongTien();
    }

    private void xoaDongDaChon() {
        int row = tblChiTiet.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chon mot dong trong bang truoc!");
            return;
        }
        chiTietTam.remove(row);
        tableModel.removeRow(row);
        capNhatTongTien();
    }

    private void capNhatTongTien() {
        BigDecimal tong = BigDecimal.ZERO;
        for (ChiTietHoaDon ct : chiTietTam) {
            tong = tong.add(ct.getThanhTien());
        }
        lblTongTien.setText("Tong tien: " + tong);
    }

    private void luuHoaDon() {
    KhachHang kh = (KhachHang) cboKhachHang.getSelectedItem();
    if (kh == null) {
        JOptionPane.showMessageDialog(this, "Vui long chon khach hang!");
        return;
    }
    try {
        BigDecimal tongTienDaLuu = tinhTongTienHienTai();
        List<ChiTietHoaDon> chiTietDaLuu = new ArrayList<>(chiTietTam);

        int maHd = hoaDonBUS.lapHoaDon(kh.getMaKh(), chiTietTam);
        JOptionPane.showMessageDialog(this, "Luu hoa don thanh cong! Ma hoa don: " + maHd);

        int xuatFile = JOptionPane.showConfirmDialog(this,
                "Ban co muon xuat hoa don ra file TXT khong?", "Xuat file", JOptionPane.YES_NO_OPTION);
        if (xuatFile == JOptionPane.YES_OPTION) {
            xuatFileHoaDon(maHd, kh, chiTietDaLuu, tongTienDaLuu);
        }

        chiTietTam.clear();
        tableModel.setRowCount(0);
        capNhatTongTien();
        loadComboBoxData();
    } catch (IllegalArgumentException | SQLException ex) {
        showError(ex);
    }
}

private BigDecimal tinhTongTienHienTai() {
    BigDecimal tong = BigDecimal.ZERO;
    for (ChiTietHoaDon ct : chiTietTam) {
        tong = tong.add(ct.getThanhTien());
    }
    return tong;
}

private void xuatFileHoaDon(int maHd, KhachHang kh, List<ChiTietHoaDon> chiTietList, BigDecimal tongTien) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setSelectedFile(new File("HoaDon_" + maHd + ".txt"));
    int ketQua = fileChooser.showSaveDialog(this);
    if (ketQua == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        try {
            HoaDonFileUtil.xuatFileTxt(file.getAbsolutePath(), maHd, LocalDate.now(), kh, chiTietList, tongTien);
            JOptionPane.showMessageDialog(this, "Da xuat file: " + file.getAbsolutePath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Loi khi xuat file: " + ex.getMessage());
        }
    }
}

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
    }
}