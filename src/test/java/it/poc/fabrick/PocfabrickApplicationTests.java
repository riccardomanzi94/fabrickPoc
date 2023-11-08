package it.poc.fabrick;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PocfabrickApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private static final String json = "{\n" +
			"  \"creditor\": {\n" +
			"    \"name\": \"John Doe\",\n" +
			"    \"account\": {\n" +
			"      \"accountCode\": \"IT23A0336844430152923804660\",\n" +
			"      \"bicCode\": \"SELBIT2BXXX\"\n" +
			"    },\n" +
			"    \"address\": {\n" +
			"      \"address\": null,\n" +
			"      \"city\": null,\n" +
			"      \"countryCode\": null\n" +
			"    }\n" +
			"  },\n" +
			"  \"executionDate\": \"2019-04-01\",\n" +
			"  \"uri\": \"REMITTANCE_INFORMATION\",\n" +
			"  \"description\": \"Payment invoice 75/2017\",\n" +
			"  \"amount\": 800,\n" +
			"  \"currency\": \"EUR\",\n" +
			"  \"isUrgent\": false,\n" +
			"  \"isInstant\": false,\n" +
			"  \"feeType\": \"SHA\",\n" +
			"  \"feeAccountId\": \"45685475\",\n" +
			"  \"taxRelief\": {\n" +
			"    \"taxReliefId\": \"L449\",\n" +
			"    \"isCondoUpgrade\": false,\n" +
			"    \"creditorFiscalCode\": \"56258745832\",\n" +
			"    \"beneficiaryType\": \"NATURAL_PERSON\",\n" +
			"    \"naturalPersonBeneficiary\": {\n" +
			"      \"fiscalCode1\": \"MRLFNC81L04A859L\",\n" +
			"      \"fiscalCode2\": null,\n" +
			"      \"fiscalCode3\": null,\n" +
			"      \"fiscalCode4\": null,\n" +
			"      \"fiscalCode5\": null\n" +
			"    },\n" +
			"    \"legalPersonBeneficiary\": {\n" +
			"      \"fiscalCode\": null,\n" +
			"      \"legalRepresentativeFiscalCode\": null\n" +
			"    }\n" +
			"  }\n" +
			"}";

	@Test
	@Order(1)
	public void getBalance_test() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/get/accounts/14537780/balance")
				.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
	}

	@Test
	@Order(2)
	public void getTransactions_test() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/get/accounts/14537780/transactions?fromAccountingDate=2019-01-01&toAccountingDate=2019-12-01")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@Order(3)
	public void createMoneyTransfer_test() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders
						.post("/post/accounts/14537780/payment/money-transfers")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());

	}

}
