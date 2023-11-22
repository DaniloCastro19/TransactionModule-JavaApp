package org.jala.university.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import jakarta.transaction.Transactional;
import org.jala.university.model.Account;
import org.jala.university.model.Check;
import org.jala.university.model.CheckStatus;
import org.jala.university.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckService {

  private CheckModule checkModule;
  private UserModule userModule;

  @Autowired
  public CheckService(CheckModule checkModule, UserModule userModule) {
    this.checkModule = checkModule;
    this.userModule = userModule;
  }
  @Transactional
  public String validateCheck(UUID checkId) {
    List<Check> checks = checkModule.findCheckWithUUID(checkId);
    if (checks.isEmpty()) {
      return "El cheque no existe.";
    }

    Check check = checks.get(0);

    if (!checkModule.hasSufficientFunds(check)) {
      return "No hay suficientes fondos en la cuenta que generó el cheque.";
    }

    checkModule.createCheck(check);
    return "El cheque ha sido procesado exitosamente.";
  }
  @Transactional
  public CheckStatus createCheck(UUID accountId, Long amount, String beneficiaryName, Currency currency, Date date) {
    Account account = userModule.findUserById(accountId);

    if (account.getBalance() < amount) {
      return CheckStatus.CANCELED;
    }

    account.setBalance(account.getBalance() - amount);
    userModule.update(account);

    // Retrieve the account again to get the updated balance
    account = userModule.findUserById(accountId);

    Check newCheck = Check.builder()
            .id(UUID.randomUUID())
            .date(date)
            .amount(amount)
            .currency(currency)
            .beneficiaryName(beneficiaryName)
            .accountFrom(account)
            .status(CheckStatus.ACTIVE)
            .build();

    checkModule.createCheck(newCheck);
    return newCheck.getStatus();
  }}