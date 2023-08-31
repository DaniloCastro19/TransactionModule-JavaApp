package org.jala.university.presentation;

import org.jala.university.domain.TransactionModule;

import javax.swing.*;
import java.awt.*;

/**
 * @author : Joaquin.Arrazola
 * Date : 8/23/2023
 */
public class TransactionView extends JFrame {
    private final TransactionModule transactionModule;

    public TransactionView(TransactionModule transactionModule) {
        this.transactionModule = transactionModule;
    }
}
