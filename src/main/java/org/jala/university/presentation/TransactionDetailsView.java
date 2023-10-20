package org.jala.university.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionDetailsView extends JFrame {
    private JLabel labelTitulo = new JLabel("Formulario de la transacción");
    private JLabel labelTipoTransaccion = new JLabel("Tipo de Transacción:");
    private JLabel labelFecha = new JLabel("Fecha:");
    private JLabel labelCantidad = new JLabel("Monto:");
    private JLabel labelDestinatario = new JLabel("Destinatario:");
    private JLabel labelNumeroCuentaDestinatario = new JLabel("N° Cuenta del Destinatario:");
    private JLabel labelTipoMoneda = new JLabel("Tipo de Moneda:");
    private JLabel labelEstadoTransaccion = new JLabel("Estado de Transacción:");
    private JLabel labelDetallesAdicionales = new JLabel("Detalles Adicionales:");

    private JTextField textFieldTipoTransaccion = new JTextField(20);
    private JTextField textFieldFecha = new JTextField(20);
    private JTextField textFieldCantidad = new JTextField(20);
    private JTextField textFieldDestinatario = new JTextField(20);
    private JTextField textFieldNumeroCuentaDestinatario = new JTextField(20);
    private JTextField textFieldTipoMoneda = new JTextField(20);
    private JTextField textFieldEstadoTransaccion = new JTextField(20);
    private JTextField textFieldDetallesAdicionales = new JTextField(20);

    private JButton btnEditar = new JButton("Editar");
    private JButton btnGuardar = new JButton("Guardar");
    private JButton btnCompletar = new JButton("Completar Transacción");

    public TransactionDetailsView() {
        // Configuración del marco principal
        setTitle("Formulario de la transacción");
        setSize(500, 500); // Tamaño del marco
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(11, 2, 10, 10)); // Diseño en cuadrícula
        setResizable(false); // Impedir que se pueda redimensionar

        // Estilo de los componentes
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 17));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Configuración de colores
        Color primaryColor = new Color(32, 76, 153);
        labelTitulo.setForeground(primaryColor);
        getContentPane().setBackground(Color.GRAY); // Fondo gris

        // Agregar componentes al marco
        add(labelTitulo);
        add(new JLabel("")); // Espacio en blanco
        addLabeledTextField(labelNumeroCuentaDestinatario, textFieldNumeroCuentaDestinatario);
        addLabeledTextField(labelDestinatario, textFieldDestinatario);
        addLabeledTextField(labelTipoTransaccion, textFieldTipoTransaccion);
        addLabeledTextField(labelCantidad, textFieldCantidad);
        addLabeledTextField(labelTipoMoneda, textFieldTipoMoneda);
        addLabeledTextField(labelFecha, textFieldFecha);
        addLabeledTextField(labelEstadoTransaccion, textFieldEstadoTransaccion);
        addLabeledTextField(labelDetallesAdicionales, textFieldDetallesAdicionales);
        add(new JLabel("")); // Espacio en blanco
        add(btnEditar);
        add(btnGuardar);
        add(btnCompletar);

        // Configuración inicial
        setFieldsEditable(false); // Inicialmente no editable
        btnGuardar.setEnabled(false); // Inicialmente deshabilitado
        btnCompletar.setEnabled(false); // Inicialmente deshabilitado

        // Configuración de eventos
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para habilitar la edición
                setFieldsEditable(true);
                btnGuardar.setEnabled(true);
            }
        });

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para guardar la información editada
                setFieldsEditable(false);
                btnGuardar.setEnabled(false);
                btnCompletar.setEnabled(true);
            }
        });

        btnCompletar.addActionListener(new ActionListener() {
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
        textFieldTipoTransaccion.setEditable(editable);
        textFieldFecha.setEditable(editable);
        textFieldCantidad.setEditable(editable);
        textFieldDestinatario.setEditable(editable);
        textFieldNumeroCuentaDestinatario.setEditable(editable);
        textFieldTipoMoneda.setEditable(editable);
        textFieldEstadoTransaccion.setEditable(editable);
        textFieldDetallesAdicionales.setEditable(editable);
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
