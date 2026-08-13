package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.TaiKhoanBUS;
import vn.edu.eaut.lab5.model.TaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginFrame extends JFrame {

    private final TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginFrame() {
        setTitle("Dang nhap - MiniShop");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("DANG NHAP HE THONG", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 16f));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Ten dang nhap:"), gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        panel.add(txtUsername, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(new JLabel("Mat khau:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        panel.add(txtPassword, gbc);

        JButton btnDangNhap = new JButton("Dang nhap");
        btnDangNhap.addActionListener(e -> xuLyDangNhap());
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(btnDangNhap, gbc);

        add(panel);

        // Cho phep bam Enter o o mat khau de dang nhap luon
        txtPassword.addActionListener(e -> xuLyDangNhap());
    }

    private void xuLyDangNhap() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        try {
            TaiKhoan tk = taiKhoanBUS.dangNhap(username, password);
            JOptionPane.showMessageDialog(this, "Xin chao, " + tk.getHoTen() + "!");

            MainFrame mainFrame = new MainFrame(tk);
            mainFrame.setVisible(true);
            dispose(); // dong cua so dang nhap
        } catch (IllegalArgumentException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
        }
    }
}