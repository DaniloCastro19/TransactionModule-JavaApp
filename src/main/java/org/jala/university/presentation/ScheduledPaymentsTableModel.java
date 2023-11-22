package org.jala.university.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.jala.university.domain.ScheduledTransferModel;

public class ScheduledPaymentsTableModel extends AbstractTableModel {
    private String[] header = {"Cuenta de origen","Cuenta de destino", "Fecha", "Monto"};
    private List<ScheduledTransferModel> scheduledTransferModelList;
    public ScheduledPaymentsTableModel(List<ScheduledTransferModel> sheduledTransferModelList) {
        this.scheduledTransferModelList = sheduledTransferModelList;
    }
    @Override
    public String getColumnName(int columnIndex) {
        return header[columnIndex];
    }

    @Override
    public int getRowCount() {
        return scheduledTransferModelList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ScheduledTransferModel scheduledTransfer = scheduledTransferModelList.get(rowIndex);
        switch (columnIndex){
            case 0:
                return scheduledTransfer.getAccountFrom().getAccountNumber();
            case 1:
                return  scheduledTransfer.getAccountTo().getAccountNumber();
            case 2:
                return scheduledTransfer.getDate();
            case 3:
                return scheduledTransfer.getAmount();
            default:
                return null;
        }
    }

    public void setScheduledTransferList(List<ScheduledTransferModel> newTransactionList) {
        this.scheduledTransferModelList = new ArrayList<>(newTransactionList);
        fireTableDataChanged();
    }

}
