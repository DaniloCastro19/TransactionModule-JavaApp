package org.jala.university.presentation;

import lombok.Getter;
import lombok.Setter;
import org.jala.university.domain.CheckModule;
import org.jala.university.domain.HistoryFilters;
import org.jala.university.domain.HistoryViewInterface;
import org.jala.university.domain.TransactionModule;
import org.jala.university.model.Check;
import org.jala.university.model.CheckStatus;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CheckHistoryView extends JFrame implements HistoryViewInterface {
    private JComboBox<String> filterTypeComboBox;
    private JComboBox amountInput;
    private JTextField accountNumberInput, nameInput;
    private JPanel dynamicFilterPanel;
    private Map<String, HistoryFilters> historyFiltersMap = Map.of(
            "Filtrar por Número de cuenta", HistoryFilters.ACCOUNT_NUMBER,
            "Filtrar por Nombre y/o Apellidos",   HistoryFilters.ACCOUNT_NAME,
            "Filtrar por Monto", HistoryFilters.TRANSACTION_AMOUNT,
            "Filtrar por Fechas", HistoryFilters.TRANSACTION_DATE
    );
    private JComboBox<String> dateRangeComboBox;
    private JComboBox<Date> startDateComboBox;
    private JComboBox<Date> endDateComboBox;
    private JLabel titleLabel;
    private JPanel searchPanel;
    private JTextField searchInput;
    private JButton searchButton;
    private JTable checkTable;
    private TransactionModule transactionModule;
    private CheckModule checkModule;

    public CheckHistoryView(CheckModule checkModule, TransactionModule transactionModule) {
        this.transactionModule = transactionModule;
        this.checkModule = checkModule;
        initializeUI();
        setTitle("Historial de Cheques");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 550);
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
        titleLabel = new JLabel("Historial de Cheques");
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
    @Override
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

    @Override
    public JComponent getTransactionTypeComboBox() {
        return null;
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
    @Override
    public void addTableScrollPane() {
        CheckTableModel checkTableModel = new CheckTableModel(new ArrayList<>());
        checkTable = new JTable(checkTableModel);
        checkTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    openWithdrawalCheckView();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(checkTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void openWithdrawalCheckView() {
        int selectedRow = checkTable.getSelectedRow();
        if (selectedRow >= 0) {
            CheckTableModel model = (CheckTableModel) checkTable.getModel();
            Check selectedCheck = model.getCheckList().get(selectedRow);
            if (selectedCheck.getStatus() == CheckStatus.ACTIVE){
                new WithdrawalCheckView(selectedCheck, transactionModule, checkModule).setVisible(true);
            } else{
                JOptionPane.showMessageDialog(this, "El cheque no se encuentra disponible, estado del cheque: " + selectedCheck.getStatus().toString());
            }
        }
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
        List<Check> checks = filterEnum.getChecks(checkModule, parameters);
        updateTableModel(checks);
    }
    @Override
    public void updateTableModel(List<?> checks) {
        CheckTableModel tableModel = (CheckTableModel) checkTable.getModel();
        tableModel.setCheckList((List<Check>) checks);
        tableModel.fireTableDataChanged();
    }

}
