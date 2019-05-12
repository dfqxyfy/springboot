package com.ccs.coin.service;

import com.ccs.coin.entity.CryptocurrenciesData;
import com.ccs.coin.entity.ExchangesData;
import com.ccs.coin.entity.RateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataFindService {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<CryptocurrenciesData> getCryptoData(){
        Query query = new Query();
        return mongoTemplate.find(query,CryptocurrenciesData.class);
    }

    public List<ExchangesData> getExchangesData(){
        Query query = new Query();
        return mongoTemplate.find(query,ExchangesData.class);
    }

    public List<RateData> getRateData(){
        Query query = new Query();
        return mongoTemplate.find(query,RateData.class);
    }
}
