package com.ccs.blockchain.service;

import com.alibaba.fastjson.JSON;
import com.ccs.blockchain.entity.CryptocurrenciesData;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
public class StoreCyptoService {

    //@Autowired


    @Autowired
    MongoTemplate mongoTemplate;

    @PostConstruct
    public void insert(){
        JsoupDataUtil jsoupDataUtil = new JsoupDataUtil();
        List<CryptocurrenciesData> list = jsoupDataUtil.jsoupSpider();
        //mongoTemplate.insert(cryptocurrenciesData);
        list.forEach(data->{
            Query query = new Query();
            query.addCriteria(Criteria.where("simpleName").is(data.getSimpleName()));
            String s = JSON.toJSONString(data);
            Update update = new BasicUpdate(Document.parse(s));
            mongoTemplate.upsert(query,update,CryptocurrenciesData.class);
        });

        Map<String, String> rate = jsoupDataUtil.getRate();


    }
}
