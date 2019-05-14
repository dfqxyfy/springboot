package cn.ccs.coin.controller;

import cn.ccs.coin.service.DataFindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class ApiController extends BaseController {

    @Autowired
    private DataFindService dataFindService;

    @RequestMapping("getCryptoData")
    public String cryptoData(String addr){
        return success(dataFindService.getCryptoData());
    }
    @RequestMapping("getExchanges")
    public String exchanges(){
        return success(dataFindService.getExchangesData());
    }

    @RequestMapping("getRate")
    public String deploy(){
        return success(dataFindService.getRateData());
    }
}
