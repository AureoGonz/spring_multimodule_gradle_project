package lab.triceracode.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
@SpringBootApplication(scanBasePackages = "lab.triceracode")
public class WarehouseMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseMsApplication.class, args);
	}

}
