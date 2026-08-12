package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;

public class B6CancelableProgressFrame extends JFrame {
    private JButton btnLoad;
    private JButton btnCancel;
    private JProgressBar progressBar;
    private JLabel lblStatus;
    private SwingWorker<Void, Integer> worker;

    public B6CancelableProgressFrame() {
        setTitle("Bài 6 - Tải dữ liệu có thể hủy");
        setSize(450, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnLoad = new JButton("Bắt đầu tải");
        btnCancel = new JButton("Hủy");
        btnCancel.setEnabled(false);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        lblStatus = new JLabel("Sẵn sàng");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnLoad);
        buttonPanel.add(btnCancel);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.add(buttonPanel);
        panel.add(progressBar);
        panel.add(lblStatus);
        add(panel);

        btnLoad.addActionListener(e -> startLoading());
        btnCancel.addActionListener(e -> cancelLoading());
    }

    private void startLoading() {
        btnLoad.setEnabled(false);
        btnCancel.setEnabled(true);
        progressBar.setValue(0);
        lblStatus.setText("Đang tải dữ liệu...");

        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i += 5) {
                    if (isCancelled()) {
                        break;
                    }
                    setProgress(i);
                    Thread.sleep(300);
                }
                return null;
            }

            @Override
            protected void done() {
                btnLoad.setEnabled(true);
                btnCancel.setEnabled(false);
                if (isCancelled()) {
                    lblStatus.setText("Đã hủy tác vụ");
                } else {
                    progressBar.setValue(100);
                    lblStatus.setText("Tải dữ liệu hoàn tất");
                }
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((int) evt.getNewValue());
            }
        });

        worker.execute();
    }

    private void cancelLoading() {
        if (worker != null && !worker.isDone()) {
            worker.cancel(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new B6CancelableProgressFrame().setVisible(true));
    }
}