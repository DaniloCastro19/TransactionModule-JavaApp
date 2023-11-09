package org.jala.university.presentation;


import org.jala.university.dao.AccountDAOMock;
import org.jala.university.dao.TransactionDAOMock;
import org.jala.university.dao.UserDAOMock;
import org.jala.university.dao.MockDataGenerator;
import org.jala.university.domain.*;
import org.jala.university.model.*;

import javax.swing.*;
import java.awt.*;
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

    public HistoryView(TransactionModule transactionModule) {
        this.transactionModule = transactionModule;
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

    public static void main(String[] args) {
        UserDAOMock userDaoMock = new UserDAOMock();
        AccountDAOMock accountDaoMock = new AccountDAOMock();
        TransactionDAOMock transactionDaoMock = new TransactionDAOMock();

        MockDataGenerator generator = new MockDataGenerator(userDaoMock, accountDaoMock, transactionDaoMock);
        generator.generateMockData();

        TransactionModule transactionModule = new TransactionModuleImpl(transactionDaoMock);
        HistoryView view = new HistoryView(transactionModule);
        view.setVisible(true);
    }
}
