package com.ccs.coin.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value="rateData")
public class RateData {
    String coin;
    String rate;
}
