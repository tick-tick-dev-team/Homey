package com.ticktack.homey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//출처: https://kamsi76.tistory.com/entry/시작 [Kamsi's Blog:티스토리]
public class HomeyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeyApplication.class, args);
	}
	

}
