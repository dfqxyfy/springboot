package cn.ccs.coin.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(value="rateData")
public class RateData {
    private String coin;
    private String rate;

    private Date date;
}
