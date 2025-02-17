package org.jala.university.validationMonthlyAmount;

import org.jala.university.model.BankUser;
import org.jala.university.model.Currency;
import org.jala.university.model.Account;
import org.jala.university.model.Transaction;
import javax.swing.JOptionPane;
import org.jala.university.dao.TransactionDAO;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class representing the alert manager for monitoring high monthly transactions.
 * @author Gabriel Mendoza
 */
public class HighMonthlyAmountAlert {
    private final double LIMIT_MONTHLY_BOLIVIANOS = 20000;
    private final double LIMIT_MONTHLY_DOLLARS = 3000;
    private final double LIMIT_MONTHLY_EUROS = 4000;

    private final TransactionDAO transactionDAO;

    public HighMonthlyAmountAlert(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public boolean hasExceededMonthlyThreshold(Transaction transaction, BankUser bankUser, Account account) {

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        Date endDate = calendar.getTime();

        List<Transaction> currentMonthTransactions = transactionDAO.getTransactionsForCurrentMonth(startDate, endDate, account);

        double totalMonthlyAmount = currentMonthTransactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();

        boolean hasExceeded = totalMonthlyAmount + transaction.getAmount() > getMonthlyLimit(account.getCurrency());

        return hasExceeded;
    }
    public void showMessage(Transaction transaction, BankUser bankUser, Account account) {
        boolean hasExceeded = hasExceededMonthlyThreshold(transaction, bankUser, account);

        if (hasExceeded) {
            String customerName = bankUser.getFirstName() + " " + bankUser.getLastName();
            String accountNumber = account.getAccountNumber();
            double totalMonthlyAmount = transaction.getAmount();
            Currency currency = account.getCurrency();

            showAlert(customerName, accountNumber, totalMonthlyAmount, currency);
        }
    }
    private double getMonthlyLimit(Currency currency) {
        return switch (currency) {
            case BOB -> LIMIT_MONTHLY_BOLIVIANOS;
            case USD -> LIMIT_MONTHLY_DOLLARS;
            case EUR -> LIMIT_MONTHLY_EUROS;
        };
    }
    private void showAlert(String customerName, String accountNumber, double totalMonthlyAmount, Currency currency) {
        String message = "ALERTA: Transaccion mensual elevada\n" +
                "Cliente: " + customerName + "\n" +
                "Numero de Cuenta: " + accountNumber + "\n" +
                "Monto Total: " + totalMonthlyAmount + " " + currency.getSymbol() + "(" + currency.name() + ")" + "\n" +
                "Fecha: " + new Date();

        JOptionPane.showMessageDialog(null, message, "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
    }
}
