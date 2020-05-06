package com.avisow.spring5;

import com.avisow.spring5.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void testGetEmployee() {
		webTestClient
			// Create a GET request to test an endpoint
			.get().uri("/employees/2")
//			.accept(MediaType.TEXT_PLAIN)
			.exchange()
			// and use the dedicated DSL to test assertions against the response
			.expectStatus().isOk()
			.expectBody(Employee.class)
			.consumeWith(e -> {
				assertThat(e).isNotNull();
				assertThat(e.getResponseBody().getId()).isEqualTo("2");
			});
	}

	@Test
	public void testGetEmployees() {
		Employee employee = new Employee();
		employee.setId("2");
		employee.setName("Employee 2");

		webTestClient
			.get().uri("/employees")
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(Employee.class)
			.hasSize(10)
			.contains(employee);
	}

}
