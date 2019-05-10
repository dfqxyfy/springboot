package com.ccs.blockchain.utils;

import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

public class ChromeSpider {
    public static RemoteWebDriver getWebDriver(){
        ChromeDriverService service;
        RemoteWebDriver driver = null;
        service = new ChromeDriverService.Builder().usingDriverExecutable(new File("/project/zeus-audit-spider/lib/mac/chromedriver"))
                .usingAnyFreePort().build();
        try {
            service.start();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setHeadless(true);
            driver = new RemoteWebDriver(service.getUrl(), chromeOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return driver;
    }
}
