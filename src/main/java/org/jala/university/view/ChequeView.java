package org.jala.university.view;
import org.jala.university.Utils.Validator.InputValidator;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

public class ChequeView extends JFrame {
    private JTextField nameFiled;
    private JTextField amountField;
    private JTextField reasonField;
    private JComboBox<String> currencyComboBox;
    private JButton generateCheckButton;
    private JButton printCheckButton;
    private JTextArea resultArea;
    private JLabel timeDateGenerationLabel;
    private boolean dateTimeVisible = false;

    public ChequeView() {
        setTitle("Emitir Cheques");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        JLabel nombreLabel = new JLabel("Nombre:");
        nameFiled = new JTextField(20);

        JLabel montoLabel = new JLabel("Monto:");
        amountField = new JTextField(20);

        JLabel motivoLabel = new JLabel("Motivo:");
        reasonField = new JTextField(20);

        JLabel tipoMonedaLabel = new JLabel("Tipo de Moneda:");
        String[] monedas = {"Dollar", "Euro", "Bolivianos"};
        currencyComboBox = new JComboBox<>(monedas);

        generateCheckButton = new JButton("Generar Cheque");
        printCheckButton = new JButton("Imprimir Cheque");

        timeDateGenerationLabel = new JLabel("Fecha y Hora de Generación:");
        timeDateGenerationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeDateGenerationLabel.setVisible(dateTimeVisible);

        resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);

        c.gridx = 0;
        c.gridy = 0;
        panel.add(nombreLabel, c);

        c.gridx = 1;
        panel.add(nameFiled, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(montoLabel, c);

        c.gridx = 1;
        panel.add(amountField, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(motivoLabel, c);

        c.gridx = 1;
        panel.add(reasonField, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(tipoMonedaLabel, c);

        c.gridx = 1;
        panel.add(currencyComboBox, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(generateCheckButton, c);

        c.gridx = 0;
        c.gridy = 5;
        panel.add(printCheckButton, c);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        panel.add(timeDateGenerationLabel, c);

        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        panel.add(resultArea, c);

        setupDocumentFilters(nameFiled, "alphaWithSpaces");
        setupDocumentFilters(amountField, "numericOrDecimal");
        setupDocumentFilters(reasonField, "alphaWithSpaces");
        getContentPane().add(panel);

        setLocationRelativeTo(null);
    }

    public String getName() {
        return nameFiled.getText();
    }

    public double getAmount() {
        return Double.parseDouble(amountField.getText());
    }

    public String getReason() {
        return reasonField.getText();
    }

    public String getCurrency() {
        return currencyComboBox.getSelectedItem().toString();
    }

    public void generateCheckListener(ActionListener listener) {
        generateCheckButton.addActionListener(e -> {
            dateTimeVisible = true;
            timeDateGenerationLabel.setVisible(dateTimeVisible);
            listener.actionPerformed(e);
        });
    }

    private boolean areEmptyFields(){
        return !nameFiled.getText().isEmpty() &&
                !amountField.getText().isEmpty() &&
                !reasonField.getText().isEmpty();

    }

    private boolean areValidInputs(String input, String validationType){
        switch (validationType) {
            case "numeric":
                return InputValidator.isNumeric(input);
            case "numericOrDecimal":
                return InputValidator.isNumericOrDecimal(input);
            case "alphaWithSpaces":
                return InputValidator.isAlphaWithSpaces(input);
            default:
                return false;
        }

    }
    private void setupDocumentFilters(JTextField textField, String validationType) {
        Document doc = textField.getDocument();
        if (doc instanceof PlainDocument) {
            ((PlainDocument) doc).setDocumentFilter(new DocumentFilter() {
                @Override
                public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                    if (areValidInputs(string, validationType)) {
                        super.insertString(fb, offset, string, attr);
                    }
                }
                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                    if (areValidInputs(text, validationType)) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            });
        }
    }



    public void printCheckListener(ActionListener listener) {
        printCheckButton.addActionListener(listener);
    }

    public void showTimeDateListener(String dateTimeGeneration) {
        timeDateGenerationLabel.setText("Fecha y Hora de Generación: " + dateTimeGeneration);
    }

    public void showResult(String resultado) {
        resultArea.setText(resultado);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChequeView formView = new ChequeView();
            formView.setVisible(true);
        });
    }


}

