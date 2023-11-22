package org.jala.university.presentation;

import org.jala.university.model.Transaction;
import org.jala.university.model.TransactionStatus;
import org.jala.university.model.TransactionType;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryTableModel extends AbstractTableModel {


    private List<Transaction> transactionList;
    private String[] columnNames = {"Fecha de transaccion", "tipo de transaccion", "Cuenta de origen", "Cuenta de destino", "Estado"};

    public HistoryTableModel(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public int getRowCount() {
        return transactionList.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Transaction transaction = transactionList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return transaction.getDate();
            case 1:
                return transaction.getType();
            case 2:
                return transaction.getAccountFrom() != null ? transaction.getAccountFrom().getAccountNumber() : "N/A";
            case 3:
                return transaction.getAccountTo() != null ? transaction.getAccountTo().getAccountNumber() : "N/A";
            case 4:
                return transaction.getStatus();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Date.class;
            case 1:
                return TransactionType.class;
            case 2, 3:
                return String.class;
            case 4:
                return TransactionStatus.class;
        }
        return null;
    }

    public void setTransactionList(List<Transaction> newTransactionList) {
        this.transactionList = new ArrayList<>(newTransactionList);
        fireTableDataChanged();
    }

}
