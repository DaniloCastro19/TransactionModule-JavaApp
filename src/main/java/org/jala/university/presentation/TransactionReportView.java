package org.jala.university.presentation;

import org.jala.university.dao.TransactionDAOMock;
import org.jala.university.domain.HistoryReportGenerator;
import org.jala.university.domain.TransactionModule;
import org.jala.university.domain.TransactionModuleImpl;
import org.jala.university.model.Transaction;
import org.jala.university.model.BankUser;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.util.List;

public class TransactionReportView extends JFrame {

    private JTextArea reportTextArea;
    private TransactionModule transactionModule;
    private HistoryReportGenerator reportGenerator;

    public TransactionReportView(BankUser user, String report) {
        initializeUI();
        showReport(report);
        setTitle("Reporte de Transacciones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Reporte de Transacciones");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }
    private void showReport(String report) {
        reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);
        reportTextArea.setText(report); // Mostrar el reporte en el TextArea
        JScrollPane scrollPane = new JScrollPane(reportTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }
}
