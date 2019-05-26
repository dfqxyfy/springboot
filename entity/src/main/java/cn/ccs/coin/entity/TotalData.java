package cn.ccs.coin.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "totalData")
public class TotalData {
    private String cryptocurrencies;
    private String markets;
    private String marketCap;
    private String vol24h;
    private String btcDominance;
}
