package cn.bookingsmart.demo;

import cn.bookingsmart.annotation.EnableJsonErrors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableJsonErrors
@EnableFeignClients(basePackages = "cn.bookingsmart")
@EnableEurekaClient
public class ServiceDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDemoApplication.class, args);
    }
}

