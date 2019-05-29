package cn.ccs.coin.service;

import cn.ccs.coin.entity.ExchangesData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsoupExchangesDataUtil {
    Document page = null;

    public JsoupExchangesDataUtil(){
        try {
            page = Jsoup.parse(new URL("https://coinmarketcap.com/rankings/exchanges/"), 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<ExchangesData> jsoupSpider(){
        List<ExchangesData> resList = new ArrayList<>();
        Elements tbody = page.getElementsByTag("tbody");
        Element element = tbody.get(0);
        Elements trs = element.getElementsByTag("tr");
        trs.forEach(tr->{
            Elements tds = tr.getElementsByTag("td");
            ExchangesData data = createData(tds);
            resList.add(data);
        });
        return resList;

    }

    private ExchangesData createData(Elements tds){
        String num = tds.get(0).text();
        String name = tds.get(1).attr("data-sort");
        String nameUrl = tds.get(1).getElementsByTag("img").get(0).attr("src");

        if(nameUrl!=null && nameUrl.startsWith("data:image")){
            nameUrl = tds.get(1).getElementsByTag("img").get(0).attr("data-src");
        }

        String adjvol = tds.get(2).attr("data-sort");

        String volume24h = tds.get(3).attr("data-sort");;
        String volume7d = tds.get(4).attr("data-sort");;
        String volume30d = tds.get(5).attr("data-sort");;
        String noMarkets = tds.get(6).attr("data-sort");
        String change24h= tds.get(7).attr("data-sort");
        String volGraph7d= tds.get(8).getElementsByTag("a").get(0).getElementsByTag("img").attr("src");
        String launched= tds.get(9).attr("data-sort");

        ExchangesData data = new ExchangesData();
        data.setNum(num);
        data.setName(name);
        data.setIconUrl(nameUrl);
        data.setAdjvol(adjvol);
        data.setVolume24h(volume24h);
        data.setVolume7d(volume7d);
        data.setVolume30d(volume30d);
        data.setNoMarkets(noMarkets);
        data.setChange24h(change24h);
        data.setVolGraph7d(volGraph7d);
        data.setLaunched(launched);

        return  data;
    }

    public static void main(String[] args) {
        new JsoupExchangesDataUtil().jsoupSpider();
    }
}
