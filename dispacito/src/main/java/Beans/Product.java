package Beans;

public class Product {
	protected String productName;
	protected String productUrl;
	protected String price;
	protected String quantity;

	
	
	public Product(String name, String url, String prc, String qnt) {
		this.productName = name;
		this.productUrl = url;
		this.price = prc;
		this.quantity = qnt;

	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getProductUrl() {
		return productUrl;
	}


	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getQuantity() {
		return quantity;
	}


	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	


}
