package it.poc.fabrick;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class PocfabrickApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(1)
	public void getBalance_test() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/get/accounts/14537780/balance")
				.accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@Order(2)
	public void getTransactions_test() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/get/accounts/14537780/transactions?fromAccountingDate=2019-01-01&toAccountingDate=2019-12-01")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
