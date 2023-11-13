package org.jala.university.dao;

import jakarta.persistence.EntityManager;
import org.jala.university.model.BankUser;
import org.jala.university.model.Transaction;
import org.jala.university.model.TransactionStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class TransactionDAOMock extends TransactionDAO {
    private final Map<UUID, Transaction> transactionMap = new HashMap<>();
    UserDAOMock userDAOMock = new UserDAOMock();
    List<BankUser> bankUsers = userDAOMock.getMockBankUsers();

    public TransactionDAOMock() {
        super(null);
    }
    private void saveTransaction(Transaction transaction) {
        transactionMap.put(transaction.getId(), transaction);
    }
    public TransactionStatus transferExecution(Transaction transaction){
        if (transaction.getAmount() <= 0 ) {
            transaction.setStatus(TransactionStatus.FAILED);
        } else if (transaction.getAmount() > transaction.getAccountFrom().getBalance()){
            transaction.setStatus(TransactionStatus.FAILED);
        } else {
            Long amount = transaction.getAmount();

            for (BankUser user: bankUsers){
                if (user.getAccount().getAccountNumber().equals(transaction.getAccountFrom().getAccountNumber())){
                    Long accountFromNewBalance = user.getAccount().getBalance() - amount;
                    user.getAccount().setBalance(accountFromNewBalance);
                }else if (user.getAccount().getAccountNumber().equals(transaction.getAccountTo().getAccountNumber())){
                    Long accountToNewBalance = user.getAccount().getBalance() + amount;
                    user.getAccount().setBalance(accountToNewBalance);
                }
            }
            for (BankUser user: bankUsers){
                System.out.println("Balance de " + user.getFirstName() + "= " + user.getAccount().getBalance());
            }

            saveTransaction(transaction);
            return TransactionStatus.COMPLETED;
        }
        saveTransaction(transaction);
        return TransactionStatus.FAILED;
    }
    @Override
    public Transaction findOne(UUID id) {
        return transactionMap.get(id);
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactionMap.values());
    }

    @Override
    public Transaction create(Transaction transaction) {
        UUID id = UUID.randomUUID();
        transaction.setId(id);
        transactionMap.put(id, transaction);
        return transaction;
    }

    @Override
    public Transaction update(Transaction transaction) {
        UUID id = transaction.getId();
        if (transactionMap.containsKey(id)) {
            transactionMap.put(id, transaction);
            return transaction;
        }
        return null;
    }

    @Override
    public void delete(Transaction transaction) {
        transactionMap.remove(transaction.getId());
    }

    @Override
    public void deleteById(UUID transactionId) {
        transactionMap.remove(transactionId);
    }

    @Override
    public List<Transaction> getTransactionsWithAccountNumber(String accountNumber) {
        return transactionMap.values().stream()
                .filter(transaction -> transaction.getAccountFrom().getAccountNumber().equals(accountNumber) ||
                        transaction.getAccountTo().getAccountNumber().equals(accountNumber))
                .collect(Collectors.toList());
    }
    List<BankUser> getBankUsers(){
        return this.bankUsers;
    }
}
