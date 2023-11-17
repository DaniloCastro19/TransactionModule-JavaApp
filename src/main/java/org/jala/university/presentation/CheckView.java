package org.jala.university.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CheckView extends JFrame {
    private JTextField accountFrom;
    private JTextField beneficiaryName;
    private JTextField amountField;
    private JTextField reasonField;
    private JComboBox<Currency> currencyComboBox;
    private JButton generateCheckButton;
    private JButton printCheckButton;
    private JTextArea resultArea;
    private JLabel timeDateGenerationLabel;
    private boolean dateTimeVisible = false;
    UserModule userModule;
    CheckModule checkModule;
    public CheckView(CheckModule checkModule, UserModule userModule) {
        this.userModule = userModule;
        this.checkModule = checkModule;
        setTitle("Emitir Cheques");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        JLabel accountNumberLabel = new JLabel("N° de Cuenta de Origen:");
        accountFrom = new JTextField(20);

        JLabel nameLabel = new JLabel("Nombre del Beneficiario:");
        beneficiaryName = new JTextField(20);

        JLabel amountLabel = new JLabel("Monto:");
        amountField = new JTextField(20);

        JLabel reasonLabel = new JLabel("Motivo:");
        reasonField = new JTextField(20);

        JLabel typeCurrencyLabel = new JLabel("Tipo de Moneda:");

        currencyComboBox = createComboBox(Currency.values());

        generateCheckButton = new JButton("Generar Check");
        printCheckButton = new JButton("Imprimir Check");
        printCheckButton.setEnabled(false);

        timeDateGenerationLabel = new JLabel("Fecha y Hora de Generación:");
        timeDateGenerationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeDateGenerationLabel.setVisible(dateTimeVisible);
        addEvenListeners();
        resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);

        c.gridx = 0;
        c.gridy = 0;
        panel.add(accountNumberLabel, c);

        c.gridx = 1;
        panel.add(accountFrom, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(nameLabel, c);

        c.gridx = 1;
        panel.add(beneficiaryName, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(amountLabel, c);

        c.gridx = 1;
        panel.add(amountField, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(reasonLabel, c);

        c.gridx = 1;
        panel.add(reasonField, c);

        c.gridx = 0;
        c.gridy = 4;
        panel.add(typeCurrencyLabel, c);

        c.gridx = 1;
        panel.add(currencyComboBox, c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(generateCheckButton, c);

        c.gridx = 0;
        c.gridy = 6;
        panel.add(printCheckButton, c);

        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        panel.add(timeDateGenerationLabel, c);

        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        panel.add(resultArea, c);

        setupDocumentFilters(accountFrom, new IntegerValidator());
        setupDocumentFilters(beneficiaryName, new AlphaStringValidator());
        setupDocumentFilters(amountField, new DecimalValidator());
        setupDocumentFilters(reasonField, new StringValidator());
        getContentPane().add(panel);
        setLocationRelativeTo(null);
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
    private void addEvenListeners(){
        printCheckButton.addActionListener(e ->{
            StringBuilder result = new StringBuilder();
            result.append("N° de Cuenta de Origen: ").append(accountFrom.getText()).append("\n");
            result.append("Nombre del beneficiario: ").append(beneficiaryName.getText()).append("\n");
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
            List<BankUser> accountFromUserResults = userModule.findUsersByAccountNumber(accountFrom.getText());
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
                        // Enable the print button after clicking "Generate Check" and passing validations.
                        printCheckButton.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "Cheque rechazado, verifique el balance de la cuenta de origen.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
    private CheckStatus makeCheck(){
        List<BankUser> accountFromUserResults = userModule.findUsersByAccountNumber(accountFrom.getText());
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
        return check.getStatus();
    }
    private boolean areEmptyFields() {
        return !beneficiaryName.getText().isEmpty() &&
                !accountFrom.getText().isEmpty() &&
                !amountField.getText().isEmpty() &&
                !reasonField.getText().isEmpty();
    }
    private Date getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return date;
    }

    private <T> JComboBox<T> createComboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setSelectedItem(items[0]);
        return comboBox;
    }
    public void setResult(String result) {
        resultArea.setText(result);
    }

    private void cleanFields(){

        accountFrom.setDocument(new PlainDocument());
        beneficiaryName.setText("");
        amountField.setDocument(new PlainDocument());
        reasonField.setText("");
        resultArea.setText("");


    }


    public static void main(String[] args) {
        UserDAOMock userDaoMock = new UserDAOMock();
        AccountDAOMock accountDAOMock = new AccountDAOMock();
        TransactionDAOMock transactionDAOMock = new TransactionDAOMock();
        CheckDAOMock checkDAOMock = new CheckDAOMock();
        UserModule userModule = new UserModuleImpl(accountDAOMock, userDaoMock);
        CheckModule checkModule = new CheckModuleImpl(checkDAOMock);
        MockDataGenerator dataGenerator = new MockDataGenerator(userDaoMock, accountDAOMock,transactionDAOMock);
        dataGenerator.generateMockData();
        CheckView view = new CheckView(checkModule ,userModule);
        view.setVisible(true);
    }
}
