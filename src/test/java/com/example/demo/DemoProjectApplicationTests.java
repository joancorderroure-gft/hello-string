package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class DemoProjectApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void rootTest(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/", String.class))
				.isEqualTo("Hello world");
	}

	@Test
	void homeTest(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/home", String.class))
				.isEqualTo("This is the home page of Joan Corderroure");
	}

	@Test
	void helloTest(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/hello", String.class))
				.isEqualTo("Hello World!");
	}
	@Test
	void canAdd(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1 &b=2", String.class))
				.isEqualTo("3");
	}

}


