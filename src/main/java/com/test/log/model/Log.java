package com.test.log.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "app_action_log")
public class Log {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "app_id")
    private String appId;
    @Column(name = "tag")
    private String tag;
    @Column(name = "message")
    private String message;
    @Column(name = "timestamp")
    private Date timestamp;
}