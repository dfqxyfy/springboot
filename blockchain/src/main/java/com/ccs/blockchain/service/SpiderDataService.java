package com.ccs.blockchain.service;

import com.ccs.blockchain.entity.CryptocurrenciesData;
import com.ccs.blockchain.utils.ChromeSpider;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ccs
 */
@Service
public class SpiderDataService {

    public List<CryptocurrenciesData> spiderData(Integer page, String baseUrl) {
        RemoteWebDriver driver = ChromeSpider.getWebDriver();
        List<CryptocurrenciesData> resultList = new ArrayList<>();
        //String baseUrl = "https://coinmarketcap.com/";
        String url = baseUrl;
        if (page != 1) {
            url = url + page;
        }
        driver.get(url);
        RemoteWebElement tbodyEle = (RemoteWebElement) driver.findElementByXPath("//tbody");
        if (tbodyEle == null) {
            return resultList;
        }

        List<WebElement> trList = tbodyEle.findElementsByXPath("trList");
        if (trList.size() == 0) {
            return resultList;
        }
        trList.forEach(e -> {
            List<WebElement> tdList = ((RemoteWebElement) e).findElementsByXPath("td");
            CryptocurrenciesData data = createData(tdList);
            resultList.add(data);
        });
        driver.close();
        return resultList;

    }

    private CryptocurrenciesData createData(List<WebElement> tdList){
        String num = tdList.get(0).getText();
        String simple = ((RemoteWebElement) tdList.get(1)).findElementByXPath("span/a").getText();
        String full = ((RemoteWebElement) tdList.get(1)).findElementByXPath("a").getText();
        String marketCap = tdList.get(2).getText();
        String price = tdList.get(3).getText();
        String volume24 = tdList.get(4).getText();
        String circulatingSupply = tdList.get(5).getText();
        String change24 = tdList.get(6).getText();
        String priceGraphImg = ((RemoteWebElement) tdList.get(7)).findElementByXPath("a/img").getAttribute("src");

        CryptocurrenciesData data = new CryptocurrenciesData();
        data.setNum(num);
        data.setSimpleName(simple);
        data.setName(full);
        data.setMarketCap(marketCap);
        data.setPrice(price);
        data.setVolume24(volume24);
        data.setCirculatingSupply(circulatingSupply);
        data.setChange24(change24);
        data.setPriceGraphImg(priceGraphImg);

        return  data;
    }

    public static void main(String[] args) {
        SpiderDataService spiderDataService = new SpiderDataService();
        spiderDataService.spiderData(1,"https://coinmarketcap.com/");
    }
}
