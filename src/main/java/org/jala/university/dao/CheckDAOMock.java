package org.jala.university.dao;

import org.jala.university.model.Check;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheckDAOMock extends CheckDAO {
  private final Map<UUID, Check> checkMap = new HashMap<>();

  public CheckDAOMock() {
    super(null);
  }

  @Override
  public Check create(Check check) {
    checkMap.put(check.getId(), check);
    return check;
  }

  @Override
  public List<Check> getCheckWithUUID(UUID ID) {
    return checkMap.values().stream()
      .filter(check -> check.getId().equals(ID))
      .collect(Collectors.toList());
  }

  @Override
  public List<Check> getChecksWithAccountNumber(String accountNumber) {
    return checkMap.values().stream()
      .filter(check -> check.getAccountFrom().getAccountNumber().contains(accountNumber))
      .collect(Collectors.toList());
  }

  @Override
  public List<Check> getChecksWithName(String name) {
    return checkMap.values().stream()
      .filter(check -> check.getAccountFrom().getName().contains(name))
      .collect(Collectors.toList());
  }

  @Override
  public List<Check> getChecksWithDate(Date startDate, Date endDate) {
    return checkMap.values().stream()
      .filter(check -> !check.getDate().before(startDate) && !check.getDate().after(endDate))
      .collect(Collectors.toList());
  }

  @Override
  public List<Check> getChecksWithAmount(boolean orderByDescending) {
    Stream<Check> stream = checkMap.values().stream();
    if (orderByDescending) {
      return stream.sorted(Comparator.comparing(Check::getAmount).reversed())
        .collect(Collectors.toList());
    } else {
      return stream.sorted(Comparator.comparing(Check::getAmount))
        .collect(Collectors.toList());
    }
  }
}
