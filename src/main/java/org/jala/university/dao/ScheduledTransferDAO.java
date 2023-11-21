package org.jala.university.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
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
    public List<ScheduledTransferModel> findByDestinationAccount(String destinationAccount) {
        String jpql = "SELECT st FROM ScheduledTransferModel st WHERE st.accountTo = :accountTo";
        TypedQuery<ScheduledTransferModel> query = entityManager.createQuery(jpql, ScheduledTransferModel.class);
        query.setParameter("accountTo", destinationAccount);
        return query.getResultList();
    }

    @Transactional
    public List<ScheduledTransferModel> findAllScheduledTransfers() {
        String jpql = "SELECT st FROM ScheduledTransferModel st";
        TypedQuery<ScheduledTransferModel> query = entityManager.createQuery(jpql, ScheduledTransferModel.class);
        return query.getResultList();
    }
}