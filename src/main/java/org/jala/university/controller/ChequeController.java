package org.jala.university.controller;


import org.jala.university.model.ChequeModel;
import org.jala.university.view.ChequeView;

import javax.swing.*;
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

        view.setGenerarChequeListener(new GenerarChequeListener());
        view.setImprimirChequeListener(new ImprimirChequeListener());
    }

    private class GenerarChequeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nombre = view.getNombre();
            double monto = 0;
            String motivo = view.getMotivo();
            String tipoMoneda = view.getTipoMoneda();

            try {
                monto = view.getMonto();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Ingrese un monto válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (nombre.isEmpty() || motivo.isEmpty() || tipoMoneda.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int confirm = JOptionPane.showConfirmDialog(view, "¿Está seguro de generar el cheque?", "Confirmación", JOptionPane.OK_CANCEL_OPTION);
                if (confirm == JOptionPane.OK_OPTION) {
                    model.setNombre(nombre);
                    model.setMonto(monto);
                    model.setMotivo(motivo);
                    model.setTipoMoneda(tipoMoneda);
                    String fechaHoraGeneracion = obtenerFechaHoraActual();
                    model.setFechaHoraGeneracion(fechaHoraGeneracion);
                    view.mostrarFechaHoraGeneracion(fechaHoraGeneracion);
                }
            }
        }
    }

    private class ImprimirChequeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder resultado = new StringBuilder();
            resultado.append("Número de Cheque: ").append(obtenerNumeroChequeAleatorio()).append("\n");
            resultado.append("Nombre: ").append(model.getNombre()).append("\n");
            resultado.append("Monto: ").append(model.getMonto()).append("\n");
            resultado.append("Motivo: ").append(model.getMotivo()).append("\n");
            resultado.append("Tipo de Moneda: ").append(model.getTipoMoneda()).append("\n");
            resultado.append("Fecha y Hora de Generación: ").append(model.getFechaHoraGeneracion()).append("\n");

            view.mostrarResultado(resultado.toString());

            JOptionPane.showMessageDialog(view, resultado.toString(), "Imprimiendo Cheque", JOptionPane.INFORMATION_MESSAGE);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(view, "Cheque impreso\n\n" + resultado.toString(), "Completado", JOptionPane.INFORMATION_MESSAGE);
                    });
                }
            }, 3000); // 3000 milisegundos = 3 segundos
        }
    }

    private String obtenerFechaHoraActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        return sdf.format(date);
    }

    public void iniciar() {
        view.setVisible(true);
    }

    private int obtenerNumeroChequeAleatorio() {
        return (int) (Math.random() * 1000);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChequeModel model = new ChequeModel();
            ChequeView view = new ChequeView();
            ChequeController controller = new ChequeController(view, model);
            controller.iniciar();
        });
    }
}
