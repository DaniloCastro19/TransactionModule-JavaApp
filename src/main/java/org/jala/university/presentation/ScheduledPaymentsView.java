package org.jala.university.presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import org.jala.university.dao.AccountDAOMock;
import org.jala.university.dao.CheckDAOMock;
import org.jala.university.dao.MockDataGenerator;
import org.jala.university.dao.ScheduledTransferDAOMock;
import org.jala.university.dao.TransactionDAOMock;
import org.jala.university.dao.UserDAOMock;
import org.jala.university.domain.Frequency;
import org.jala.university.domain.ScheduledTransferModel;
import org.jala.university.domain.ScheduledTransferModule;
import org.jala.university.domain.ScheduledTransferModuleImpl;
import org.jala.university.domain.UserModule;
import org.jala.university.domain.UserModuleImpl;

public class ScheduledPaymentsView extends JFrame {
  private JTable paymentsTable;
  private JScrollPane paymentTableScrollPane;
  private JPanel searchPanel;
  private JButton searchButton;
  private JTextField searchInput;
  private UserModule userModule;

  private JComboBox<Frequency> frequencyJComboBox;

  private ScheduledTransferModule sheduledScheduledTransferModule;

  private AccountSelection accountSelection;


  public ScheduledPaymentsView(UserModule userModule, ScheduledTransferModule scheduledTransferModule) {
    this.userModule = userModule;
    this.sheduledScheduledTransferModule = scheduledTransferModule;
    frequencyJComboBox = createComboBox(Frequency.values());
    setTitle("Scheduled Payments");
    setSize(500, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    createTable();
    setLayout(new BorderLayout());
    addSearchPanel();
    addEventListeners();
    add(paymentTableScrollPane, BorderLayout.CENTER);
  }

  private void createTable() {
    ScheduledPaymentsTableModel sheduledPaymentsTableModel = new ScheduledPaymentsTableModel(new ArrayList<>());
    paymentsTable = new JTable(sheduledPaymentsTableModel);
    JTableHeader tableHeader = paymentsTable.getTableHeader();
    tableHeader.setReorderingAllowed(false);
    paymentTableScrollPane = new JScrollPane(paymentsTable);
    add(paymentTableScrollPane, BorderLayout.CENTER);
  }

  private void addSearchPanel() {
    searchPanel = new JPanel(new FlowLayout());
    searchInput = new JTextField(20);
    searchButton = new JButton("Buscar transacciones de la cuenta.");
    searchPanel.add(searchInput);
    searchPanel.add(searchButton);
    add(frequencyJComboBox, BorderLayout.NORTH);
    add(searchPanel, BorderLayout.SOUTH);
  }

  private void addEventListeners() {
    searchButton.addActionListener(e -> {
      String accountFromNumber = searchInput.getText();
      List<ScheduledTransferModel> sheduledTransferList = sheduledScheduledTransferModule.findByRootAccount(accountFromNumber);
      List<ScheduledTransferModel> result;
      Frequency frequency = (Frequency) frequencyJComboBox.getSelectedItem();

      if (!sheduledTransferList.isEmpty()) {
        result = frequency.findTransfers(sheduledScheduledTransferModule, accountFromNumber);
        updateTableModel(result);
      } else {
        JOptionPane.showMessageDialog(this, "Cuenta no encontrada.");
      }
    });
  }


  private void updateTableModel(List<ScheduledTransferModel> scheduledTransferTableModelList) {
    ScheduledPaymentsTableModel tableModel = (ScheduledPaymentsTableModel) paymentsTable.getModel();
    tableModel.setScheduledTransferList(scheduledTransferTableModelList);
    tableModel.fireTableDataChanged();
  }

  private <T> JComboBox<T> createComboBox(T[] items) {
    JComboBox<T> comboBox = new JComboBox<>(items);
    comboBox.setSelectedItem(items[0]);
    return comboBox;
  }

  public static void main(String[] args) {
    UserDAOMock userDAOMock = new UserDAOMock();
    AccountDAOMock accountDAOMock = new AccountDAOMock();
    TransactionDAOMock transactionDAOMock = new TransactionDAOMock();
    CheckDAOMock checkDAOMock = new CheckDAOMock();
    ScheduledTransferDAOMock scheduledTransferDAOMock = new ScheduledTransferDAOMock();
    MockDataGenerator dataGenerator = new MockDataGenerator(userDAOMock, accountDAOMock, transactionDAOMock, checkDAOMock, scheduledTransferDAOMock);
    ScheduledTransferModule scheduledTransferModule = new ScheduledTransferModuleImpl(scheduledTransferDAOMock);
    UserModule userModule = new UserModuleImpl(accountDAOMock, userDAOMock);
    dataGenerator.generateMockData();
    ScheduledPaymentsView view = new ScheduledPaymentsView(userModule, scheduledTransferModule);
    view.setVisible(true);
  }
}
