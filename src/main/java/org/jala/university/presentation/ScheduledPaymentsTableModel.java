package org.jala.university.presentation;

import org.jala.university.model.Transaction;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ScheduledPaymentsTableModel extends AbstractTableModel {
    private String[] header = {"Destinatario", "Horario", "Monto"};
    private List<Transaction> transactionList;
    public ScheduledPaymentsTableModel(List<Transaction> transactionList) {
        this.transactionList = transactionList;

    }

    @Override
    public int getRowCount() {
        return transactionList.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Transaction transaction = transactionList.get(rowIndex);
        switch (columnIndex){
            case 0:
                return transaction.getAccountTo();
            case 1:
                return transaction.getDate();
            case 2:
                return transaction.getAmount();
            default:
                return null;
        }
    }

    public void setTransactionList(List<Transaction> newTransactionList) {
        this.transactionList = new ArrayList<>(newTransactionList);
        fireTableDataChanged();
    }

}
