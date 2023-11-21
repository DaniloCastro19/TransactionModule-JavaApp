package org.jala.university.domain;

import org.jala.university.model.Account;
import org.jala.university.model.Check;
import org.jala.university.model.CheckStatus;
import org.jala.university.model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CheckServiceTest {
  private CheckModule checkModule;
  private UserModule userModule;
  private CheckService checkService;

  @BeforeEach
  public void setup(){
    checkModule = mock(CheckModule.class);
    userModule = mock(UserModule.class);
    checkService = new CheckService(checkModule, userModule);
  }

  @Test
  void validateCheck_checkExists() {
    UUID checkId = UUID.randomUUID();
    Check check = Check.builder().build();
    when(checkModule.findCheckWithUUID(checkId)).thenReturn(Collections.singletonList(check));
    when(checkModule.hasSufficientFunds(check)).thenReturn(true);

    String result = checkService.validateCheck(checkId);

    assertEquals("El cheque ha sido procesado exitosamente.", result);
    verify(checkModule).createCheck(check);
  }

  @Test
  void validateCheck_checkDoesNotExist() {
    UUID checkId = UUID.randomUUID();
    when(checkModule.findCheckWithUUID(checkId)).thenReturn(List.of());

    String result = checkService.validateCheck(checkId);

    assertEquals("El cheque no existe.", result);
  }

  @Test
  void createCheck_sufficientFunds() {
    UUID accountId = UUID.randomUUID();
    long amount = 100L;
    Account account = Account.builder().balance(amount + 50).build();
    when(userModule.findUserById(accountId)).thenReturn(account);

    CheckStatus result = checkService.createCheck(accountId, amount, "beneficiary", Currency.USD, new Date());

    assertEquals(CheckStatus.ACTIVE, result);
    verify(userModule, times(1)).update(account);
  }

  @Test
  void createCheck_insufficientFunds() {
    UUID accountId = UUID.randomUUID();
    long amount = 100L;
    Account account = Account.builder().balance(amount - 50).build();
    when(userModule.findUserById(accountId)).thenReturn(account);

    CheckStatus result = checkService.createCheck(accountId, amount, "beneficiary", Currency.USD, new Date());

    assertEquals(CheckStatus.CANCELED, result);
    verify(userModule, never()).update(account);
  }
}