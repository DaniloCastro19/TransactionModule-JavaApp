package org.jala.university.presentation;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionDetailsView extends JFrame {
    private JLabel titleLabel = new JLabel("Formulario de la transacción");
    private JLabel transactionTypeLabel = new JLabel("Tipo de Transacción:");
    private JLabel dateLabel = new JLabel("Fecha:");
    private JLabel amountLabel = new JLabel("Monto:");
    private JLabel destinataryLabel = new JLabel("Destinatario:");
    private JLabel accountToLabel = new JLabel("N° Cuenta del Destinatario:");
    private JLabel currencyLabel = new JLabel("Tipo de Moneda:");
    private JLabel transactionStatusLabel = new JLabel("Estado de Transacción:");
    private JLabel aditionalDetailsLabel = new JLabel("Detalles Adicionales:");

    private JTextField transactionType = new JTextField(20);
    private JTextField dateField = new JTextField(20);
    private JTextField amountField = new JTextField(20);
    private JTextField textFieldDestinatario = new JTextField(20);
    private JTextField acountToField = new JTextField(20);
    private JTextField currencyField = new JTextField(20);
    private JTextField transactionStatusField = new JTextField(20);
    private JTextField aditionalDetails = new JTextField(20);

    private JButton editBtn = new JButton("Editar");
    private JButton saveBtn = new JButton("Guardar");
    private JButton completeBtn = new JButton("Completar Transacción");

    public TransactionDetailsView() {
        setTitle("Formulario de la transacción");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(11, 2, 10, 10));
        setResizable(false);

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 17));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Color primaryColor = new Color(32, 76, 153);
        titleLabel.setForeground(primaryColor);
        getContentPane().setBackground(Color.GRAY);

        add(titleLabel);
        add(new JLabel(""));
        addLabeledTextField(accountToLabel, acountToField);
        addLabeledTextField(destinataryLabel, textFieldDestinatario);
        addLabeledTextField(transactionTypeLabel, transactionType);
        addLabeledTextField(amountLabel, amountField);
        addLabeledTextField(currencyLabel, currencyField);
        addLabeledTextField(dateLabel, dateField);
        addLabeledTextField(transactionStatusLabel, transactionStatusField);
        addLabeledTextField(aditionalDetailsLabel, aditionalDetails);
        add(new JLabel(""));
        add(editBtn);
        add(saveBtn);
        add(completeBtn);

        setFieldsEditable(false);
        saveBtn.setEnabled(false);
        completeBtn.setEnabled(false);

        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFieldsEditable(true);
                saveBtn.setEnabled(true);
            }
        });

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setFieldsEditable(false);
                saveBtn.setEnabled(false);
                completeBtn.setEnabled(true);
            }
        });

        completeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(TransactionDetailsView.this, "Transacción completada.");
            }
        });
    }

    private void addLabeledTextField(JLabel label, JTextField textField) {
        label.setFont(new Font("Arial", Font.BOLD, 16));
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(label);
        add(textField);
    }

    private void setFieldsEditable(boolean editable) {
        transactionType.setEditable(editable);
        dateField.setEditable(editable);
        amountField.setEditable(editable);
        textFieldDestinatario.setEditable(editable);
        acountToField.setEditable(editable);
        currencyField.setEditable(editable);
        transactionStatusField.setEditable(editable);
        aditionalDetails.setEditable(editable);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TransactionDetailsView view = new TransactionDetailsView();
                view.setVisible(true);
            }
        });
    }
}
