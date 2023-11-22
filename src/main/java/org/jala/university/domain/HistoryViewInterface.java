package org.jala.university.domain;

import javax.swing.JComponent;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;

public interface HistoryViewInterface {
    void initializeUI();
    void addTitleLabel();
    void addSearchPanel();
    void addTableScrollPane();
    void performSearch(ActionEvent e);
    void updateTableModel(List<?> dataList);
    void setupDynamicFilters();
    void clearTextFields();
    void updateEndDateComboBox(Date startDate);
    JComponent getAccountNumberInput();
    JComponent getNameInput();
    JComponent getAmountInput();
    JComponent getStartDateComboBox();
    JComponent getEndDateComboBox();
    JComponent getDateRangeComboBox();
    JComponent getTransactionTypeComboBox();
}
