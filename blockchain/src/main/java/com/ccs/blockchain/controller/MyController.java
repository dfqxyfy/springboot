package com.ccs.blockchain.controller;

import com.ccs.blockchain.common.HttpClient;
import com.ccs.blockchain.common.HttpClientUtil;
import com.ccs.blockchain.common.HttpClientUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class MyController {

    @RequestMapping("getDividends.php")
    public String dividends(String addr){
        //return HttpClient.get("https://btcdiv.com/getDividends.php?addr=aaa",new HashMap<>(),new HashMap<>());

        try {
            //return HttpClientUtil.doGet("https://btcdiv.com/getDividends.php?addr="+addr, "GBK");
            return HttpClientUtil.doGet("https://btcdiv.com/getDividends.php?addr=1H6ZZpRmMnrw8ytepV3BYwMjYYnEkWDqVP",new HashMap<>(),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping("restart")
    public String deploy(){
        try {
            Runtime.getRuntime().exec("/ccs/app/start.sh");
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
        return "success";
    }
}
