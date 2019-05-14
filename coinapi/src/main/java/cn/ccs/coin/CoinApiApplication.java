package cn.ccs.coin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class CoinApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoinApiApplication.class, args);
	}


}
