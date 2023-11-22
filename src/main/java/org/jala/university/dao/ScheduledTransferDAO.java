package org.jala.university.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.jala.university.domain.Frequency;
import org.jala.university.domain.ScheduledTransferModel;

import java.util.List;
import java.util.UUID;

/**
 * DAO To manage scheduled transfers.
 */
public class ScheduledTransferDAO extends AbstractDAO <ScheduledTransferModel, UUID>  {

    public ScheduledTransferDAO(EntityManager entityManager) {
        super(UUID.class, ScheduledTransferModel.class, entityManager);
    }

    @Transactional
    public List<ScheduledTransferModel> findByRootAccount(String rootAccount) {
        String jpql = "SELECT st FROM ScheduledTransferModel st WHERE st.accountFrom = :accountFrom";
        TypedQuery<ScheduledTransferModel> query = entityManager.createQuery(jpql, ScheduledTransferModel.class);
        query.setParameter("accountFrom", rootAccount);
        return query.getResultList();
    }

    @Transactional
    public List<ScheduledTransferModel> findDailyTransfers(String accountFrom) {
        String jpql = "SELECT st FROM ScheduledTransferModel st WHERE st.frequency = :daily AND st.accountFrom = :accountFrom";
        TypedQuery<ScheduledTransferModel> query = entityManager.createQuery(jpql, ScheduledTransferModel.class);
        query.setParameter("daily", Frequency.EVERY_DAY);
        query.setParameter("accountFrom", accountFrom);
        return query.getResultList();
    }
    @Transactional
    public List<ScheduledTransferModel> findBiweeklyTransfers(String accountFrom) {
        String jpql = "SELECT st FROM ScheduledTransferModel st WHERE st.frequency = :biweekly AND st.accountFrom = :accountFrom";
        TypedQuery<ScheduledTransferModel> query = entityManager.createQuery(jpql, ScheduledTransferModel.class);
        query.setParameter("biweekly", Frequency.EVERY_FIFTEEN_DAYS);
        query.setParameter("accountFrom", accountFrom);
        return query.getResultList();
    }
    @Transactional
    public List<ScheduledTransferModel> findMonthlyTransfers(String accountFrom) {
        String jpql = "SELECT st FROM ScheduledTransferModel st WHERE st.frequency = :monthly AND st.accountFrom = :accountFrom";
        TypedQuery<ScheduledTransferModel> query = entityManager.createQuery(jpql, ScheduledTransferModel.class);
        query.setParameter("monthly", Frequency.EACH_MONTH);
        query.setParameter("accountFrom", accountFrom);
        return query.getResultList();
    }

    @Transactional
    public List<ScheduledTransferModel> findWeeklyTransfers(String accountFrom) {
        String jpql = "SELECT st FROM ScheduledTransferModel st WHERE st.frequency = :weekly AND st.accountFrom = :accountFrom";
        TypedQuery<ScheduledTransferModel> query = entityManager.createQuery(jpql, ScheduledTransferModel.class);
        query.setParameter("weekly",Frequency.WEEKLY);
        query.setParameter("accountFrom", accountFrom);
        return query.getResultList();
    }

    @Transactional
    public List<ScheduledTransferModel> findAllScheduledTransfers() {
        String jpql = "SELECT st FROM ScheduledTransferModel st";
        TypedQuery<ScheduledTransferModel> query = entityManager.createQuery(jpql, ScheduledTransferModel.class);
        return query.getResultList();
    }

}