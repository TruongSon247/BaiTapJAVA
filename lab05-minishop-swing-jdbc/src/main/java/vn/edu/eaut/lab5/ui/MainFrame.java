package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.model.TaiKhoan;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(TaiKhoan taiKhoan) {
        setTitle("MiniShop - Xin chao " + taiKhoan.getHoTen() + " (" + taiKhoan.getVaiTro() + ")");
        setSize(850, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        String vaiTro = taiKhoan.getVaiTro();

        // ADMIN: toan quyen tat ca tab
        // NHANVIEN: san pham, danh muc, khach hang, hoa don (khong co thong ke)
        // KETOAN: chi xem hoa don va thong ke
        if (vaiTro.equals("ADMIN") || vaiTro.equals("NHANVIEN")) {
            tabbedPane.addTab("San pham", new SanPhamPanel());
            tabbedPane.addTab("Danh muc", new DanhMucPanel());
            tabbedPane.addTab("Khach hang", new KhachHangPanel());
            tabbedPane.addTab("Hoa don", new HoaDonPanel());
        }
        if (vaiTro.equals("KETOAN")) {
            tabbedPane.addTab("Hoa don", new HoaDonPanel());
        }
        if (vaiTro.equals("ADMIN") || vaiTro.equals("KETOAN")) {
            tabbedPane.addTab("Thong ke", new ThongKePanel());
        }

        JPanel topPanel = new JPanel(new BorderLayout());
        JButton btnDangXuat = new JButton("Dang xuat");
        btnDangXuat.addActionListener(e -> dangXuat());
        JPanel rightTop = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightTop.add(btnDangXuat);
        topPanel.add(rightTop, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private void dangXuat() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        dispose();
    }
}