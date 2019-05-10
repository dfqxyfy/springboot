package com.ccs.blockchain.service;

import com.ccs.blockchain.entity.CryptocurrenciesData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsoupDataUtil {
    Document page = null;

    public JsoupDataUtil(){
        try {
            page = Jsoup.parse(new URL("https://coinmarketcap.com/"), 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String,String> getRate(){
        Element elementById = page.getElementById("currency-exchange-rates");
        Attributes attributes = elementById.attributes();
        attributes.forEach(attribute -> {
            //System.out.println(attribute.getKey()+"\t"+attribute.getValue());
        });
        new HashMap<>();
        return null;
    }

    public List<CryptocurrenciesData> jsoupSpider(){


        List<CryptocurrenciesData> resList = new ArrayList<>();
        Elements tbody = page.getElementsByTag("tbody");
        Element element = tbody.get(0);
        Elements trs = element.getElementsByTag("tr");
        trs.forEach(tr->{
            Elements tds = tr.getElementsByTag("td");
            CryptocurrenciesData data = createData(tds);
            resList.add(data);
        });
        return resList;

    }

    private CryptocurrenciesData createData(Elements tds){
        String num = tds.get(0).ownText();
        String full = tds.get(1).attr("data-sort");
        String coinUrl = tds.get(1).getElementsByTag("img").get(0).attr("src");
        String simple = tds.get(1).getElementsByTag("span").get(0).text();

        String marketCap = tds.get(2).attr("data-sort");

        String price = tds.get(3).attr("data-sort");
        String volume24 = tds.get(3).attr("data-usd");;
        String circulatingSupply = tds.get(3).attr("data-sort");
        String change24= tds.get(3).attr("data-sort");

        CryptocurrenciesData data = new CryptocurrenciesData();
        data.setNum(num);
        data.setSimpleName(simple);
        data.setName(full);
        data.setNameImg(coinUrl);
        data.setMarketCap(marketCap);
        data.setPrice(price);
        data.setVolume24(volume24);
        data.setCirculatingSupply(circulatingSupply);
        data.setChange24(change24);

        return  data;
    }

    public static void main(String[] args) {
        new JsoupDataUtil().jsoupSpider();
    }
}
