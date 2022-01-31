package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
	void helloNames(@Autowired TestRestTemplate restTemplate) {
		String[] arr = {"Javier", "Rodriguez", "Arturo"};
		for (String name: arr) {
			assertThat(restTemplate.getForObject("/hello?name="+name, String.class))
					.isEqualTo("Hello "+name+"!");
		}
	}

	@Autowired TestRestTemplate restTemplate;
	@ParameterizedTest
	@ValueSource(strings = {
			"Javier",
			"Rodriguez",
			"Javier Arturo",
			"Arturo"
	})
	void helloParamNames(String name) {
		assertThat(restTemplate.getForObject("/hello?name="+name, String.class))
				.isEqualTo("Hello "+name+"!");
	}

	@DisplayName("test multiple input values")
	@ParameterizedTest(name="[{index}] ({arguments}) \"{0}\" -> \"{1}\",")
	@CsvSource({
			"a, Hello a!",
			"b, Hello b!",
			", Hello null!",
			"'', Hello World!",
			"' ', Hello  !",
			"first+last, Hello first last!",
			"first%20last, Hello first%20last!"
	})



	void helloParamNamesCsv(String name, String expected) {
		assertThat(restTemplate.getForObject("/hello?name="+name, String.class))
				.isEqualTo(expected);
	}

	@Test
	void canAdd(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1 &b=2", String.class))
				.isEqualTo("3");
	}
	@Test
	void canAddNegative(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1 &b=-2", String.class))
				.isEqualTo("-1");
	}

	@Test
	void canAddNullA(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=&b=1", String.class))
				.isEqualTo("1");
	}
	
	@Test
	void canAddNullB(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1&b=", String.class))
				.isEqualTo("1");
	}

	@Test
	void canAddFractions(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1.5&b=2", String.class))
				.isEqualTo("3.5");
	}
	@DisplayName("multiple addittions")
	@ParameterizedTest(name="[{index}] {0} + {1} = {2}")
	@CsvSource({
			"1, 2, 3",
			"1, 1, 2",
			"1.0, 1.0, 2",
			"1, -2, -1",
			"0, 1, 1",
			"1.5, -0.5, 1"
	})
	void canAddCsvParamaterizedFloat(String a, String b, String expected){
		assertThat(restTemplate.getForObject("/add?a="+a+"&b="+b, Float.class))
				.isEqualTo(Float.parseFloat(expected));
	}
	
	void canAddCsvParamaterized(String a, String b, String expected){
		assertThat(restTemplate.getForObject("/add?a="+a+"&b="+b, String.class))
				.isEqualTo(expected);
	}


	@Test
	void canAddExceptionJsonString() {
		assertThat(restTemplate.getForObject("/add?a=string&b=1", String.class)
				.indexOf("Error Request")).isGreaterThan(-1);
	}

	@Test
	void canAddFloat(){
		assertThat(restTemplate.getForObject("/add?a=1.5&b=2", Float.class))
				.isEqualTo(3.5f);
	}

	@Test
	void canAddFloatException() {
		Exception thrown = assertThrows(RestClientException.class, ()->{
			restTemplate.getForObject("/add?a=1.5&b=2", Float.class);
		});
	}



}



