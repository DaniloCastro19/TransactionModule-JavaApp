package org.jala.university.presentation;

import org.jala.university.Utils.Validator.AlphaStringValidator;
import org.jala.university.Utils.Validator.DecimalValidator;
import org.jala.university.Utils.Validator.IntegerValidator;
import org.jala.university.Utils.Validator.StringValidator;
import org.jala.university.Utils.Validator.Validator;
import org.jala.university.dao.AccountDAOMock;
import org.jala.university.dao.CheckDAOMock;
import org.jala.university.dao.MockDataGenerator;
import org.jala.university.dao.TransactionDAOMock;
import org.jala.university.dao.UserDAOMock;
import org.jala.university.domain.CheckModule;
import org.jala.university.domain.CheckModuleImpl;
import org.jala.university.domain.UserModule;
import org.jala.university.domain.UserModuleImpl;
import org.jala.university.model.Account;
import org.jala.university.model.BankUser;
import org.jala.university.model.Check;
import org.jala.university.model.CheckStatus;
import org.jala.university.model.Currency;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CheckView extends JFrame {
    private CheckModule checkModule;
    private UserModule userModule;

    private JTextField accountNumberTextField;
    private JTextField beneficiaryNameTextField;
    private JTextField amountField;
    private JTextField reasonField;
    private JComboBox<Currency> currencyComboBox;
    private JButton generateCheckButton;
    private JButton printCheckButton;
    private JButton selectAccountButton;
    private JTextArea resultArea;
    private JLabel timeDateGenerationLabel;
    private boolean dateTimeVisible = false;
    public CheckView(CheckModule checkModule, UserModule userModule) {
        this.checkModule = checkModule;
        this.userModule = userModule;
        initializeUI();
        setTitle("Emitir Cheques");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initializeUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        accountNumberTextField = new JTextField(20);
        accountNumberTextField.setEditable(false);
        beneficiaryNameTextField = new JTextField(20);
        beneficiaryNameTextField.setEditable(false);
        amountField = new JTextField(20);
        reasonField = new JTextField(20);
        currencyComboBox = new JComboBox<>(Currency.values());
        generateCheckButton = new JButton("Generar Cheque");
        printCheckButton = new JButton("Imprimir Cheque");
        selectAccountButton = new JButton("Agregar Cuenta de Origen");
        resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);
        timeDateGenerationLabel = new JLabel("Fecha y Hora de Generación:");
        timeDateGenerationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeDateGenerationLabel.setVisible(dateTimeVisible);
        addComponentToPanel(panel, c, new JLabel("Número de Cuenta:"), 0, 0);
        addComponentToPanel(panel, c, accountNumberTextField, 1, 0);
        addComponentToPanel(panel, c, new JLabel("Nombre del Beneficiario:"), 0, 1);
        addComponentToPanel(panel, c, beneficiaryNameTextField, 1, 1);
        addComponentToPanel(panel, c, new JLabel("Monto:"), 0, 2);
        addComponentToPanel(panel, c, amountField, 1, 2);
        addComponentToPanel(panel, c, new JLabel("Motivo:"), 0, 3);
        addComponentToPanel(panel, c, reasonField, 1, 3);
        addComponentToPanel(panel, c, new JLabel("Tipo de Moneda:"), 0, 4);
        addComponentToPanel(panel, c, currencyComboBox, 1, 4);
        addComponentToPanel(panel, c, selectAccountButton, 0, 5);
        addComponentToPanel(panel, c, generateCheckButton, 0, 6);
        addComponentToPanel(panel, c, printCheckButton, 0, 7);
        addComponentToPanel(panel, c, timeDateGenerationLabel, 0, 8);
        addComponentToPanel(panel, c, new JScrollPane(resultArea), 0, 9);
        setupDocumentFilters(accountNumberTextField, new IntegerValidator());
        setupDocumentFilters(amountField, new DecimalValidator());
        setupDocumentFilters(reasonField, new StringValidator());
        addEventListeners();
        getContentPane().add(panel);
    }
    private void addComponentToPanel(JPanel panel, GridBagConstraints c, Component component, int x, int y) {
        c.gridx = x;
        c.gridy = y;
        panel.add(component, c);
    }

    private void setupDocumentFilters(JTextField textField, Validator validator) {
        Document doc = textField.getDocument();
        if (doc instanceof PlainDocument) {
            ((PlainDocument) doc).setDocumentFilter(new DocumentFilter() {
                @Override
                public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                    if (validator.validate(string)) {
                        super.insertString(fb, offset, string, attr);
                    }
                }

                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                    if (validator.validate(text)) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            });
        }
    }
    private void addEventListeners(){
        selectAccountButton.addActionListener(e -> {
            AccountSelection accountSelection = new AccountSelection(userModule, accountData -> {
                accountNumberTextField.setText(accountData[0]);
                beneficiaryNameTextField.setText(accountData[1] + " " + accountData[2]);
            });
            accountSelection.setVisible(true);
        });
        printCheckButton.addActionListener(e ->{
            StringBuilder result = new StringBuilder();
            result.append("N° de Cuenta de Origen: ").append(accountNumberTextField.getText()).append("\n");
            result.append("Nombre del beneficiario: ").append(beneficiaryNameTextField.getText()).append("\n");
            result.append("Monto: ").append(amountField.getText()).append("\n");
            result.append("Motivo: ").append(reasonField.getText()).append("\n");
            result.append("Tipo de Moneda: ").append(currencyComboBox.getSelectedItem()).append("\n");
            this.setResult(result.toString());
            JOptionPane.showMessageDialog(this, result.toString(), "Imprimiendo Cheque", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(this, "Estado de cheque." + result
                    , "Cheque impreso - Completado", JOptionPane.INFORMATION_MESSAGE);
            cleanFields();
            printCheckButton.setEnabled(false);
        });
        generateCheckButton.addActionListener(e -> {
            List<BankUser> accountFromUserResults = userModule.findUsersByAccountNumber(accountNumberTextField.getText());
            if (!areEmptyFields()) {
                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (accountFromUserResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cuenta inexistente detectada. Por favor ingrese cuentas existentes.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de generar el cheque?", "Confirmación", JOptionPane.OK_CANCEL_OPTION);
                if (confirm == JOptionPane.OK_OPTION) {
                    CheckStatus checkStatus = makeCheck();
                    if (checkStatus.equals(CheckStatus.ACTIVE)) {
                        JOptionPane.showMessageDialog(this, "Cheque emitido con éxito.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dateTimeVisible = true;
                        timeDateGenerationLabel.setVisible(dateTimeVisible);
                        printCheckButton.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "Cheque rechazado, verifique el balance de la cuenta de origen.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
    private CheckStatus makeCheck(){
        List<BankUser> accountFromUserResults = userModule.findUsersByAccountNumber(accountNumberTextField.getText());
        Account accountFrom = accountFromUserResults.get(0).getAccount();
        Check check = Check.builder()
                .id(UUID.randomUUID())
                .date(getCurrentDateTime())
                .amount(Long.valueOf(amountField.getText()))
                .reason(reasonField.getText())
                .currency((Currency) currencyComboBox.getSelectedItem())
                .accountFrom(accountFrom)
                .status(CheckStatus.ACTIVE)
                .build();
        checkModule.createCheck(check);
        return check.getStatus();
    }
    private boolean areEmptyFields() {
        return  !amountField.getText().isEmpty() &&
                !reasonField.getText().isEmpty();
    }
    private Date getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return date;
    }

    public void setResult(String result) {
        resultArea.setText(result);
    }

    private void cleanFields(){
        accountNumberTextField.setDocument(new PlainDocument());
        beneficiaryNameTextField.setText("");
        amountField.setDocument(new PlainDocument());
        reasonField.setText("");
        resultArea.setText("");
    }

}
