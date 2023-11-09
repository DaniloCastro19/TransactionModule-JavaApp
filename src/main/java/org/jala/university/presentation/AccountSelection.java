package org.jala.university.presentation;

import org.jala.university.domain.TransactionModule;
import org.jala.university.domain.UserModule;
import org.jala.university.model.BankUser;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

    public class AccountSelection extends JFrame {
        private JLabel titleLabel;
        private JPanel searchPanel;
        private JComboBox<String> searchOption;
        private JTextField searchInput;
        private JButton searchButton;
        private JTable table;
        private UserModule module;

        public AccountSelection(UserModule module) {
            this.module = module;

            initializeUI();
            setTitle("Cuentas");
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

        private void addTableScrollPane() {
            BankUserTableModel tableModel = new BankUserTableModel(new ArrayList<>());
            table = new JTable(tableModel);
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

