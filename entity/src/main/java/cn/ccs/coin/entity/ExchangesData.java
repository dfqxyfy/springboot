package cn.ccs.coin.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("exchangesData")
public class ExchangesData {
    private String num;
    private String name;
    private String iconUrl;
    private String localUrl;

    private String adjvol;
    private String volume24h;
    private String volume7d;
    private String volume30d;
    private String noMarkets;
    private String change24h;
    private String volGraph7d;
    private String launched;

    private Date updateTime;
}
