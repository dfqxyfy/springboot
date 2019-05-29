package cn.ccs.coin.service;

import cn.ccs.coin.entity.CryptocurrenciesData;
import cn.ccs.coin.entity.RateData;
import cn.ccs.coin.entity.TotalData;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
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

@Slf4j
public class JsoupCryptoDataUtil {
    Document page = null;
    public JsoupCryptoDataUtil(){
        try {
            page = Jsoup.parse(new URL("https://coinmarketcap.com/"), 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<RateData> getRate(){
        Element elementById = page.getElementById("currency-exchange-rates");
        Attributes attributes = elementById.attributes();
        Map<String,String> map = new HashMap<>();
        List<RateData> list = new ArrayList<>();
        attributes.forEach(attribute -> {
            //System.out.println(attribute.getKey()+"\t"+attribute.getValue());
            RateData data = new RateData();
            data.setCoin(attribute.getKey());
            data.setRate(attribute.getValue());
            list.add(data);
        });
        return list;
    }

    public TotalData totalSum(){
        TotalData data = new TotalData();
        try {
            final Document document = Jsoup.connect("https://s2.coinmarketcap.com/generated/stats/global.json")
                    .ignoreContentType(true)
                    .get();
            final String body = document.getElementsByTag("body").get(0).text();
            final JSONObject parse = JSONObject.parseObject(body);
            ;
            data.setCryptocurrencies(parse.getString("active_cryptocurrencies"));
            data.setMarkets(parse.getString("active_markets"));
            data.setMarketCap(parse.getString("total_market_cap_by_available_supply_usd"));
            data.setVol24h(parse.getString("total_volume_usd"));
            data.setBtcDominance(parse.getString("bitcoin_percentage_of_market_cap"));
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if(coinUrl!=null && coinUrl.startsWith("data:image")){
            coinUrl = tds.get(1).getElementsByTag("img").get(0).attr("data-src");
        }
        String simple = tds.get(1).getElementsByTag("span").get(0).text();

        String marketCap = tds.get(2).attr("data-sort");

        String price = tds.get(3).attr("data-sort");
        String volume24 = tds.get(4).attr("data-sort");;
        String circulatingSupply = tds.get(5).attr("data-sort");
        String change24= tds.get(6).attr("data-sort");

        CryptocurrenciesData data = new CryptocurrenciesData();
        data.setNum(num);
        data.setSimpleName(simple);
        data.setName(full);
        data.setIconUrl(coinUrl);
        data.setMarketCap(marketCap);
        data.setPrice(price);
        data.setVolume24(volume24);
        data.setCirculatingSupply(circulatingSupply);
        data.setChange24(change24);

        return  data;
    }

    public static void main(String[] args) {
        final JsoupCryptoDataUtil jsoupCryptoDataUtil = new JsoupCryptoDataUtil();
//        jsoupCryptoDataUtil.jsoupSpider();
        jsoupCryptoDataUtil.totalSum();

//        final Document parse;
//        try {
//            final Document document = Jsoup.connect("https://s2.coinmarketcap.com/generated/stats/global.json")
//                    .ignoreContentType(true)
//                    .get();
//            System.out.println(document);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
