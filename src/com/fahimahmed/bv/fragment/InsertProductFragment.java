package com.fahimahmed.bv.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fahimahmed.bv.R;
import com.fahimahmed.bv.database.DatabaseManager;
import com.fahimahmed.bv.database.Product;

public class InsertProductFragment extends Fragment implements
		View.OnClickListener {

	private Activity mActivity;
	private EditText etProductName, etProductPrice, etProductQuantity;
	private Button btnSave;
	private DatabaseManager database;
	private ArrayList<Product> products;
	private Context context;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_insert_product, container,
				false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		initUI(view);
		context = this.mActivity;
		database = DatabaseManager.getInstance(context);

	}

	private void initUI(View view) {
		etProductName = (EditText) view.findViewById(R.id.etProductName);
		etProductPrice = (EditText) view.findViewById(R.id.etProductPrice);
		etProductQuantity = (EditText) view
				.findViewById(R.id.etProductQuantity);
		btnSave = (Button) view.findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSave:
			products = new ArrayList<Product>();
			Product product = new Product();
			product.name = etProductName.getText().toString();
			product.price = etProductPrice.getText().toString();
			product.quantity = Integer.parseInt(etProductQuantity.getText()
					.toString());
			product.isEmailSent = 0;
			products.add(product);
			database.insertProducts(products);
			break;
		}
	}
}