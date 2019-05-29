package cn.ccs.coin.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("cryptocurrenciesData")
public class CryptocurrenciesData {
    private String num;
    private String simpleName;
    private String name;
    private String iconUrl;
    private String localUrl;

    private String marketCap;
    private String price;
    private String volume24;
    private String circulatingSupply;
    private String change24;
    private String priceGraphImg;

    private Date updateTime;
    @Override
    public String toString() {
        return "" +
                "	\t" + num + 
                "	\t" + simpleName +
                ", 	\t" + name +
                ", 	\t" + iconUrl +
                ", 	\t" + localUrl +
                ", 	\t" + marketCap +
                ", 	\t" + price + 
                ", 	\t" + volume24 + 
                ", 	\t" + circulatingSupply + 
                ", 	\t" + change24 + 
                ", 	\t" + priceGraphImg ;
    }
}
