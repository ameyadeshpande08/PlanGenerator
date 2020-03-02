package com.lendico.plangenerator;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PlanGeneratorApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testStdCalculation() throws Exception {
		this.mockMvc.perform(post("/generate-plan").contentType(MediaType.APPLICATION_JSON).content(
				"{\"loanAmount\":\"1000\",\"nominalRate\":\"5.0\",\"duration\":2,\"startDate\":\"2018-01-01T00:00:01Z\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$[0].borrowerPaymentAmount", is(503.13)))
				.andExpect(jsonPath("$[0].initialOutstandingPrincipal", is(1000.0)))
				.andExpect(jsonPath("$[0].interest", is(4.17)))
				.andExpect(jsonPath("$[0].principal", is(498.96)))
				.andExpect(jsonPath("$[0].remainingOutstandingPrincipal", is(501.04)));
	}
}
