package com.bank.authorization.service;

import com.bank.authorization.dao.AuditDao;
import com.bank.authorization.model.Audit;
import com.bank.authorization.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@Aspect
public class AuditAspect {

    private final AuditDao auditDao;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuditAspect(AuditDao auditDao) {
        this.auditDao = auditDao;
        this.objectMapper = new ObjectMapper();
    }

    @Before("execution(" +
                    "public void com.bank.authorization.dao.UserDaoImpl.createUser(" +
                    "com.bank.authorization.model.User)" +
            ")")
    @Transactional
    public void beforeCreateUserAdvice(JoinPoint joinPoint) throws JsonProcessingException {
        Object[] args = joinPoint.getArgs();
        createAudit(args, "create");
    }

    @Before("execution(" +
            "public void com.bank.authorization.dao.UserDaoImpl.updateUser(" +
            "com.bank.authorization.model.User, com.bank.authorization.model.User)" +
            ")")
    @Transactional
    public void beforeUpdateUserAdvice(JoinPoint joinPoint) throws JsonProcessingException {
        Object[] args = joinPoint.getArgs();
        createAudit(args, "update");
    }

    @Before("execution(" +
            "public void com.bank.authorization.dao.UserDaoImpl.deleteUser(" +
            "com.bank.authorization.model.User)" +
            ")")
    @Transactional
    public void beforeDeleteUserAdvice(JoinPoint joinPoint) throws JsonProcessingException {
        Object[] args = joinPoint.getArgs();
        createAudit(args, "delete");
    }

    private void createAudit(Object[] args, String action) throws JsonProcessingException {

        long millis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(millis);

        User user = (User) args[0];
        String jsonUser = objectMapper.writeValueAsString(user);
        String createdBy = user.getProfileId().toString();

        Audit audit = new Audit(
                "User",
                action,
                createdBy,
                (!action.equals("create")) ? createdBy : null,
                timestamp,
                (!action.equals("create")) ? timestamp : null,
                (action.equals("update")) ? jsonUser : null,
                (action.equals("update")) ? objectMapper.writeValueAsString((User) args[1]) : jsonUser
        );

        auditDao.createAudit(audit);
    }
}
