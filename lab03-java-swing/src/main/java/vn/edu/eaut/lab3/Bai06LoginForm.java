package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai06LoginForm extends JFrame {
    private final JTextField txtUsername = new JTextField();
    private final JPasswordField txtPassword = new JPasswordField();
    private final JComboBox<String> cboRole = new JComboBox<>(new String[]{"Admin", "User"});
    private final JCheckBox chkShowPassword = new JCheckBox("Hiển thị mật khẩu");

    public Bai06LoginForm() {
        setTitle("Bài 6 - Đăng nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 8, 8));

        add(new JLabel("Tài khoản:"));
        add(txtUsername);
        add(new JLabel("Mật khẩu:"));
        add(txtPassword);
        add(new JLabel("Vai trò:"));
        add(cboRole);
        add(new JLabel(""));
        add(chkShowPassword);

        JButton btnLogin = new JButton("Đăng nhập");
        add(new JLabel(""));
        add(btnLogin);

        chkShowPassword.addActionListener(e ->
                txtPassword.setEchoChar(chkShowPassword.isSelected() ? (char) 0 : '•'));

        btnLogin.addActionListener(e -> xuLyDangNhap());

        setSize(380, 230);
        setLocationRelativeTo(null);
    }

    private void xuLyDangNhap() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String role = (String) cboRole.getSelectedItem();

        boolean hopLe = (username.equals("admin") && password.equals("123456") && "Admin".equals(role))
                || (username.equals("user") && password.equals("123456") && "User".equals(role));

        if (hopLe) {
            JOptionPane.showMessageDialog(this, "Chào mừng " + username + " (" + role + ")!");
        } else {
            JOptionPane.showMessageDialog(this, "Sai tài khoản, mật khẩu hoặc vai trò!",
                    "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai06LoginForm().setVisible(true));
    }
}