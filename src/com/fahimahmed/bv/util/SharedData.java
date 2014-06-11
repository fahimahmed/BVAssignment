package com.fahimahmed.bv.util;

import com.fahimahmed.bv.database.Product;

public class SharedData {
	private static SharedData instance = new SharedData();

	private SharedData() {

	}

	public static SharedData getInstance() {
		return instance;
	}

	public static void setInstance(SharedData instance) {
		SharedData.instance = instance;
	}

	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
