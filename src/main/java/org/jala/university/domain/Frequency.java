package org.jala.university.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.Getter;

/**
 * Enumeration representing different frequencies for scheduled transfers.
 * Each frequency has a custom name associated with it.
 */
@Getter
public enum Frequency {
  EACH_MONTH("Cada mes") {
    @Override
    public Calendar getCalendarDate(Date date) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(Calendar.MONTH, 1);
      return calendar;
    }

    @Override
    public List<ScheduledTransferModel> findTransfers(ScheduledTransferModule module, String accountFromNumber) {
      List<ScheduledTransferModel> result;
      result = module.findMonthlyTransfers(accountFromNumber);
      return result;
    }
  },
  EVERY_FIFTEEN_DAYS("Cada 15 días") {
    @Override
    public Calendar getCalendarDate(Date date) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(Calendar.DAY_OF_MONTH, 15);
      return calendar;
    }

    @Override
    public List<ScheduledTransferModel> findTransfers(ScheduledTransferModule module, String accountFromNumber) {
      List<ScheduledTransferModel> result;
      result = module.findBiweeklyTransfers(accountFromNumber);
      return result;
    }
  },
  WEEKLY("Cada semana") {
    @Override
    public Calendar getCalendarDate(Date date) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(Calendar.WEEK_OF_MONTH, 1);
      return calendar;
    }

    @Override
    public List<ScheduledTransferModel> findTransfers(ScheduledTransferModule module, String accountFromNumber) {
      List<ScheduledTransferModel> result;
      result = module.findWeeklyTransfers(accountFromNumber);
      return result;
    }
  },
  EVERY_DAY("Cada día") {
    @Override
    public Calendar getCalendarDate(Date date) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(Calendar.DAY_OF_MONTH, 1);
      return calendar;
    }

    @Override
    public List<ScheduledTransferModel> findTransfers(ScheduledTransferModule module, String accountFromNumber) {
      List<ScheduledTransferModel> result;
      result = module.findDailyTransfers(accountFromNumber);
      return result;
    }
  };


  private final String name;

  /**
   * Constructor for Frequency enum.
   *
   * @param name The custom name associated with the frequency.
   */
  Frequency(String name) {
    this.name = name;

  }

  public abstract Calendar getCalendarDate(Date date);

  public abstract List<ScheduledTransferModel> findTransfers(ScheduledTransferModule module, String accountFromNumber);
}