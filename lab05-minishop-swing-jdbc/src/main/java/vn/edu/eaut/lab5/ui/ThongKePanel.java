package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.ThongKeBUS;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ThongKePanel extends JPanel {

    private final ThongKeBUS thongKeBUS = new ThongKeBUS();

    private JTextField txtTuNgay;
    private JTextField txtDenNgay;
    private JLabel lblDoanhThu;
    private JLabel lblHoaDonCaoNhat;
    private JLabel lblSanPhamBanChay;

    public ThongKePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildResultPanel(), BorderLayout.CENTER);
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Thong ke doanh thu (dinh dang ngay: yyyy-MM-dd)"));

        txtTuNgay = new JTextField(LocalDate.now().withDayOfMonth(1).toString());
        txtDenNgay = new JTextField(LocalDate.now().toString());
        JButton btnThongKe = new JButton("Thong ke");
        btnThongKe.addActionListener(e -> chayThongKe());

        panel.add(new JLabel("Tu ngay:"));
        panel.add(txtTuNgay);
        panel.add(new JLabel("Den ngay:"));
        panel.add(txtDenNgay);

        panel.add(new JLabel(""));
        panel.add(btnThongKe);

        return panel;
    }

    private JPanel buildResultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Ket qua"));

        lblDoanhThu = new JLabel("Doanh thu: chua thong ke");
        lblHoaDonCaoNhat = new JLabel("Hoa don cao nhat: chua thong ke");
        lblSanPhamBanChay = new JLabel("San pham ban chay nhat: chua thong ke");

        Font font = lblDoanhThu.getFont().deriveFont(14f);
        lblDoanhThu.setFont(font);
        lblHoaDonCaoNhat.setFont(font);
        lblSanPhamBanChay.setFont(font);

        lblDoanhThu.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
        lblHoaDonCaoNhat.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
        lblSanPhamBanChay.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

        panel.add(lblDoanhThu);
        panel.add(lblHoaDonCaoNhat);
        panel.add(lblSanPhamBanChay);

        return panel;
    }

    private void chayThongKe() {
        LocalDate tuNgay, denNgay;
        try {
            tuNgay = LocalDate.parse(txtTuNgay.getText().trim());
            denNgay = LocalDate.parse(txtDenNgay.getText().trim());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Dinh dang ngay khong hop le! Dung yyyy-MM-dd, vi du 2026-08-01");
            return;
        }

        lblDoanhThu.setText("Doanh thu: dang tinh...");
        lblHoaDonCaoNhat.setText("Hoa don cao nhat: dang tinh...");
        lblSanPhamBanChay.setText("San pham ban chay nhat: dang tinh...");

        // Dung SwingWorker de truy van CSDL o luong nen, tranh treo giao dien (khong chay tren EDT)
        new ThongKeWorker(tuNgay, denNgay).execute();
    }

    /**
     * SwingWorker<TenKetQua, TenTienTrinh>
     * doInBackground() chay tren luong nen (khong duoc dong cham truc tiep vao Swing component o day)
     * done() chay lai tren EDT, dung de cap nhat giao dien an toan
     */
    private class ThongKeWorker extends SwingWorker<Object[], Void> {
        private final LocalDate tuNgay;
        private final LocalDate denNgay;

        ThongKeWorker(LocalDate tuNgay, LocalDate denNgay) {
            this.tuNgay = tuNgay;
            this.denNgay = denNgay;
        }

        @Override
        protected Object[] doInBackground() throws Exception {
            BigDecimal doanhThu = thongKeBUS.tinhDoanhThu(tuNgay, denNgay);
            String hoaDonCaoNhat = thongKeBUS.hoaDonCaoNhat();
            String sanPhamBanChay = thongKeBUS.sanPhamBanChayNhat();
            return new Object[]{doanhThu, hoaDonCaoNhat, sanPhamBanChay};
        }

        @Override
        protected void done() {
            try {
                Object[] ketQua = get();
                lblDoanhThu.setText("Doanh thu: " + ketQua[0] + " VND");
                lblHoaDonCaoNhat.setText("Hoa don cao nhat: " + ketQua[1]);
                lblSanPhamBanChay.setText("San pham ban chay nhat: " + ketQua[2]);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(ThongKePanel.this, "Loi thong ke: " + e.getMessage());
            }
        }
    }
}