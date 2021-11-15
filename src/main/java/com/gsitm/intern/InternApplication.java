package com.gsitm.intern;

import com.gsitm.intern.test.mybatis.MyBatisService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class InternApplication {

	private MyBatisService myBatisService ;

	public static void main(String[] args) {
		SpringApplication.run(InternApplication.class, args);
	}

}


