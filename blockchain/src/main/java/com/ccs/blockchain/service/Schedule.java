package com.ccs.blockchain.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@EnableScheduling
@Component
public class Schedule {



    @PostConstruct
    public void exe(){

    }
}
