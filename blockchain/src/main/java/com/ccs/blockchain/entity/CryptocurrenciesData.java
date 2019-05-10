package com.ccs.blockchain.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("cryptocurrenciesData")
public class CryptocurrenciesData {
    private String num;
    private String simpleName;
    private String name;
    private String nameImg;


    private String marketCap;
    private String price;
    private String volume24;
    private String circulatingSupply;
    private String change24;
    private String priceGraphImg;

    @Override
    public String toString() {
        return "" +
                "	\t" + num + 
                "	\t" + simpleName +
                ", 	\t" + name +
                ", 	\t" + nameImg + 
                ", 	\t" + marketCap + 
                ", 	\t" + price + 
                ", 	\t" + volume24 + 
                ", 	\t" + circulatingSupply + 
                ", 	\t" + change24 + 
                ", 	\t" + priceGraphImg ;
    }
}
