package com.navtivenavs.NativeNavs_BackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ComponentScan(basePackages = {"com.nativenavs"})
public class NativeNavsBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(NativeNavsBackEndApplication.class, args);
	}

}
