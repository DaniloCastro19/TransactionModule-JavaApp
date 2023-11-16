package org.jala.university.presentation;


import org.jala.university.dao.AccountDAOMock;
import org.jala.university.dao.TransactionDAOMock;
import org.jala.university.dao.UserDAOMock;
import org.jala.university.dao.MockDataGenerator;
import org.jala.university.domain.TransactionModule;
import org.jala.university.domain.TransactionModuleImpl;
import org.jala.university.model.BankUser;
import org.jala.university.model.Transaction;
import org.jala.university.domain.HistoryReportGenerator;
import  org.jala.university.dao.TransactionDAO;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import  javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class HistoryView extends JFrame {
    private JLabel titleLabel;
    private JPanel searchPanel;
    private JTextField searchInput;
    private JButton searchButton;
    private JTable historyTable;
    private TransactionModule transactionModule;

    private HistoryReportGenerator historyReportGenerator;

    public HistoryView(TransactionModule transactionModule) {
        this.transactionModule = transactionModule;
        this.historyReportGenerator = new HistoryReportGenerator(transactionModule);
        initializeUI();
        setTitle("Historial de Transacciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        addTitleLabel();
        addSearchPanel();
        addTableScrollPane();
    }

    private void addTitleLabel() {
        titleLabel = new JLabel("Historial de Transacciones");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);
    }

    private void addSearchPanel() {
        searchPanel = new JPanel(new FlowLayout());
        searchInput = new JTextField(20);
        searchButton = new JButton("Buscar");
        searchButton.addActionListener(this::performSearch);
        searchPanel.add(searchInput);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.SOUTH);
        JButton generateReportButton = new JButton("Generar Reporte");
        generateReportButton.addActionListener(this::generateReport);
        searchPanel.add(generateReportButton);
        add(searchPanel, BorderLayout.SOUTH);
    }
    private void generateReport(ActionEvent e) {
        String accountNumber = searchInput.getText();

        BankUser user = transactionModule.getUserInfoForAccountNumber(accountNumber);
        if (user != null) {
            String report = historyReportGenerator.generateReport(accountNumber);

            TransactionReportView reportView = new TransactionReportView(user, report);
            reportView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró un usuario con ese número de cuenta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTableScrollPane() {
        HistoryTableModel historyTableModel = new HistoryTableModel(new ArrayList<>());
        historyTable = new JTable(historyTableModel);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void performSearch(ActionEvent e) {
        String accountNumber = searchInput.getText();
        List<Transaction> transactionList;

        transactionList = transactionModule.findTransactionsWithAccountNumber(accountNumber);

        updateTableModel(transactionList);
    }

    private void updateTableModel(List<Transaction> transactionList) {
        HistoryTableModel tableModel = (HistoryTableModel) historyTable.getModel();
        tableModel.setTransactionList(transactionList);
        tableModel.fireTableDataChanged();
    }
}
