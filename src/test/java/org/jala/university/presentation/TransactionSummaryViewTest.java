package org.jala.university.presentation;

import org.junit.Test;

public class TransactionSummaryViewTest {
    @Test
    public void initTransactionSummaryViewTest() {
        String[] values = {"123", "24 de Octubre de 2023", "Transferencia", "2000", "Dólares", "Daniel",
                "Pepe", "Estado", "Esta transacción ha sido realizada correctamente."};
        //TransactionSummaryView window = new TransactionSummaryView(values);
        //window.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
