package org.jala.university.domain;

import org.jala.university.model.Check;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface CheckModule {
  void createCheck(Check check);

  List<Check> findCheckWithUUID(UUID checkId);

  List<Check> findCheckWithAccountNumber(String parameter);

  List<Check> findCheckWithName(String parameter);

  List<Check> findCheckWithDate(Date startDate, Date endDate);

  List<Check> findChecksWithAmount(boolean isAscending);

  void delete(Check check);

}
