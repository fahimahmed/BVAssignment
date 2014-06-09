package com.fahimahmed.bv.database;

public class Product {

	static String PRODUCT_TABLE = "product_table";
	static String PRODUCT_ID = "id";
	static String PRODUCT_NAME = "name";
	static String PRODUCT_PRICE = "price";
	static String PRODUCT_QUANTITY = "quantity";
	static String PRODUCT_EMAIL_SENT = "isEmailSent";

	public String id;
	public String name;
	public String price;
	public int quantity;
	public boolean isEmailSent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isEmailSent() {
		return isEmailSent;
	}

	public void setEmailSent(boolean isEmailSent) {
		this.isEmailSent = isEmailSent;
	}

}
