package com.ccs.blockchain.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SpiderDataService {

    private RemoteWebDriver getWebDriver(){
        ChromeDriverService service;
        RemoteWebDriver driver = null;
        service = new ChromeDriverService.Builder().usingDriverExecutable(new File("D:/program/chromedriver.exe"))
                .usingAnyFreePort().build();
        try {
            service.start();
            driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return driver;
    }

    public void spiderData(){
        RemoteWebDriver driver = getWebDriver();
        driver.get("https://coinmarketcap.com/");
        System.out.println("************************8");
        final WebElement tbody = driver.findElement(By.tagName("tbody"));
        final WebElement elementByXPath = driver.findElementByXPath("//tbody");
        final String tbody1 = elementByXPath.getText();
        //driver.findElementsByXPath("//tbody/tr");
        System.out.println(tbody1);
        System.out.println("************************8");
        //System.out.println(tbody.getText());
        final List<WebElement> trList = tbody.findElements(By.tagName("tr"));
//        trList.forEach(e->{
//            final List<WebElement> tdList = e.findElements(By.tagName("td"));
//            String num = tdList.get(0).getText();
//            String simple = tdList.get(1).findElement(By.tagName("span")).findElement(By.tagName("a")).getText();
//            String full = tdList.get(1).findElement(By.tagName("a")).getText();
//            String marketCap = tdList.get(2).getText();
//
//            //System.out.println(num +"\t"+simple+"\t"+full+"\t"+marketCap);
//
//        });
        driver.close();
    }

    public static void main(String[] args) {
        SpiderDataService spiderDataService = new SpiderDataService();
        spiderDataService.spiderData();
    }
}
