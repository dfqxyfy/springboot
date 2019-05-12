package cn.ccs.coin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class ApiController {

    @RequestMapping("getCryptoData")
    public String cryptoData(String addr){
      return "";
    }
    @RequestMapping("getExchanges")
    public String exchanges(){
        return "";
    }

    @RequestMapping("getRate")
    public String deploy(){
        return "success";
    }
}
