package org.jala.university.domain;

import java.util.Calendar;
import java.util.Date;

public enum DateRangeOption {
  LAST_WEEK("1 semana") {
    @Override
    public Date[] getStartAndEndDate() {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.WEEK_OF_YEAR, -1);
      Date startDate = cal.getTime();
      cal.add(Calendar.WEEK_OF_YEAR, 1);
      Date endDate = cal.getTime();
      return new Date[]{startDate, endDate};
    }
  },
  LAST_MONTH("1 mes") {
    @Override
    public Date[] getStartAndEndDate() {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.MONTH, -1);
      Date startDate = cal.getTime();
      cal.add(Calendar.MONTH, 1);
      Date endDate = cal.getTime();
      return new Date[]{startDate, endDate};
    }
  },
  CUSTOM_DATE("Rango de fechas") {
    @Override
    public Date[] getStartAndEndDate(Date startDate, Date endDate) {
      return new Date[]{startDate, endDate};
    }
  };

  private final String label;

  DateRangeOption(String label) {
    this.label = label;
  }

  public Date[] getStartAndEndDate() {
    return null;
  }

  public Date[] getStartAndEndDate(Date startDate, Date endDate) {
    return getStartAndEndDate();
  }

  public static DateRangeOption getByLabel(String label) {
    for (DateRangeOption option : values()) {
      if (option.label.equalsIgnoreCase(label)) {
        return option;
      }
    }
    return null;
  }
}
