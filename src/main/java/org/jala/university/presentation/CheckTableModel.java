package org.jala.university.presentation;

import org.jala.university.model.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CheckTableModel extends AbstractTableModel {
    private List<Check> checkList;
    private String[] columnNames = {"Check ID" , "Fecha de emision de cheque", "Estado de cheque", "Cuenta de origen"};

    public CheckTableModel(List<Check> checkList){
        this.checkList = checkList;
    }
    @Override
    public String getColumnName(int columnIndex){
        return columnNames[columnIndex];
    }
    @Override
    public int getRowCount() {
        return checkList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Check check = checkList.get(rowIndex);
        switch (columnIndex){
            case 0:
                return check.getId();
            case 1:
                return check.getDate();
            case 2:
                return check.getStatus();
            case 3:
                return check.getAccountFrom().getName();
        }
        return null;
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return UUID.class;
            case 1:
                return Date.class;
            case 2, 3:
                return CheckStatus.class;
            case 4:
                return String.class;
        }
        return null;
    }
    public void setCheckList(List<Check> newCheckList) {
        this.checkList = new ArrayList<>(newCheckList);
        fireTableDataChanged();
    }
    public List<Check> getCheckList() {
        return checkList;
    }
}
