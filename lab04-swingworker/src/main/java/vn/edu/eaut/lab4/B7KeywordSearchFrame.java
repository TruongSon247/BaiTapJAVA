package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;

public class B7KeywordSearchFrame extends JFrame {
    private JButton btnChoose;
    private JTextField txtKeyword;
    private JButton btnSearch;
    private JLabel lblFile;
    private JLabel lblResult;
    private JTextArea txtArea;
    private File selectedFile;

    public B7KeywordSearchFrame() {
        setTitle("Bài 7 - Tìm từ khóa trong file");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        btnChoose = new JButton("Chọn file");
        txtKeyword = new JTextField(15);
        btnSearch = new JButton("Tìm kiếm");
        lblFile = new JLabel("Chưa chọn file");
        lblResult = new JLabel("Kết quả: ");
        txtArea = new JTextArea();
        txtArea.setEditable(false);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(btnChoose);
        topPanel.add(new JLabel("Từ khóa:"));
        topPanel.add(txtKeyword);
        topPanel.add(btnSearch);

        JPanel headerPanel = new JPanel(new GridLayout(3, 1));
        headerPanel.add(topPanel);
        headerPanel.add(lblFile);
        headerPanel.add(lblResult);

        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(txtArea), BorderLayout.CENTER);

        btnChoose.addActionListener(e -> chooseFile());
        btnSearch.addActionListener(e -> searchKeyword());
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            lblFile.setText("File: " + selectedFile.getAbsolutePath());
        }
    }

    private void searchKeyword() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn file trước");
            return;
        }
        String keyword = txtKeyword.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa");
            return;
        }

        btnSearch.setEnabled(false);
        txtArea.setText("");
        lblResult.setText("Đang tìm kiếm...");

        SwingWorker<List<String>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                List<String> matched = new ArrayList<>();
                String lowerKeyword = keyword.toLowerCase();
                try (BufferedReader reader = Files.newBufferedReader(
                        selectedFile.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    int lineNumber = 0;
                    while ((line = reader.readLine()) != null) {
                        lineNumber++;
                        if (line.toLowerCase().contains(lowerKeyword)) {
                            matched.add("Dòng " + lineNumber + ": " + line);
                        }
                    }
                }
                return matched;
            }

            @Override
            protected void done() {
                try {
                    List<String> matched = get();
                    txtArea.setText(String.join("\n", matched));
                    lblResult.setText("Tìm thấy " + matched.size() + " dòng chứa từ khóa");
                } catch (Exception ex) {
                    lblResult.setText("Lỗi khi tìm kiếm");
                }
                btnSearch.setEnabled(true);
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new B7KeywordSearchFrame().setVisible(true));
    }
}