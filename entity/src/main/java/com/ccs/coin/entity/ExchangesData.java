package com.ccs.coin.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("exchangesData")
public class ExchangesData {
    private String num;
    private String name;
    private String nameUrl;
    private String adjvol;
    private String volume24h;
    private String volume7d;
    private String volume30d;
    private String noMarkets;
    private String change24h;
    private String volGraph7d;
    private String launched;

}