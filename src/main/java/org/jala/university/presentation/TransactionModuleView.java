package org.jala.university.presentation;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import org.jala.university.domain.AccountModule;
import org.jala.university.domain.CheckModule;
import org.jala.university.domain.ScheduledTransferModule;
import org.jala.university.domain.TransactionModule;
import org.jala.university.domain.UserModule;

public class TransactionModuleView extends JFrame {
    private final CheckModule checkModule;
    private final AccountModule accountModule;
    private final ScheduledTransferModule scheduledTransferModule;
    private final UserModule userModule;
    private final TransactionModule transactionModule;
    private JLabel titleLabel;
    private JButton newTransactionButton;
    private JButton checkButton;
    private JButton transactionHistoryButton;
    private JButton checkHistoryButton;
    private JButton ScheduledTransactionButton;

    public TransactionModuleView(AccountModule accountModule, UserModule userModule, TransactionModule transactionModule, CheckModule checkModule, ScheduledTransferModule scheduledTransferModule) {
        this.accountModule = accountModule;
        this.userModule = userModule;
        this.transactionModule = transactionModule;
        this.checkModule = checkModule;
        this.scheduledTransferModule = scheduledTransferModule;
        initializeUI();
        setTitle("Vista del Modulo de Transacciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        addTitleLabel();
        addButtonsPanel();
        configureButtonActions();
    }

    private void addButtonsPanel() {
        JPanel mainButtonsPanel = new JPanel(new GridLayout(2, 1));
        JPanel topButtonsPanel = new JPanel();
        topButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 100));
        newTransactionButton = new JButton("Nueva Transacción");
        transactionHistoryButton = new JButton("Historial de Transacciones");
        newTransactionButton.setPreferredSize(new Dimension(300, 95));
        transactionHistoryButton.setPreferredSize(new Dimension(300, 95));
        topButtonsPanel.add(newTransactionButton);
        topButtonsPanel.add(transactionHistoryButton);
        JPanel bottomButtonPanel = new JPanel();
        bottomButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        checkButton = new JButton("Realizar Creación de Cheque");
        checkHistoryButton = new JButton("Cobrar cheque");
        checkButton.setPreferredSize(new Dimension(300, 95));
        checkHistoryButton.setPreferredSize(new Dimension(300, 95));
        ScheduledTransactionButton = new JButton("Agendar Pago Programado");
        ScheduledTransactionButton.setPreferredSize(new Dimension(300, 95));
        topButtonsPanel.add(ScheduledTransactionButton);
        bottomButtonPanel.add(checkButton);
        bottomButtonPanel.add(checkHistoryButton);
        mainButtonsPanel.add(topButtonsPanel);
        mainButtonsPanel.add(bottomButtonPanel);
        add(mainButtonsPanel, BorderLayout.CENTER);
    }

    private void addTitleLabel() {
        titleLabel = new JLabel("Vista del Modulo de transacciones");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

    }

    private void configureButtonActions() {
        newTransactionButton.addActionListener(e -> performNewTransaction());
        transactionHistoryButton.addActionListener(e -> showTransactionHistory());
        checkButton.addActionListener(e -> showCheckView());
        checkHistoryButton.addActionListener(e -> showHistoryCheckView());
        ScheduledTransactionButton.addActionListener(e -> performScheduledTransaction());
    }
    private void performScheduledTransaction() {
        ScheduledTransferView scheduledTransferView = new ScheduledTransferView(scheduledTransferModule, userModule, transactionModule);
        initForm(scheduledTransferView);
    }

    private void showHistoryCheckView() {
        CheckHistoryView checkHistoryView = new CheckHistoryView(checkModule, transactionModule);
        initForm(checkHistoryView);
    }

    private void performNewTransaction() {
        TransactionView transactionView = new TransactionView(transactionModule, userModule,scheduledTransferModule);
        initForm(transactionView);
    }

    private void showTransactionHistory() {
        HistoryView historyView = new HistoryView(transactionModule);
        initForm(historyView);

    }
    private void showCheckView(){
        CheckView checkView = new CheckView(checkModule, userModule);
        initForm(checkView);
    }

    private void initForm(JFrame view) {
        view.setVisible(true);
    }
}
