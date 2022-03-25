package sg.nus.edu.iss.Assessment.Services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.nus.edu.iss.Assessment.Model.Quotation;

@Service
public class QuotationService {
	public Optional<Quotation> getQuotations(List<String> items) {

		RestTemplate template = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<List<String>> requestEntity = new HttpEntity<>(items, headers);
		ResponseEntity<String> response = template.exchange("https://quotation.chuklee.com/quotation", HttpMethod.POST,
				requestEntity, String.class);
		if(response.getStatusCodeValue()!=200) {
			return Optional.empty();
		}
		return Optional.of(stringToQuotation(response.getBody()));
	}

	public Quotation stringToQuotation(String input) {

		InputStream inputStream = new ByteArrayInputStream(input.getBytes());
		JsonReader jsonReader = Json.createReader(inputStream);
		JsonObject jsonObject = jsonReader.readObject();

		Quotation quotation = new Quotation();
		quotation.setQuoteId(jsonObject.getString("quoteId"));

		JsonArray jsonArray = jsonObject.getJsonArray("quotations");
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject item = jsonArray.getJsonObject(i);
			String itemName = item.getString("item");
			Double unitPrice = item.getJsonNumber("unitPrice").doubleValue();
			quotation.addQuotation(itemName, unitPrice.floatValue());
		}

		return quotation;
	}
}
