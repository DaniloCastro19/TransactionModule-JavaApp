package org.jala.university.controller;

import org.jala.university.model.ChequeModel;
import org.jala.university.view.ChequeView;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller for issuing checks.
 */
public class ChequeController {
    private ChequeView view;
    private ChequeModel model;
    private double accountBalance = 2000; // Example: opening account balance.

    public ChequeController(ChequeView view, ChequeModel model) {
        this.view = view;
        this.model = model;

        view.generateCheckListener(new GenerateCheckListener ());
        view.printCheckListener(new PrintChequeListener());
    }

    /**
     * Listener for the generate check button.
     */
    private class GenerateCheckListener  implements ActionListener {

        /**
         * Invoked when the generate check button is clicked.
         *
         * @param e The action event.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getName();
            double amount = 0;
            String reason = view.getReason();
            String currency = view.getCurrency();
            String accountNumber = view.getNumeroCuenta();

            if (!isValidName(name)) {
                JOptionPane.showMessageDialog(view, "El nombre no es válido. Debe contener solo letras.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                amount = view.getAmount();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Ingrese un monto válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (accountNumber.isEmpty() || name.isEmpty() || reason.isEmpty() || currency.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (accountBalance >= amount) {
                    int confirm = JOptionPane.showConfirmDialog(view, "¿Está seguro de generar el cheque?", "Confirmación", JOptionPane.OK_CANCEL_OPTION);
                    if (confirm == JOptionPane.OK_OPTION) {
                        model.setAccountNumber(accountNumber);
                        model.setName(name);
                        model.setAmount(amount);
                        model.setReason(reason);
                        model.setCurrency(currency);
                        String timeDateGeneration = getCurrentDateTime();
                        model.setTimeDateGenerationLabel(timeDateGeneration);
                        view.showTimeDateListener(timeDateGeneration);
                        // Actualizar el saldo de la cuenta
                        accountBalance -= amount;
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Saldo insuficiente para generar el cheque.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        /**
         * Checks if the entered name is valid (only letters).
         *
         * @param name The name to validate.
         * @return True if the name is valid, false otherwise.
         */
        private boolean isValidName(String name) {
            return name.matches("^[a-zA-Z ]*$");
        }
    }

    private class PrintChequeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder result = new StringBuilder();
            result.append("Número de Cheque: ").append(getRandomCheckNumber()).append("\n");
            result.append("N° de Cuenta de Origen: ").append(model.getAccountNumber()).append("\n");
            result.append("Nombre del beneficiario: ").append(model.getName()).append("\n");
            result.append("Monto: ").append(model.getAmount()).append("\n");
            result.append("Motivo: ").append(model.getReason()).append("\n");
            result.append("Tipo de Moneda: ").append(model.getCurrency()).append("\n");

            view.showResult(result.toString());

            JOptionPane.showMessageDialog(view, result.toString(), "Imprimiendo Cheque", JOptionPane.INFORMATION_MESSAGE);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(view, "Cheque BANCARIO BOLIVIA.\n\n" + result.toString(), "Cheque impreso - Completado", JOptionPane.INFORMATION_MESSAGE);
                    });
                }
            }, 3000); // 3000 milliseconds = 3 seconds.
        }
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        return sdf.format(date);
    }

    public void start() {
        view.setVisible(true);
    }

    private int getRandomCheckNumber() {
        return (int) (Math.random() * 1000);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChequeModel model = new ChequeModel();
            ChequeView view = new ChequeView();
            ChequeController controller = new ChequeController(view, model);
            controller.start();
        });
    }
}
