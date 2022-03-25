package sg.nus.edu.iss.Assessment.Model;

import java.util.List;

public class Order {
	private String name;
	private String address;
	private String email;
	private List<Item> lineItems;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Item> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<Item> items) {
		this.lineItems = items;
	}

	public Order(String name, String address, String email, List<Item> items) {
		super();
		this.name = name;
		this.address = address;
		this.email = email;
		this.lineItems = items;
	}

	public Order() {

	}

	@Override
	public String toString() {
		return "Order [name=" + name + ", address=" + address + ", email=" + email + ", lineItems=" + lineItems + "]";
	}

}
