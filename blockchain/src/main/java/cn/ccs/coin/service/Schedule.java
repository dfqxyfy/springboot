package cn.ccs.coin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@EnableScheduling
@Component
public class Schedule {

    @Autowired
    StoreCyptoService storeCyptoService;


    @Scheduled(cron="0 0/1 * * * ?")
    public void exe(){
        storeCyptoService.updateCryptocurrencies();
        storeCyptoService.updateExchanges();
    }
}
