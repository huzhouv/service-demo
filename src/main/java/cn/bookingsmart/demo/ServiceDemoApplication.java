package cn.bookingsmart.demo;

import cn.bookingsmart.annotation.EnableFrameworkExt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "cn.bookingsmart")
@EnableFrameworkExt
public class ServiceDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceDemoApplication.class, args);
	}
}

