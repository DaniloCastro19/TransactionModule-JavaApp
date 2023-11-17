package org.jala.university.presentation;


import lombok.Getter;
import lombok.Setter;
import org.jala.university.dao.AccountDAOMock;
import org.jala.university.dao.TransactionDAOMock;
import org.jala.university.dao.UserDAOMock;
import org.jala.university.dao.MockDataGenerator;
import org.jala.university.domain.HistoryFilters;
import org.jala.university.domain.TransactionModule;
import org.jala.university.domain.TransactionModuleImpl;
import org.jala.university.model.BankUser;
import org.jala.university.model.Transaction;
import org.jala.university.domain.HistoryReportGenerator;
import  org.jala.university.dao.TransactionDAO;
import org.jala.university.model.TransactionType;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import  javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class HistoryView extends JFrame {
    private HistoryReportGenerator historyReportGenerator;
    private JComboBox<String> filterTypeComboBox;
    private JComboBox<String> transactionTypeFilterComboBox;
    private JComboBox amountInput;
    private JTextField accountNumberInput, nameInput;
    private JPanel dynamicFilterPanel;
    private Map<String, HistoryFilters> historyFiltersMap = Map.of(
            "Filtrar por Número de cuenta", HistoryFilters.ACCOUNT_NUMBER,
            "Filtrar por Nombre y/o Apellidos",   HistoryFilters.ACCOUNT_NAME,
            "Filtrar por Tipo de Transacción", HistoryFilters.TRANSACTION_TYPE,
            "Filtrar por Monto", HistoryFilters.TRANSACTION_AMOUNT,
            "Filtrar por Fechas", HistoryFilters.TRANSACTION_DATE
    );
    private JComboBox<String> transactionTypeComboBox;
    private JComboBox<String> dateRangeComboBox;
    private JComboBox<Date> startDateComboBox;
    private JComboBox<Date> endDateComboBox;
    private JLabel titleLabel;
    private JPanel searchPanel;
    private JTextField searchInput;
    private JButton searchButton;
    private JTable historyTable;

    private TransactionModule transactionModule;

    public HistoryView(TransactionModule transactionModule) {
        this.historyReportGenerator = new HistoryReportGenerator(transactionModule);
        this.transactionModule = transactionModule;
        initializeUI();
        setTitle("Historial de Transacciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 550);
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
        filterTypeComboBox = new JComboBox<>(new String[]{
                "Filtrar por Número de cuenta",
                "Filtrar por Nombre y/o Apellidos",
                "Filtrar por Tipo de Transacción",
                "Filtrar por Monto",
                "Filtrar por Fechas"
        });
        filterTypeComboBox.addActionListener(this::onFilterTypeChanged);
        dynamicFilterPanel = new JPanel(new CardLayout());
        setupDynamicFilters();
        searchPanel.add(filterTypeComboBox);
        searchPanel.add(dynamicFilterPanel);
        searchButton = new JButton("Buscar");
        searchButton.addActionListener(this::performSearch);
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
    private void setupDynamicFilters() {
        JPanel accountNumberPanel = new JPanel();
        accountNumberInput = new JTextField(20);
        accountNumberPanel.add(accountNumberInput);
        JPanel namePanel = new JPanel();
        nameInput = new JTextField(20);
        namePanel.add(nameInput);
        JPanel transactionTypePanel = new JPanel();
        transactionTypeFilterComboBox = new JComboBox<>(new String[]{"Transfer", "Deposit", "Withdrawal"});
        transactionTypePanel.add(transactionTypeFilterComboBox);
        JPanel amountPanel = new JPanel();
        amountInput = new JComboBox<>(new String[]{"Maximo", "Minimo"});
        amountPanel.add(amountInput);
        JPanel dateFilterPanel = new JPanel();
        dateRangeComboBox = new JComboBox<>(new String[]{"1 semana", "1 mes", "Rango de fechas"});
        Date start = new GregorianCalendar(2022, Calendar.OCTOBER, 1).getTime();
        Date end = new Date();
        startDateComboBox = new JComboBox<Date>();
        endDateComboBox = new JComboBox<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while (!calendar.getTime().after(end)) {
            startDateComboBox.addItem(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        startDateComboBox.addActionListener(e -> updateEndDateComboBox((Date) startDateComboBox.getSelectedItem()));
        dateFilterPanel.add(dateRangeComboBox);
        dateFilterPanel.add(startDateComboBox);
        dateFilterPanel.add(endDateComboBox);
        dynamicFilterPanel.add(accountNumberPanel, "Filtrar por Número de cuenta");
        dynamicFilterPanel.add(namePanel, "Filtrar por Nombre y/o Apellidos");
        dynamicFilterPanel.add(transactionTypePanel, "Filtrar por Tipo de Transacción");
        dynamicFilterPanel.add(amountPanel, "Filtrar por Monto");
        dynamicFilterPanel.add(dateFilterPanel, "Filtrar por Fechas");
        dateRangeComboBox.addActionListener(e -> onDateRangeChanged(dateRangeComboBox));
    }
    private void onFilterTypeChanged(ActionEvent e) {
        CardLayout cl = (CardLayout)(dynamicFilterPanel.getLayout());
        String selectedFilter = (String) filterTypeComboBox.getSelectedItem();
        cl.show(dynamicFilterPanel, selectedFilter);
        clearTextFields();
    }

    private void clearTextFields() {
        if (accountNumberInput != null) {
            accountNumberInput.setText("");
        }
        if (nameInput != null) {
            nameInput.setText("");
        }

    }
    private void updateEndDateComboBox(Date startDate) {
        endDateComboBox.removeAllItems();

        if (startDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            Date endDateLimit = new Date();
            for (int i = 0; i < 30; i++) {
                if (calendar.getTime().after(endDateLimit)) {
                    break;
                }
                endDateComboBox.addItem(calendar.getTime());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
    }

    private void onDateRangeChanged(JComboBox<String> dateRangeComboBox) {
        String selectedOption = (String) dateRangeComboBox.getSelectedItem();
        if ("Rango de fechas".equals(selectedOption)) {
            startDateComboBox.setVisible(true);
            endDateComboBox.setVisible(true);
        } else {
            startDateComboBox.setVisible(false);
            endDateComboBox.setVisible(false);
        }
    }
    private void addTableScrollPane() {
        HistoryTableModel historyTableModel = new HistoryTableModel(new ArrayList<>());
        historyTable = new JTable(historyTableModel);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    private void performSearch(ActionEvent e) {
        String selectedFilter = (String) filterTypeComboBox.getSelectedItem();
        HistoryFilters filterEnum = historyFiltersMap.get(selectedFilter);
        Map<String, Object> parameters = new HashMap<>();
        if (filterEnum == HistoryFilters.TRANSACTION_DATE) {
            JComponent[] component = filterEnum.getComponent(this);
            String value = filterEnum.getValue(component);
            Date startDate = (Date) startDateComboBox.getSelectedItem();
            Date endDate = (Date) endDateComboBox.getSelectedItem();
            parameters.put(filterEnum.toString(), value);
            parameters.put("startDate", startDate);
            parameters.put("endDate", endDate);
        } else {
            JComponent[] component = filterEnum.getComponent(this);
            String value = filterEnum.getValue(component);
            parameters.put(filterEnum.toString(), value);
        }
        List<Transaction> transactions = filterEnum.getTransactions(transactionModule, parameters);
        updateTableModel(transactions);
    }

    private void updateTableModel(List<Transaction> transactionList) {
        HistoryTableModel tableModel = (HistoryTableModel) historyTable.getModel();
        tableModel.setTransactionList(transactionList);
        tableModel.fireTableDataChanged();
    }


}
