package org.jala.university.presentation;

import org.jala.university.domain.ScheduledTransferModel;

import javax.swing.JOptionPane;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Date;
import java.util.List;

/**
 * This class provides methods for displaying and printing scheduled transfer alerts.
 */
public class ScheduledTransferAlert {

  public static void showScheduledTransferAlert(List<Date> scheduledDates, ScheduledTransferModel scheduledTransferModel) {
    String message = buildScheduledTransferMessage(scheduledDates, scheduledTransferModel);

    int option = JOptionPane.showOptionDialog(
      null,
      message,
      "Información del Pago Programado",
      JOptionPane.DEFAULT_OPTION,
      JOptionPane.INFORMATION_MESSAGE,
      null,
      new Object[]{"OK", "Imprimir"},
      "OK"
    );

    if (option == 1) {
      printScheduledTransfer(scheduledDates, scheduledTransferModel);
    }
  }

  private static void printScheduledTransfer(List<Date> scheduledDates, ScheduledTransferModel scheduledTransferModel) {
    String message = buildScheduledTransferMessage(scheduledDates, scheduledTransferModel);
    PrinterJob job = PrinterJob.getPrinterJob();
    if (job.printDialog()) {
      try {
        job.print();
        JOptionPane.showMessageDialog(null, "La factura del pago agendado ya fue impreso", "Impresión ", JOptionPane.INFORMATION_MESSAGE);
      } catch (PrinterException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al imprimir", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private static String buildScheduledTransferMessage(List<Date> scheduledDates, ScheduledTransferModel scheduledTransferModel) {
    StringBuilder message = new StringBuilder();
    message.append("Información del Pago Programado:\n\n");
    message.append("Monto: ").append(scheduledTransferModel.getAmount()).append("\n");
    message.append("Cuenta de Origen: ").append(scheduledTransferModel.getAccountFrom().getAccountNumber()).append("\n");
    message.append("Cuenta de Destino: ").append(scheduledTransferModel.getAccountTo().getAccountNumber()).append("\n");
    message.append("Moneda: ").append(scheduledTransferModel.getCurrency()).append("\n");
    message.append("Fecha de Inicio: ").append(scheduledTransferModel.getDate()).append("\n");
    message.append("Estado: ").append(scheduledTransferModel.getStatus()).append("\n");
    message.append("Frecuencia: ").append(scheduledTransferModel.getFrequency()).append("\n");
    message.append("Número de Ocurrencias: ").append(scheduledTransferModel.getNumOccurrences()).append("\n");
    message.append("Detalles: ").append(scheduledTransferModel.getDetails()).append("\n");

    message.append("\nFechas de Transferencia:\n");
    for (Date date : scheduledDates) {
      message.append("- ").append(date).append("\n");
    }

    return message.toString();
  }
}