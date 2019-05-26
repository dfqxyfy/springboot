package cn.ccs.coin.vo;

import cn.ccs.coin.entity.RateData;
import cn.ccs.coin.entity.TotalData;
import lombok.Data;

import java.util.List;

@Data
public class PageInitData {
    private List<RateData> listRateData;
    private TotalData totalData;

    public PageInitData(){
    }
    public PageInitData(List<RateData> listRateData,TotalData totalData ){
        this.listRateData = listRateData;
        this.totalData = totalData;
    }
}
