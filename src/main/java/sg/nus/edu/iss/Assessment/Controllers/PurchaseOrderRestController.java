package sg.nus.edu.iss.Assessment.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.Assessment.Model.Item;
import sg.nus.edu.iss.Assessment.Model.Order;
import sg.nus.edu.iss.Assessment.Model.Quotation;
import sg.nus.edu.iss.Assessment.Services.QuotationService;

@RestController
public class PurchaseOrderRestController {
	@Autowired
	private QuotationService qtsvc;

	@RequestMapping(value = "/api/po", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> purchaseOrder(@RequestBody Order order) {
		List<String> items = new ArrayList<String>();
		for (Item item : order.getLineItems()) {
			items.add(item.getItem());
		}
		Optional<Quotation> quotations = qtsvc.getQuotations(items);
		if (quotations.isEmpty()) {
			return ResponseEntity.badRequest().body("{}");
		}

		Quotation quotation = quotations.get();
		Float total = 0.0f;
		for (Item item : order.getLineItems()) {
			Float unitPrice = quotation.getQuotation(item.getItem());
			total += item.getQuantity() * unitPrice;
		}

		JsonObject jsonObject = Json.createObjectBuilder().add("invoiceId", quotation.getQuoteId())
				.add("name", order.getName()).add("total", total).build();

		return ResponseEntity.ok().body(jsonObject.toString());

	}
}
