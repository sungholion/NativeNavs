package com.navtivenavs.NativeNavs_BackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.nativenavs"})	// 디렉토리 생성 시 제대로 동작함.
public class NativeNavsBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(NativeNavsBackEndApplication.class, args);
	}

}
