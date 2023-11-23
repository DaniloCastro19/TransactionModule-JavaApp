package org.jala.university.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jala.university.dao.EntityDAO;
import org.jala.university.model.Account;
import org.jala.university.model.Currency;
import org.jala.university.model.TransactionStatus;

import java.util.UUID;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Model represents a scheduled transfer.
 */
@Entity
@Table(name = "scheduled_transfer")
@Getter
@Setter
@Builder
public class ScheduledTransferModel implements EntityDAO<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private Long amount;

    @Column
    private Account accountFrom;

    @Column
    private Account accountTo;

    @Column
    private Currency currency;

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    private TransactionStatus status;

    @Column
    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    @Column
    private int numOccurrences;

    @Column
    private String details;

    public List<Date> calculateDatesTransfer() {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int numOccursRemaining = numOccurrences;

        // Calculate next transfer date outside the loop.
        Calendar nextTransferDate = frequency.getCalendarDate(date);

        while (numOccursRemaining > 0) {
            dates.add(nextTransferDate.getTime());

            // Calculate the next transfer date for all occurrences.
            nextTransferDate = frequency.getCalendarDate(nextTransferDate.getTime());

            numOccursRemaining--;
        }
        return dates;
    }
}