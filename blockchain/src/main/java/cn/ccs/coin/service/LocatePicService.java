package cn.ccs.coin.service;

import cn.ccs.coin.entity.DownloadPic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.List;

@Service
@Slf4j
public class LocatePicService {

    @Value("${download.path:/ccs/}")
    private String downLoadPath = "/ccs/";

    @Autowired
    private MongoTemplate mongoTemplate;

    public String findUrlByCoin(String prex,String name,String imgUrl){
        DownloadPic pic;
        Query query = new Query();
        Criteria criteria = Criteria.where("prex").is(prex);
        criteria.andOperator(Criteria.where("name").is(name));
        query.addCriteria(criteria);
        List<DownloadPic> downloadPics = mongoTemplate.find(query, DownloadPic.class);
        if(downloadPics.size()>0){
            pic = downloadPics.get(0);
        }else{
            String sPath = downloadPic(prex, name,imgUrl);
            pic = new DownloadPic();
            pic.setPrex(prex);
            pic.setName(name);
            pic.setStoreUrl(sPath);
            mongoTemplate.insert(pic);
        }
        return pic.getStoreUrl();
    }

    public String downloadPic(String prex,String name, String urlPath){
        try {
            URL url = new URL(urlPath);
            int i = urlPath.lastIndexOf(".");
            String ext=urlPath.substring(i);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            File f=new File(downLoadPath+"/"+prex+"/");
            if(!f.exists()){
                f.mkdir();
            }
            String imageName="";
            int num=1;
            do {
                String add = "";
                if(num != 1){
                    add = num+"";
                }
                imageName= downLoadPath + prex + "/" + name + add + ext;
                num++;
            }while(new File(imageName).exists());

            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] context=output.toByteArray();
            fileOutputStream.write(context);
            dataInputStream.close();
            fileOutputStream.close();
            return imageName;
        } catch (Exception e) {
            log.error("下载失败",e);
            throw new RuntimeException("下载失败",e);
        }
    }

//    public static void main(String[] args) {
//        String btc = new LocatePicService().downloadPic("https://s2.coinmarketcap.com/static/img/coins/32x32/1765.png", "btc");
//        System.out.println(btc);
//    }
}
