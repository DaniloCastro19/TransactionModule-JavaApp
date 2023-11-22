package org.jala.university.domain;

import org.jala.university.model.Check;
import org.jala.university.model.Transaction;
import org.jala.university.model.TransactionType;

import javax.swing.*;
import java.util.*;

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
        public List<Check> getChecks(CheckModule checkModule, Map<String, Object> value) {
            String parameter = (String) value.get("ACCOUNT_NUMBER");
            return checkModule.findCheckWithAccountNumber(parameter);
        }

        @Override
        public JComponent[] getComponent(HistoryViewInterface view) {
            return new JComponent[]{view.getAccountNumberInput()};
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
        public List<Check> getChecks(CheckModule checkModule, Map<String, Object> value) {
            String parameter = (String) value.get("ACCOUNT_NAME");
            return checkModule.findCheckWithName(parameter);
        }

        @Override
        public JComponent[] getComponent(HistoryViewInterface view) {
            return new JComponent[]{view.getNameInput()};
        }
    },
    TRANSACTION_TYPE("Filtrar por Tipo de Transaccion") {
        @Override
        public String getValue(JComponent... component) {
            if (component[0] instanceof JComboBox<?>) {
                JComboBox textField = (JComboBox) component[0];
                return (String) textField.getSelectedItem();
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
        public List<Check> getChecks(CheckModule checkModule, Map<String, Object> value) {
            return null;
        }

        @Override
        public JComponent[] getComponent(HistoryViewInterface view) {
            return new JComponent[]{view.getTransactionTypeComboBox()};
        }
    },
    TRANSACTION_AMOUNT("Filtrar por Monto") {
        @Override
        public String getValue(JComponent... component) {
            if (component[0] instanceof JComboBox<?>) {
                JComboBox textField = (JComboBox) component[0];
                return (String) textField.getSelectedItem();
            }
            return null;
        }

        @Override
        public List<Transaction> getTransactions(TransactionModule transactionModule, Map<String, Object> value) {
            String parameter = (String) value.get("TRANSACTION_AMOUNT");
            return transactionModule.finTransactionsWithTransactionAmount(parameter.equals("Maximo"));
        }

        @Override
        public List<Check> getChecks(CheckModule checkModule, Map<String, Object> value) {
            String parameter = (String) value.get("TRANSACTION_AMOUNT");
            return checkModule.findChecksWithAmount(parameter.equals("Maximo"));
        }

        @Override
        public JComponent[] getComponent(HistoryViewInterface view) {
            return new JComponent[]{view.getAmountInput()};
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
            String dateFilter = (String) parameters.get("TRANSACTION_DATE");
            DateRangeOption dateRangeOption = DateRangeOption.getByLabel(dateFilter);

            Date[] dates;
            if (dateRangeOption == DateRangeOption.CUSTOM_DATE) {
                Date startDate = (Date) parameters.get("startDate");
                Date endDate = (Date) parameters.get("endDate");
                dates = dateRangeOption.getStartAndEndDate(startDate, endDate);
            } else {
                dates = dateRangeOption.getStartAndEndDate();
            }

            return transactionModule.findTransactionWithDate(dates[0], dates[1]);
        }

        @Override
        public List<Check> getChecks(CheckModule checkModule, Map<String, Object> parameters) {
            String dateFilter = (String) parameters.get("TRANSACTION_DATE");
            DateRangeOption dateRangeOption = DateRangeOption.getByLabel(dateFilter);
            Date[] dates;
            if (dateRangeOption == DateRangeOption.CUSTOM_DATE) {
                Date startDate = (Date) parameters.get("startDate");
                Date endDate = (Date) parameters.get("endDate");
                dates = dateRangeOption.getStartAndEndDate(startDate, endDate);
            } else {
                dates = dateRangeOption.getStartAndEndDate();
            }
            return checkModule.findCheckWithDate(dates[0], dates[1]);
        }

        @Override
        public JComponent[] getComponent(HistoryViewInterface view) {
            return new JComponent[]{
                    view.getStartDateComboBox(),
                    view.getEndDateComboBox(),
                    view.getDateRangeComboBox()
            };
        }
    };

    private final String name;

    HistoryFilters(String name) {
        this.name = name;
    }

    public abstract String getValue(JComponent... components);

    public abstract List<Transaction> getTransactions(TransactionModule transactionModule, Map<String, Object> parameters);

    public abstract List<Check> getChecks(CheckModule checkModule, Map<String, Object> parameters);

    public abstract JComponent[] getComponent(HistoryViewInterface view);
}
