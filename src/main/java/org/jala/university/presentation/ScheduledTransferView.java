package org.jala.university.presentation;

import com.toedter.calendar.JDateChooser;
import org.jala.university.domain.*;
import org.jala.university.model.Account;
import org.jala.university.model.BankUser;
import org.jala.university.model.Currency;
import org.jala.university.model.TransactionStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * This class is the controller and view combined to be able to schedule scheduled transfers.
 * It is responsible for the interaction between the user and the model, allowing the programming of transfers,
 * account validation and other related operations.
 */
public class ScheduledTransferView extends JFrame {
    UserModule userModule;
    ScheduledTransferModule scheduledTransferModule;
    TransactionModule transactionModule;
    private JTextField amountTextField;
    private JTextField detailsTextField;
    private JTextField rootAccountTextField;
    private JTextField destinationAccountTextField;
    private final JComboBox<Currency> currencyComboBox = createComboBox(Currency.values());
    private JComboBox<String> numOccurrencesTextField;
    private JComboBox<String> frequencyComboBox;
    private JDateChooser dateChooser;
    private static final String LABEL_AMOUNT = "Monto:";
    private static final String ACCOUNT_ORIGIN_LABEL = "Cuenta de Origen:";
    private static final String ACCOUNT_DESTINATION_LABEL = "Cuenta de Destino:";
    private static final String CURRENCY_LABEL = "Tipo de Moneda:";
    private static final String DATE_LABEL = "Fecha de Transferencia:";
    private static final String LABEL_FREQUENCY = "Frecuencia de Transferencia:";
    private static final String LABEL_OCCURRENCE_NUM = "Número de Ocurrencias:";
    private static final String LABEL_DETAILS = "Detalles de la Transacción:";

    public ScheduledTransferView(ScheduledTransferModule scheduledTransferModule, UserModule userModule, TransactionModule transactionModule) {
        this.transactionModule = transactionModule;
        this.scheduledTransferModule = scheduledTransferModule;
        this.userModule = userModule;

        setTitle("Programar Transferencia");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = createTransferInfoPanel();
        JPanel buttonPanel = createButtonPanel();

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        dateChooser.setMinSelectableDate(new Date());
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private JPanel createTransferInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel(LABEL_AMOUNT), gbc);
        amountTextField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(amountTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(ACCOUNT_ORIGIN_LABEL), gbc);
        rootAccountTextField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(rootAccountTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(ACCOUNT_DESTINATION_LABEL), gbc);
        destinationAccountTextField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(destinationAccountTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(CURRENCY_LABEL), gbc);
        gbc.gridx = 1;
        panel.add(currencyComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(DATE_LABEL), gbc);
        dateChooser = new JDateChooser();
        gbc.gridx = 1;
        panel.add(dateChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(LABEL_FREQUENCY), gbc);
        String[] frecuenciaOptions = Arrays.stream(Frequency.values())
                .map(Frequency::getName)
                .toArray(String[]::new);
        frequencyComboBox = new JComboBox<>(frecuenciaOptions);

        gbc.gridx = 1;
        panel.add(frequencyComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(LABEL_OCCURRENCE_NUM), gbc);
        String[] numOcurrenciasOptions = {"1", "2", "3", "4", "5", "6"};
        numOccurrencesTextField = new JComboBox<>(numOcurrenciasOptions);
        gbc.gridx = 1;
        panel.add(numOccurrencesTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(LABEL_DETAILS), gbc);
        detailsTextField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(detailsTextField, gbc);

        return panel;
    }

    private <T> JComboBox<T> createComboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setSelectedItem(items[0]);
        return comboBox;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addButton("Agendar Transferencia", e -> scheduleTransfer(), buttonPanel);
        addButton("Cancelar Transferencia", e -> cancelTransfer(), buttonPanel);

        return buttonPanel;
    }

    public void scheduleTransfer() {
        List<BankUser> accountToUserResults = userModule.findUsersByAccountNumber(destinationAccountTextField.getText());
        List<BankUser> accountFromUserResults = userModule.findUsersByAccountNumber(rootAccountTextField.getText());

        if (validateInputFiles()) {
            Date dateTransfer = dateChooser.getDate();

            if (dateTransfer == null) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione una fecha de transferencia.");
                return;
            }

            if (accountToUserResults.isEmpty() || accountFromUserResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cuentas de origen y/o destino no válidas. Verifica los números de cuenta.");
            } else {
                String amountText = amountTextField.getText();

                try {
                    Long amount = Long.parseLong(amountText);

                    if (amount > 3500) {
                        JOptionPane.showMessageDialog(this, "El monto del pago programado no puede ser mayor a $3500.", "Alerta", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    TransactionStatus scheduledStatusTransfers = scheduledTransfer();

                    if (scheduledStatusTransfers.equals(TransactionStatus.FAILED)) {
                        JOptionPane.showMessageDialog(this, "Transacción fallida verifique los balances de la cuenta origen.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Transferencia agendada con éxito.");
                    }

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Pago agendado rechazado. Ingrese un monto válido (número entero).", "Alerta", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, llena todas las casillas.");
        }
    }

    private boolean validateInputFiles(){
        return !amountTextField.getText().isEmpty() &&
                !rootAccountTextField.getText().isEmpty() &&
                !destinationAccountTextField.getText().isEmpty() &&
                !detailsTextField.getText().isEmpty();
    }

    private void addButton(String label, ActionListener actionListener, JPanel panel) {
        JButton button = new JButton(label);
        button.addActionListener(actionListener);
        panel.add(button);
    }
    public TransactionStatus scheduledTransfer(){
        TransactionService transactionService = new TransactionService(transactionModule, userModule, scheduledTransferModule);
        List<BankUser> accountToUserResults = userModule.findUsersByAccountNumber(destinationAccountTextField.getText());
        List<BankUser> accountFromUserResults = userModule.findUsersByAccountNumber(rootAccountTextField.getText());
        Currency currency = (Currency) currencyComboBox.getSelectedItem();
        Account accountTo= accountToUserResults.get(0).getAccount();
        Account accountFrom = accountFromUserResults.get(0).getAccount();
        ScheduledTransferModel scheduledTransferModel = ScheduledTransferModel.builder()
                .id(UUID.randomUUID())
                .amount(Long.valueOf(amountTextField.getText()))
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .currency(currency)
                .date(dateChooser.getDate())
                .status(TransactionStatus.PENDING)
                .frequency(getFrequency())
                .numOccurrences(Integer.parseInt((String) numOccurrencesTextField.getSelectedItem()))
                .details(detailsTextField.getText())
                .build();

        List<Date> scheduledDates = scheduledTransferModel.calculateDatesTransfer();
        ScheduledTransferAlert.showScheduledTransferAlert(scheduledDates, scheduledTransferModel);
        TransactionStatus transactionStatus = transactionService.scheduledTransfer(scheduledTransferModel);
        return transactionStatus;
    }

    public Frequency getFrequency() {
        String frequencyName = frequencyComboBox.getSelectedItem().toString();
        Frequency frequency = Arrays.stream(Frequency.values())
                .filter(f -> f.getName().equals(frequencyName))
                .findFirst()
                .orElse(null);
        return frequency;
    }

    public void cancelTransfer() {
        JOptionPane.showMessageDialog(this, "Transferencia cancelada");
        dispose();
    }
}