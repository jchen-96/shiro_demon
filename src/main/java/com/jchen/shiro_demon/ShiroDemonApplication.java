package com.jchen.shiro_demon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.jchen.shiro_demon.mapper"})
public class ShiroDemonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShiroDemonApplication.class, args);
	}

}
