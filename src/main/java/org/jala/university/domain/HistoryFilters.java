package org.jala.university.domain;

import org.jala.university.model.Transaction;
import org.jala.university.model.TransactionType;
import org.jala.university.presentation.HistoryView;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public enum HistoryFilters {
    ACCOUNT_NUMBER("Filtrar por Número de cuenta") {
        @Override
        public String getValue(JComponent... component) {
            if (component[0] instanceof JTextField) {
                JTextField textField = (JTextField) component[0];
                return textField.getText();
            }
            return null;
        }


        @Override
        public List<Transaction> getTransactions(TransactionModule transactionModule, Map<String, Object> value) {
            String parameter = (String) value.get("ACCOUNT_NUMBER");
            return transactionModule.findTransactionsWithAccountNumber(parameter);
        }

        @Override
        public JComponent[] getComponent(HistoryView historyView) {
            return new JComponent[] {historyView.getAccountNumberInput()};
        }
    },
    ACCOUNT_NAME("Filtrar por Nombre y/o Apellidos") {
        @Override
        public String getValue(JComponent... component) {
            if (component[0] instanceof JTextField) {
                JTextField textField = (JTextField) component[0];
                return textField.getText();
            }
            return null;
        }

        @Override
        public List<Transaction> getTransactions(TransactionModule transactionModule, Map<String, Object> value) {
            String parameter = (String) value.get("ACCOUNT_NAME");
            return transactionModule.findTransactionsWithNameOrLastName(parameter);
        }

        @Override
        public JComponent[] getComponent(HistoryView historyView) {
            return new JComponent[] {historyView.getNameInput()};
        }
    },
    TRANSACTION_TYPE("Filtrar por Tipo de Transaccion") {
        @Override
        public String getValue(JComponent... component) {
            if (component[0] instanceof JComboBox<?>) {
                JComboBox textField = (JComboBox) component[0];
                return (String)textField.getSelectedItem();
            }
            return null;
        }

        @Override
        public List<Transaction> getTransactions(TransactionModule transactionModule, Map<String, Object> value) {
            String parameter = (String) value.get("TRANSACTION_TYPE");
            TransactionType values = TransactionType.valueOf(parameter.toUpperCase());
            return transactionModule.findTransactionWithType(values);
        }

        @Override
        public JComponent[] getComponent(HistoryView historyView) {
            return new JComponent[] {historyView.getTransactionTypeFilterComboBox()};
        }
    },
    TRANSACTION_AMOUNT("Filtrar por Monto") {
        @Override
        public String getValue(JComponent... component) {
            if (component[0] instanceof JComboBox<?>) {
                JComboBox textField = (JComboBox) component[0];
                return (String)textField.getSelectedItem();
            }
            return null;
        }

        @Override
        public List<Transaction> getTransactions(TransactionModule transactionModule, Map<String, Object> value) {
            String parameter = (String) value.get("TRANSACTION_AMOUNT");
            if (parameter.equals("Maximo")){
                return transactionModule.finTransactionsWithTransactionAmount(true);
            } else {
                return transactionModule.finTransactionsWithTransactionAmount(false);
            }
        }

        @Override
        public JComponent[] getComponent(HistoryView historyView) {
            return new JComponent[] {historyView.getAmountInput()};
        }
    },
    TRANSACTION_DATE("Filtrar por Fechas") {
        @Override
        public String getValue(JComponent... components) {
            if (components.length >= 2 && components[2] instanceof JComboBox) {
                JComboBox<?> dateRangeComboBox = (JComboBox<?>) components[2];
                return (String) dateRangeComboBox.getSelectedItem();
            }
            return null;
        }

        @Override
        public List<Transaction> getTransactions(TransactionModule transactionModule, Map<String, Object> parameters) {
            Date startDate = (Date) parameters.get("startDate");
            Date endDate = (Date) parameters.get("endDate");
            String dateFilter = (String) parameters.get("TRANSACTION_DATE");
            if (startDate != null && endDate != null) {
                return transactionModule.findTransactionWithDate(startDate, endDate);
            } else if ("1 semana".equals(dateFilter)){
                Calendar cal = Calendar.getInstance();
                endDate = cal.getTime();
                cal.add(Calendar.DAY_OF_MONTH, -7);
                startDate = cal.getTime();
            } else if ("1 mes".equals(dateFilter)) {
                Calendar cal = Calendar.getInstance();
                endDate = cal.getTime();
                cal.set(Calendar.DAY_OF_MONTH, 1);
                startDate = cal.getTime();
            }
            return transactionModule.findTransactionWithDate(startDate, endDate);

        }

        @Override
        public JComponent[] getComponent(HistoryView historyView) {
            return new JComponent[] {historyView.getStartDateComboBox(), historyView.getEndDateComboBox(), historyView.getDateRangeComboBox() };
        }
    };

    private final String name;

    HistoryFilters(String name) {
        this.name = name;
    }

    public abstract String getValue(JComponent... components);

    public abstract List<Transaction> getTransactions(TransactionModule transactionModule, Map<String, Object> parameters);

    public abstract JComponent[] getComponent(HistoryView historyView);
}
