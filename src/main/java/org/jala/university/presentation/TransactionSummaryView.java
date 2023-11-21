package org.jala.university.presentation;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Container;

/**
 * The TransactionSummaryView class represents the graphical user
 * interface for displaying transaction summaries.
 *
 * @author Daniel Virreira
 * @since 2023-10-20
 * @version 1.4
 */
public class TransactionSummaryView extends JFrame {
    /*
     * Constants for layout interface configuration
     */
    private final Color BACKGROUND_TEXT_FIELD_COLOR = new Color(230,230,230);
    private final int LABEL_STANDARD_HORIZONTAL_POSITION = 20;
    private final int FIELD_STANDARD_HORIZONTAL_POSITION = 180;
    private final int LABEL_STANDARD_WIDTH = 150;
    private final int FIELD_STANDARD_WIDTH = 300;
    private final int LABEL_FIELD_STANDARD_HEIGHT = 25;
    private int labelAndFieldVerticalPosition;


    /**
     * Constructor for the TransactionSummaryView class.
     *
     * @param transactionValues Transaction values to display in the screen.
     */
    public TransactionSummaryView(TransactionModuleValues transactionValues) {
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);
        labelAndFieldVerticalPosition = 45; // Initial position value

        setTitle("Resumen de Transacción");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        int VERTICAL_POSITION_MOVEMENT = 40; //Vertical movement in pixels to set JLabel and JTextField items.

        // Member variables for labels and text fields.
        JLabel idLabel = labelCreator("ID");
        add(idLabel);
        JTextField idField = fieldCreator(String.valueOf(transactionValues.getPrintableId()));
        add(idField);

        labelAndFieldVerticalPosition += VERTICAL_POSITION_MOVEMENT;
        JLabel dateLabel = labelCreator("Fecha: ");
        add(dateLabel);
        JTextField dateField = fieldCreator(transactionValues.getPrintableDate());
        add(dateField);

        labelAndFieldVerticalPosition += VERTICAL_POSITION_MOVEMENT;
        JLabel typeLabel = labelCreator("Tipo Transacción: ");
        add(typeLabel);
        JTextField typeField = fieldCreator(transactionValues.getPrintableTransactionType());
        add(typeField);

        labelAndFieldVerticalPosition += VERTICAL_POSITION_MOVEMENT;
        JLabel amountLabel = labelCreator("Monto: ");
        add(amountLabel);
        JTextField amountField = fieldCreator(transactionValues.getPrintableAmount());
        add(amountField);

        labelAndFieldVerticalPosition += VERTICAL_POSITION_MOVEMENT;
        JLabel currencyLabel = labelCreator("Divisa: ");
        add(currencyLabel);
        JTextField currencyField = fieldCreator(transactionValues.getPrintableCurrency());
        add(currencyField);

        labelAndFieldVerticalPosition += VERTICAL_POSITION_MOVEMENT;
        JLabel accountFromLabel = labelCreator("Cuenta Debitada: ");
        add(accountFromLabel);
        JTextField accountFromField = fieldCreator(transactionValues.getPrintableAccountFrom());
        add(accountFromField);

        labelAndFieldVerticalPosition += VERTICAL_POSITION_MOVEMENT;
        JLabel accountToLabel = labelCreator("Cuenta Destinataria: ");
        add(accountToLabel);
        JTextField accountToField = fieldCreator(transactionValues.getPrintableAccountTo());
        add(accountToField);

        labelAndFieldVerticalPosition += VERTICAL_POSITION_MOVEMENT;
        JLabel statusLabel = labelCreator("Estado Transacción: ");
        add(statusLabel);
        JTextField statusField = fieldCreator(transactionValues.getPrintableTransactionStatus());
        add(statusField);

        labelAndFieldVerticalPosition += VERTICAL_POSITION_MOVEMENT;
        descriptionCreatorUI(transactionValues.getPrintableDescription());
    }

    /**
     * Private method to create JLabel items in a specific and standardized format.
     *
     * @param labelName Text that JLabel will contain
     * @return JLabel
     */
    private JLabel labelCreator(String labelName) {
        JLabel label = new JLabel(labelName);
        label.setBounds(LABEL_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                LABEL_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        return label;
    }

    /**
     * Private method to create JTextField items in a specific and standardized format
     *
     * @param fieldInformation Data received from TransactionModuleValues transformed
     *                         into Strings for print in the screen.
     * @return JTextField
     */

    private JTextField fieldCreator(String fieldInformation) {
        JTextField textField = new JTextField(fieldInformation);
        textField.setBounds(FIELD_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                FIELD_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        textField.setEnabled(false);
        textField.setBackground(BACKGROUND_TEXT_FIELD_COLOR);
        textField.setDisabledTextColor(Color.darkGray);
        return textField;
    }

    /**
     * Creates and configures the user interface for displaying the
     * transaction description.
     *
     * @param description The transaction description to display.
     */
    private void descriptionCreatorUI(String description) {
        JLabel descriptionLabel = new JLabel("Descripción: ");
        descriptionLabel.setBounds(LABEL_STANDARD_HORIZONTAL_POSITION, labelAndFieldVerticalPosition,
                LABEL_STANDARD_WIDTH, LABEL_FIELD_STANDARD_HEIGHT);
        add(descriptionLabel);

        int FIELD_HEIGHT_TEXT_AREA = 100;
        JTextArea descriptionArea = new JTextArea(description);
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
}

