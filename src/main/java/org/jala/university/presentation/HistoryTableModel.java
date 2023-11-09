package org.jala.university.presentation;

import org.jala.university.dao.AccountDAOMock;
import org.jala.university.dao.MockDataGenerator;
import org.jala.university.dao.TransactionDAOMock;
import org.jala.university.dao.UserDAOMock;
import org.jala.university.domain.TransactionModule;
import org.jala.university.domain.TransactionModuleImpl;
import org.jala.university.domain.UserModule;
import org.jala.university.domain.UserModuleImpl;
import org.jala.university.model.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryTableModel extends AbstractTableModel {


    private List<Transaction> transactinoList;
    private String[] columnNames = {"Fecha de transaccion", "tipo de transaccion", "Cuenta de origen", "Cuenta de destino", "Estado"};

    public HistoryTableModel(List<Transaction> transactionList) {
        this.transactinoList = transactionList;
    }
    @Override
    public String getColumnName(int columnIndex){
        return columnNames[columnIndex];
    }

    @Override
    public int getRowCount() {
        return transactinoList.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Transaction transaction = transactinoList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return transaction.getDate();
            case 1:
                return transaction.getType();
            case 2:
                return transaction.getAccountFrom().getAccountNumber();
            case 3:
                return transaction.getAccountTo().getAccountNumber();
            case 4:
                return transaction.getStatus();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch (columnIndex){
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
        this.transactinoList = new ArrayList<>(newTransactionList);
        fireTableDataChanged();
    }

}
