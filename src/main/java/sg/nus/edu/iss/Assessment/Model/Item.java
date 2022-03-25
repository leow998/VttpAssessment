package sg.nus.edu.iss.Assessment.Model;

public class Item {
	private String item;
	private Integer quantity;

	public Item(String itemName, Integer quantity) {
		super();
		this.item = itemName;
		this.quantity = quantity;
	}

	public Item() {

	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Item [item=" + item + ", quantity=" + quantity + "]";
	}

}
