package cn.ccs.coin.service;

import cn.ccs.coin.entity.CryptocurrenciesData;
import cn.ccs.coin.entity.RateData;
import cn.ccs.coin.entity.TotalData;
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
        final Elements select = page.select("body > div.cmc-nav-wrap > div.cmc-nav > div.cmc-nav__topbar.cmc-nav-desktop > div > div.cmc-global-stats.js-global-stats");
        final Elements spans = select.get(0).getElementsByTag("span");

        final String cryptocurrencies = spans.get(0).getElementsByTag("a").get(0).getElementsByTag("span").get(0).text();
        final String markets = spans.get(1).getElementsByTag("a").get(0).getElementsByTag("span").get(0).text();
        final String marketCap = spans.get(2).getElementsByTag("a").get(0).getElementsByTag("span").get(0).text();
        final String vol24h = spans.get(3).getElementsByTag("a").get(0).getElementsByTag("span").get(0).text();
        final String btcDominance = spans.get(4).getElementsByTag("a").get(0).getElementsByTag("span").get(0).text();
        TotalData data = new TotalData();
        data.setCryptocurrencies(cryptocurrencies);
        data.setMarkets(markets);
        data.setMarketCap(marketCap);
        data.setVol24h(vol24h);
        data.setBtcDominance(btcDominance);

        return data;
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
        String volume24 = tds.get(4).attr("data-sort");;
        String circulatingSupply = tds.get(5).attr("data-sort");
        String change24= tds.get(6).attr("data-sort");

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
        new JsoupCryptoDataUtil().jsoupSpider();
    }
}
