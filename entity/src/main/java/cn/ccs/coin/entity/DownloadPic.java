package cn.ccs.coin.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("downloadPic")
public class DownloadPic {
    private String prex;
    private String name;
    private String storeUrl;
}
