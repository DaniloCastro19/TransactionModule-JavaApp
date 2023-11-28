package org.jala.university.presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import org.jala.university.domain.UserModule;
import org.jala.university.model.BankUser;

public class AccountSelection extends JFrame {
  private JLabel titleLabel;
  private JPanel searchPanel;
  private JComboBox<String> searchOption;
  private JTextField searchInput;
  private JButton searchButton;
  private JTable table;
  private UserModule module;
  private AccountSelectionDataSender accountSelectionListener;
  private final int USER_INFO_ARR_LENGHT = 3;
  String [] searchedUserInfo;

  public AccountSelection(UserModule module , AccountSelectionDataSender accountSelectionListener) {
    this.module = module;
    this.accountSelectionListener = accountSelectionListener;
    this.searchedUserInfo = new String[USER_INFO_ARR_LENGHT];
    initializeUI();
    setTitle("Cuentas");
    setSize(800, 550);
    setLocationRelativeTo(null);
    setVisible(true);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        accountSelectionListener.onClosedAccountSelecctionView(searchedUserInfo);
      }
    });

  }

  private void initializeUI() {
    setLayout(new BorderLayout());
    addTitleLabel();
    addSearchPanel();
    addTableScrollPane();
    JTableHeader tableHeader = table.getTableHeader();
    tableHeader.setReorderingAllowed(false);
  }

  private void addTableScrollPane() {
    BankUserTableModel tableModel = new BankUserTableModel(new ArrayList<>());
    table = new JTable(tableModel);
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int row = table.rowAtPoint(e.getPoint());
        int columnCount = table.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
          Object cellValue = table.getValueAt(row, i);
          searchedUserInfo[i] = String.valueOf(cellValue);
        }
        dispose();
      }
    });
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);
  }

  private void addTitleLabel() {
    titleLabel = new JLabel("Cuentas");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(titleLabel, BorderLayout.NORTH);
  }

  private void addSearchPanel() {
    searchPanel = new JPanel(new FlowLayout());
    searchOption = new JComboBox<>(new String[]{"Nombre(s) o apellido(s)", "Numero de cuenta"});
    searchInput = new JTextField(20);
    searchButton = new JButton("Buscar");
    searchButton.addActionListener(this::performSearch);
    searchPanel.add(searchOption);
    searchPanel.add(searchInput);
    searchPanel.add(searchButton);
    add(searchPanel, BorderLayout.SOUTH);
  }


  private void performSearch(ActionEvent e) {
    String searchTerm = searchInput.getText();
    List<BankUser> userList;

    if (searchOption.getSelectedIndex() == 0) {
      userList = module.findUsersByNameOrLastName(searchTerm);
    } else {
      userList = module.findUsersByAccountNumber(searchTerm);
    }

    updateTableModel(userList);
  }

  private void updateTableModel(List<BankUser> userList) {
    BankUserTableModel tableModel = (BankUserTableModel) table.getModel();
    tableModel.setUserList(userList);
    tableModel.fireTableDataChanged();
  }
}

