package org.jala.university.presentation;

import org.jala.university.domain.TransactionModule;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;

public class TransactionView extends JFrame {
    private final TransactionModule transactionModule;
    private JPanel topPanel;
    private JPanel btnPanel;
    private JScrollPane scrollPane;

    public TransactionView(TransactionModule transactionModule) {
        this.transactionModule = transactionModule;
        setTitle("Account Module");
        setSize(500, 500);
        setBackground(Color.gray);
        setLocationRelativeTo(null);
    }
}
