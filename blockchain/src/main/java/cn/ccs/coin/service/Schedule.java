package cn.ccs.coin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@EnableScheduling
@Component
@Slf4j
public class Schedule {

    @Autowired
    StoreCyptoService storeCyptoService;


    @Scheduled(cron="0 0/1 * * * ?")
    public void exe(){
        log.info("start update data");
        storeCyptoService.updateCryptocurrencies();
        storeCyptoService.updateExchanges();
    }
}
