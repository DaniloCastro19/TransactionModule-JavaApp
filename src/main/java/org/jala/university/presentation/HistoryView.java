package org.jala.university.presentation;


import lombok.Getter;
import lombok.Setter;
import org.jala.university.domain.HistoryFilters;
import org.jala.university.domain.HistoryViewInterface;
import org.jala.university.domain.TransactionModule;
import org.jala.university.model.BankUser;
import org.jala.university.model.Transaction;
import org.jala.university.domain.HistoryReportGenerator;

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
import  javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.util.*;

@Getter
@Setter
public class HistoryView extends JFrame implements HistoryViewInterface {
    private HistoryReportGenerator historyReportGenerator;
    private JComboBox<String> filterTypeComboBox;
    private JComboBox<String> transactionTypeFilterComboBox;
    private JComboBox amountInput;
    private JTextField accountNumberInput;
    private JTextField nameInput;
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
    private List<Transaction> filteredTransactions;

    public HistoryView(TransactionModule transactionModule) {
        this.historyReportGenerator = new HistoryReportGenerator(transactionModule);
        this.transactionModule = transactionModule;
        initializeUI();
        setTitle("Historial de Transacciones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 550);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    @Override
    public void initializeUI() {
        setLayout(new BorderLayout());
        addTitleLabel();
        addSearchPanel();
        addTableScrollPane();
    }

    @Override
    public void addTitleLabel() {
        titleLabel = new JLabel("Historial de Transacciones");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);
    }

    @Override
    public void addSearchPanel() {
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

    private boolean areMultipleAccounts(List<Transaction> transactions) {
        Set<String> uniqueAccountNumbers = new HashSet<>();
        for (Transaction transaction : transactions) {
            uniqueAccountNumbers.add(transaction.getAccountFrom().getAccountNumber());
            uniqueAccountNumbers.add(transaction.getAccountTo().getAccountNumber());
        }
        return uniqueAccountNumbers.size() > 1;
    }

    private boolean hasSelection() {
        return historyTable.getSelectedRow() != -1;
    }

    private boolean hasResults() {
        return historyTable.getModel().getRowCount() > 0;
    }
    private void generateReport(ActionEvent e) {
        if (!hasResults()) {
            JOptionPane.showMessageDialog(this, "No hay resultados para generar un reporte", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedRowIndex = historyTable.getSelectedRow();
        if (selectedRowIndex == -1 && areMultipleAccounts(filteredTransactions)) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una transacción para generar el reporte", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String accountNumber;
        if (areMultipleAccounts(filteredTransactions)) {
            accountNumber = (String) historyTable.getValueAt(selectedRowIndex, 2);
        } else {
            accountNumber = filteredTransactions.get(0).getAccountFrom().getAccountNumber();
        }

        BankUser user = transactionModule.getUserInfoForAccountNumber(accountNumber);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un usuario con ese número de cuenta", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String report = historyReportGenerator.generateReport(accountNumber);
        TransactionReportView reportView = new TransactionReportView(user, report);
        reportView.setVisible(true);
    }
    @Override
    public void setupDynamicFilters() {
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

    @Override
    public void clearTextFields() {
        if (accountNumberInput != null) {
            accountNumberInput.setText("");
        }
        if (nameInput != null) {
            nameInput.setText("");
        }

    }
    public void updateEndDateComboBox(Date startDate) {
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
    public void addTableScrollPane() {
        HistoryTableModel historyTableModel = new HistoryTableModel(new ArrayList<>());
        historyTable = new JTable(historyTableModel);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    @Override
    public void performSearch(ActionEvent e) {
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
        try {
            JComponent[] component = filterEnum.getComponent(this);
            String value = filterEnum.getValue(component);
            if (value == null || value.isEmpty()) {
                throw new IllegalArgumentException("Campo vacío");
            }
            parameters.put(filterEnum.toString(), value);
            filteredTransactions = filterEnum.getTransactions(transactionModule, parameters);
            updateTableModel(filteredTransactions);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Campo Vacio. Ingrese un valor para la búsqueda", "AVISO", JOptionPane.WARNING_MESSAGE);
        }
    }
    @Override
    public void updateTableModel(List<?> transactionList) {
        HistoryTableModel tableModel = (HistoryTableModel) historyTable.getModel();
        tableModel.setTransactionList((List<Transaction>) transactionList);
        tableModel.fireTableDataChanged();
    }
}
