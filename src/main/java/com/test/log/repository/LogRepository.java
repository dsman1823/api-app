package com.test.log.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.test.log.dto.Pageable;
import com.test.log.model.Log;
import com.test.log.model.QLog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public class LogRepository {
    private SessionFactory sessionFactory;

    @Autowired
    public LogRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Log> find(Long appId, Pageable pageable, Date from, Date to, String containedWord) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        JPAQuery<Log> query = new JPAQuery<>(entityManager);

        handlePagination(query, pageable);
        handleConditions(query, from, to, containedWord);

        return query.from(QLog.log).fetch();
    }

    private void handleConditions(JPAQuery<Log> query, Date from, Date to, String containedWord) {
        if (Objects.nonNull(from)) {
            query.where(QLog.log.timestamp.after(from));
        }

        if (Objects.nonNull(to)) {
            query.where(QLog.log.timestamp.before(to));
        }

        if (Objects.nonNull(containedWord)) {
            query.where(QLog.log.message.contains(containedWord));
        }
    }

    private void handlePagination(JPAQuery<Log> query, Pageable pageable) {
        query.offset(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize());
    }

    public Log save(Log log) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        log.setTimestamp(new Date());
        session.save(log);

        session.getTransaction().commit();

        return log;
    }
}