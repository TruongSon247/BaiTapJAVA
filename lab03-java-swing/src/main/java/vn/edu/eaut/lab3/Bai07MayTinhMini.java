package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai07MayTinhMini extends JFrame {
    private final JTextField txtA = new JTextField();
    private final JTextField txtB = new JTextField();
    private final JTextField txtResult = new JTextField();
    private final JTextArea txtHistory = new JTextArea(8, 30);

    public Bai07MayTinhMini() {
        setTitle("Bài 7 - Máy tính mini");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        inputPanel.add(new JLabel("Số thứ nhất:"));
        inputPanel.add(txtA);
        inputPanel.add(new JLabel("Số thứ hai:"));
        inputPanel.add(txtB);
        inputPanel.add(new JLabel("Kết quả:"));
        txtResult.setEditable(false);
        inputPanel.add(txtResult);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        JButton btnCong = new JButton("+");
        JButton btnTru = new JButton("-");
        JButton btnNhan = new JButton("*");
        JButton btnChia = new JButton("/");
        JButton btnClear = new JButton("Clear");
        buttonPanel.add(btnCong);
        buttonPanel.add(btnTru);
        buttonPanel.add(btnNhan);
        buttonPanel.add(btnChia);
        buttonPanel.add(btnClear);

        txtHistory.setEditable(false);

        btnCong.addActionListener(e -> tinhToan("+"));
        btnTru.addActionListener(e -> tinhToan("-"));
        btnNhan.addActionListener(e -> tinhToan("*"));
        btnChia.addActionListener(e -> tinhToan("/"));
        btnClear.addActionListener(e -> lamMoi());

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(txtHistory), BorderLayout.CENTER);

        setSize(420, 400);
        setLocationRelativeTo(null);
    }

    private void tinhToan(String phepTinh) {
        try {
            double a = Double.parseDouble(txtA.getText().trim());
            double b = Double.parseDouble(txtB.getText().trim());
            double ketQua;

            switch (phepTinh) {
                case "+" -> ketQua = a + b;
                case "-" -> ketQua = a - b;
                case "*" -> ketQua = a * b;
                case "/" -> {
                    if (b == 0) {
                        JOptionPane.showMessageDialog(this, "Không thể chia cho 0!");
                        return;
                    }
                    ketQua = a / b;
                }
                default -> throw new IllegalStateException();
            }

            txtResult.setText(String.valueOf(ketQua));
            txtHistory.append(a + " " + phepTinh + " " + b + " = " + ketQua + "\n");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!");
        }
    }

    private void lamMoi() {
        txtA.setText("");
        txtB.setText("");
        txtResult.setText("");
        txtA.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai07MayTinhMini().setVisible(true));
    }
}