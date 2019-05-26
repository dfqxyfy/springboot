package cn.ccs.coin.service;

import cn.ccs.coin.entity.TotalData;
import com.alibaba.fastjson.JSON;
import cn.ccs.coin.entity.CryptocurrenciesData;
import cn.ccs.coin.entity.ExchangesData;
import cn.ccs.coin.entity.RateData;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Service
public class StoreCyptoService {

    //@Autowired


    @Autowired
    private MongoTemplate mongoTemplate;

    //@PostConstruct
    public void updateCryptocurrencies(){
        JsoupCryptoDataUtil jsoupDataUtil = new JsoupCryptoDataUtil();
        List<CryptocurrenciesData> list = jsoupDataUtil.jsoupSpider();
        //mongoTemplate.insert(cryptocurrenciesData);
        final Date now = new Date();
        list.forEach(data->{
            Query query = new Query();
            //data.setUpdateTime(now);
            query.addCriteria(Criteria.where("simpleName").is(data.getSimpleName()));
            String s = JSON.toJSONString(data);
            final Document parse = Document.parse(s);
            parse.put("updateTime",now);
            Update update = new BasicUpdate(parse);
            mongoTemplate.upsert(query,update,CryptocurrenciesData.class);
        });

        final List<RateData> rateDataList = jsoupDataUtil.getRate();
        rateDataList.forEach(rateData -> {
            Query query = new Query();
            query.addCriteria(Criteria.where("coin").is(rateData.getCoin()));
            String s = JSON.toJSONString(rateData);
            final Document parse = Document.parse(s);
            parse.put("updateTime",now);
            Update update = new BasicUpdate(parse);
            mongoTemplate.upsert(query,update,RateData.class);
        });

        final TotalData totalData = jsoupDataUtil.totalSum();
        Query query = new Query();
        final Document parse = Document.parse(JSON.toJSONString(totalData));
        parse.put("updateTime",now);
        Update update = new BasicUpdate(parse);
        mongoTemplate.upsert(query,update,TotalData.class);

    }


    //@PostConstruct
    public void updateExchanges(){
        JsoupExchangesDataUtil jsoupExchangesDataUtil = new JsoupExchangesDataUtil();
        List<ExchangesData> list = jsoupExchangesDataUtil.jsoupSpider();
        //mongoTemplate.insert(cryptocurrenciesData);
        Date now = new Date();
        list.forEach(data->{
            //data.setUpdateTime(now);
            Query query = new Query();
            query.addCriteria(Criteria.where("name").is(data.getName()));
            String s = JSON.toJSONString(data);
            final Document parse = Document.parse(s);
            parse.put("updateTime",now);
            Update update = new BasicUpdate(parse);
            mongoTemplate.upsert(query,update,ExchangesData.class);
        });

    }
}
