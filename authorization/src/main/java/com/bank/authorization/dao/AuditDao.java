package com.bank.authorization.dao;

import com.bank.authorization.model.Audit;

public interface AuditDao {

    void createAudit(Audit audit);
}
