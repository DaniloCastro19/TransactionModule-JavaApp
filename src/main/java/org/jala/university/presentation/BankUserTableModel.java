package org.jala.university.presentation;

import org.jala.university.model.Account;
import org.jala.university.model.BankUser;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class BankUserTableModel extends AbstractTableModel {
    private List<BankUser> userList;
    private String[] columnNames = {"numero de cuenta", "Nombre", "Apellido"};

    public BankUserTableModel(List<BankUser> userList) {
        this.userList = userList;
    }
    @Override
    public String getColumnName(int columnIndex){
        return columnNames[columnIndex];
    }

    @Override
    public int getRowCount() {
        return userList.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BankUser account = userList.get(rowIndex);
        Account userAccount = account.getAccount();
        switch (columnIndex) {
            case 0:
                return userAccount.getAccountNumber();
            case 1:
                return account.getFirstName();
            case 2:
                return account.getLastName();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
        return String.class;
    }

    public void setUserList(List<BankUser> newUserList) {
        this.userList = new ArrayList<>(newUserList);
        fireTableDataChanged();
    }
}
