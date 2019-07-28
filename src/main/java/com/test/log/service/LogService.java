package com.test.log.service;

import com.test.log.dto.Pageable;
import com.test.log.model.Log;
import com.test.log.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogService {
    @Autowired
    private LogRepository repository;
    @Value("${test}")
    private String test;
    @Value("${db.password}")
    private String dbPassword;

    public Log save(Log log) {
        return repository.save(log);
    }

    public List<Log> find(Long appId, Pageable pageable, Date from, Date to, String containedWord) {
        return repository.find(appId, pageable, from, to, containedWord);
    }
}
