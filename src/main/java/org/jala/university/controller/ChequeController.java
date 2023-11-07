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

public class ChequeController {
    private ChequeView view;
    private ChequeModel model;

    public ChequeController(ChequeView view, ChequeModel model) {
        this.view = view;
        this.model = model;

        view.generateCheckListener(new GenerarChequeListener());
        view.printCheckListener(new ImprimirChequeListener());
    }

    private class GenerarChequeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getName();
            double amount = 0;
            String reason = view.getReason();
            String currency = view.getCurrency();

            try {
                amount = view.getAmount();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Ingrese un monto válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (name.isEmpty() || reason.isEmpty() || currency.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int confirm = JOptionPane.showConfirmDialog(view, "¿Está seguro de generar el cheque?", "Confirmación", JOptionPane.OK_CANCEL_OPTION);
                if (confirm == JOptionPane.OK_OPTION) {
                    model.setName(name);
                    model.setAmount(amount);
                    model.setReason(reason);
                    model.setCurrency(currency);
                    String timeDateGeneration = getCurrentDateTime();
                    model.setTimeDateGenerationLabel(timeDateGeneration);
                    view.showTimeDateListener(timeDateGeneration);
                }
            }
        }
    }

    private class ImprimirChequeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder result = new StringBuilder();
            result.append("Número de Cheque: ").append(getRandomCheckNumber()).append("\n");
            result.append("nombre: ").append(model.getName()).append("\n");
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
                        JOptionPane.showMessageDialog(view, "Cheque impreso\n\n" + result.toString(), "Completado", JOptionPane.INFORMATION_MESSAGE);
                    });
                }
            }, 3000); // 3000 milisegundos = 3 segundos
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
