package com.test.log.controller;

import com.test.log.aspect.AppAction;
import com.test.log.dto.Pageable;
import com.test.log.model.Log;
import com.test.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class LogController {
    @Autowired
    private LogService service;

    @AppAction
    @PostMapping("/api/log")
    public Log createLog(@RequestBody Log log) {
        return service.save(log);
    }

    @AppAction
    @GetMapping("/api/log/{appId}")
    public List<Log> getLogs(@PathVariable Long appId,
                             Pageable pageable,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to,
                             @RequestParam(required = false, name = "contains") String containedWord) {


        return service.find(appId, pageable, from, to, containedWord);
    }
}
