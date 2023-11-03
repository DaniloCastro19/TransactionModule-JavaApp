package org.jala.university.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChequeView extends JFrame {
    private JTextField nombreField;
    private JTextField montoField;
    private JTextField motivoField;
    private JComboBox<String> tipoMonedaComboBox;
    private JButton generarChequeButton;
    private JButton imprimirChequeButton;
    private JTextArea resultadoArea;
    private JLabel fechaHoraGeneracionLabel;
    private boolean fechaHoraVisible = false;

    public ChequeView() {
        setTitle("Emitir Cheques");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreField = new JTextField(20);

        JLabel montoLabel = new JLabel("Monto:");
        montoField = new JTextField(20);

        JLabel motivoLabel = new JLabel("Motivo:");
        motivoField = new JTextField(20);

        JLabel tipoMonedaLabel = new JLabel("Tipo de Moneda:");
        String[] monedas = {"Dollar", "Euro", "Bolivianos"};
        tipoMonedaComboBox = new JComboBox<>(monedas);

        generarChequeButton = new JButton("Generar Cheque");
        imprimirChequeButton = new JButton("Imprimir Cheque");

        fechaHoraGeneracionLabel = new JLabel("Fecha y Hora de Generación:");
        fechaHoraGeneracionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fechaHoraGeneracionLabel.setVisible(fechaHoraVisible);

        resultadoArea = new JTextArea(5, 20);
        resultadoArea.setEditable(false);

        c.gridx = 0;
        c.gridy = 0;
        panel.add(nombreLabel, c);

        c.gridx = 1;
        panel.add(nombreField, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(montoLabel, c);

        c.gridx = 1;
        panel.add(montoField, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(motivoLabel, c);

        c.gridx = 1;
        panel.add(motivoField, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(tipoMonedaLabel, c);

        c.gridx = 1;
        panel.add(tipoMonedaComboBox, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(generarChequeButton, c);

        c.gridx = 0;
        c.gridy = 5;
        panel.add(imprimirChequeButton, c);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        panel.add(fechaHoraGeneracionLabel, c);

        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        panel.add(resultadoArea, c);

        getContentPane().add(panel);

        setLocationRelativeTo(null);
    }

    public String getNombre() {
        return nombreField.getText();
    }

    public double getMonto() {
        return Double.parseDouble(montoField.getText());
    }

    public String getMotivo() {
        return motivoField.getText();
    }

    public String getTipoMoneda() {
        return tipoMonedaComboBox.getSelectedItem().toString();
    }

    public void setGenerarChequeListener(ActionListener listener) {
        generarChequeButton.addActionListener(e -> {
            fechaHoraVisible = true;
            fechaHoraGeneracionLabel.setVisible(fechaHoraVisible);
            listener.actionPerformed(e);
        });
    }

    public void setImprimirChequeListener(ActionListener listener) {
        imprimirChequeButton.addActionListener(listener);
    }

    public void mostrarFechaHoraGeneracion(String fechaHoraGeneracion) {
        fechaHoraGeneracionLabel.setText("Fecha y Hora de Generación: " + fechaHoraGeneracion);
    }

    public void mostrarResultado(String resultado) {
        resultadoArea.setText(resultado);
    }
}

