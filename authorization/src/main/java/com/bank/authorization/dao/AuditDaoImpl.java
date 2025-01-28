package com.bank.authorization.dao;

import com.bank.authorization.model.Audit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class AuditDaoImpl implements AuditDao {

    @PersistenceContext
    private EntityManager entityManager;

    public AuditDaoImpl() {

    }

    @Override
    public void createAudit(Audit audit) {
        entityManager.persist(audit);
    }
}
