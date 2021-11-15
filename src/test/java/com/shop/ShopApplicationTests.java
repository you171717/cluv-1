package com.shop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan("com/shop/*")
class ShopApplicationTests {

	@Test
	void contextLoads() {
	}

}
