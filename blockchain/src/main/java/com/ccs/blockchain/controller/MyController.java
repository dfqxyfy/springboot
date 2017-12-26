package com.ccs.blockchain.controller;

import com.ccs.blockchain.common.HttpClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class MyController {

    @RequestMapping("getDividends.php")
    public String dividends(String addr){
        return HttpClient.get("https://btcdiv.com/getDividends.php?addr=aaa",new HashMap<>(),new HashMap<>());
    }
}
