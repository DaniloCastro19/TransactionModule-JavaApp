package org.jala.university.presentation;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Container;

public class TransactionSummaryView extends JFrame {
    private final Color BACKGROUND_TEXT_FIELD_COLOR = new Color(230,230,230);
    private final int LABEL_STANDARD_HORIZONTAL_POSITION = 20;
    private final int FIELD_STANDARD_HORIZONTAL_POSITION = 180;
    private final int LABEL_STANDARD_WIDTH = 150;
    private final int FIELD_STANDARD_WIDTH = 300;
    private final int LABEL_FIELD_STANDARD_HEIGHT = 25;
    private int labelAndFieldVerticalPosition = 45; //Initial value

    public TransactionSummaryView(String[] transactionValues) {
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);

        setTitle("Resumen de Transacción");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        int VERTICAL_POSITION_MOVEMENT = 40;
        idCreatorUI(transactionValues[0]);

        labelAndFieldVerticalPosition += VERTICAL_POSITION_MOVEMENT;
        dateCreatorUI(transactionValues[1]);

        labelAndFieldVerticalPosition += VERTICAL_POSITION_MOVEMENT;
        transactionTypeCreatorUI(transactionValues[2]);

        labelAndFieldVerticalPosition += VERTICAL_POSITION_MOVEMENT;
        amountCreationUI(transactionValues[3]);

        labelAndFieldVerticalPosition += VERTICAL_POSITION_MOVEMENT;
        currencyCreatorIU(transactionValues[4]);

        labelAndFieldVerticalPosition += VERTICAL_POSITION_MOVEMENT;
        accountFromCreatorUI(transactionValues[5]);

        labelAndFieldVerticalPosition += VERTICAL_POSITION_MOVEMENT;
        accountToCreatorUI(transactionValues[6]);

        labelAndFieldVerticalPosition += VERTICAL_POSITION_MOVEMENT;
        statusCreatorUI(transactionValues[7]);

        labelAndFieldVerticalPosition += VERTICAL_POSITION_MOVEMENT;
        descriptionCreatorUI(transactionValues[8]);
    }
    private void idCreatorUI(String id) {
        idLabel = new JLabel("ID: ");
        idLabel.setBounds(LABEL_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                LABEL_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        add(idLabel);

        idField = new JTextField(id);
        idField.setBounds(FIELD_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                FIELD_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        idField.setEnabled(false);
        idField.setBackground(BACKGROUND_TEXT_FIELD_COLOR);
        idField.setDisabledTextColor(Color.darkGray);
        add(idField);
    }

    private void dateCreatorUI(String date) {
        dateLabel = new JLabel("Fecha: ");
        dateLabel.setBounds(LABEL_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                LABEL_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        add(dateLabel);

        dateField = new JTextField(date);
        dateField.setBounds(FIELD_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                FIELD_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        dateField.setEnabled(false);
        dateField.setBackground(BACKGROUND_TEXT_FIELD_COLOR);
        dateField.setDisabledTextColor(Color.darkGray);
        add(dateField);
    }

    private void transactionTypeCreatorUI(String transactionType) {
        typeLabel = new JLabel("Tipo: ");
        typeLabel.setBounds(LABEL_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                FIELD_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        add(typeLabel);

        typeField = new JTextField(transactionType);
        typeField.setBounds(FIELD_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                FIELD_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        typeField.setEnabled(false);
        typeField.setBackground(BACKGROUND_TEXT_FIELD_COLOR);
        typeField.setDisabledTextColor(Color.darkGray);
        add(typeField);
    }

    private void amountCreationUI(String amount) {
        amountLabel = new JLabel("Cantidad: ");
        amountLabel.setBounds(LABEL_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                FIELD_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        add(amountLabel);

        amountField = new JTextField(amount);
        amountField.setBounds(FIELD_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                FIELD_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        amountField.setEnabled(false);
        amountField.setBackground(BACKGROUND_TEXT_FIELD_COLOR);
        amountField.setDisabledTextColor(Color.darkGray);
        add(amountField);
    }

    private void currencyCreatorIU(String currency) {
        currencyLabel = new JLabel("Moneda: ");
        currencyLabel.setBounds(LABEL_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                FIELD_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        add(currencyLabel);

        currencyField = new JTextField(currency);
        currencyField.setBounds(FIELD_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                FIELD_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        currencyField.setEnabled(false);
        currencyField.setBackground(BACKGROUND_TEXT_FIELD_COLOR);
        currencyField.setDisabledTextColor(Color.darkGray);
        add(currencyField);
    }

    private void accountFromCreatorUI(String accountFrom) {
        accountFromLabel = new JLabel("Cuenta Debitada: ");
        accountFromLabel.setBounds(LABEL_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                LABEL_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        add(accountFromLabel);

        accountFromField = new JTextField(accountFrom);
        accountFromField.setBounds(FIELD_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                FIELD_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        accountFromField.setEnabled(false);
        accountFromField.setBackground(BACKGROUND_TEXT_FIELD_COLOR);
        accountFromField.setDisabledTextColor(Color.darkGray);
        add(accountFromField);
    }

    private void accountToCreatorUI(String accountTo) {
        accountToLabel = new JLabel("Cuenta Destinataria:");
        accountToLabel.setBounds(LABEL_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                LABEL_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        add(accountToLabel);

        accountToField = new JTextField(accountTo);
        accountToField.setBounds(FIELD_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition, 300, 25);
        accountToField.setEnabled(false);
        accountToField.setBackground(BACKGROUND_TEXT_FIELD_COLOR);
        accountToField.setDisabledTextColor(Color.darkGray);
        add(accountToField);
    }

    private void statusCreatorUI(String status) {
        statusLabel = new JLabel("Estado: ");
        statusLabel.setBounds(LABEL_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                FIELD_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        add(statusLabel);

        statusField = new JTextField(status);
        statusField.setBounds(FIELD_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                FIELD_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        statusField.setEnabled(false);
        statusField.setBackground(BACKGROUND_TEXT_FIELD_COLOR);
        statusField.setDisabledTextColor(Color.darkGray);
        add(statusField);
    }

    private void descriptionCreatorUI(String description) {
        descriptionLabel = new JLabel("Descripción: ");
        descriptionLabel.setBounds(LABEL_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                LABEL_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        add(descriptionLabel);

        int FIELD_HEIGHT_TEXT_AREA = 100;
        descriptionArea = new JTextArea(description);
        descriptionArea.setBounds(FIELD_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                FIELD_STANDARD_WIDTH, FIELD_HEIGHT_TEXT_AREA);
        descriptionArea.setEnabled(false);
        descriptionArea.setBackground(BACKGROUND_TEXT_FIELD_COLOR);
        descriptionArea.setDisabledTextColor(Color.BLACK);
        descriptionArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        add(descriptionArea);
    }

    private JLabel idLabel;
    private JLabel dateLabel;
    private JLabel typeLabel;
    private JLabel amountLabel;
    private JLabel currencyLabel;
    private JLabel accountFromLabel;
    private JLabel accountToLabel;
    private JLabel statusLabel;
    private JLabel descriptionLabel;

    private JTextField idField;
    private JTextField dateField;
    private JTextField typeField;
    private JTextField amountField;
    private JTextField currencyField;
    private JTextField accountFromField;
    private JTextField accountToField;
    private JTextField statusField;
    private JTextArea descriptionArea;
}