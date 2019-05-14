package cn.ccs.coin.service;

import com.alibaba.fastjson.JSON;
import cn.ccs.coin.entity.CryptocurrenciesData;
import cn.ccs.coin.entity.ExchangesData;
import cn.ccs.coin.entity.RateData;
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

@Service
public class StoreCyptoService {

    //@Autowired


    @Autowired
    MongoTemplate mongoTemplate;

    @PostConstruct
    public void updateCryptocurrencies(){
        JsoupCryptoDataUtil jsoupDataUtil = new JsoupCryptoDataUtil();
        List<CryptocurrenciesData> list = jsoupDataUtil.jsoupSpider();
        //mongoTemplate.insert(cryptocurrenciesData);
        list.forEach(data->{
            Query query = new Query();
            query.addCriteria(Criteria.where("simpleName").is(data.getSimpleName()));
            String s = JSON.toJSONString(data);
            Update update = new BasicUpdate(Document.parse(s));
            mongoTemplate.upsert(query,update,CryptocurrenciesData.class);
        });

        final List<RateData> rateDataList = jsoupDataUtil.getRate();
        rateDataList.forEach(rateData -> {
            Query query = new Query();
            query.addCriteria(Criteria.where("coin").is(rateData.getCoin()));
            String s = JSON.toJSONString(rateData);
            Update update = new BasicUpdate(Document.parse(s));
            mongoTemplate.upsert(query,update,RateData.class);
        });


    }


    @PostConstruct
    public void updateExchanges(){
        JsoupExchangesDataUtil jsoupExchangesDataUtil = new JsoupExchangesDataUtil();
        List<ExchangesData> list = jsoupExchangesDataUtil.jsoupSpider();
        //mongoTemplate.insert(cryptocurrenciesData);
        list.forEach(data->{
            Query query = new Query();
            query.addCriteria(Criteria.where("name").is(data.getName()));
            String s = JSON.toJSONString(data);
            Update update = new BasicUpdate(Document.parse(s));
            mongoTemplate.upsert(query,update,ExchangesData.class);
        });

    }
}
