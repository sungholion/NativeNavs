package com.nativenavs.NativeNavs_BackEnd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ComponentScan(basePackages = {"com.nativenavs"})
@MapperScan("com.nativenavs.user.mapper")
public class NativeNavsBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(NativeNavsBackEndApplication.class, args);
	}

}
