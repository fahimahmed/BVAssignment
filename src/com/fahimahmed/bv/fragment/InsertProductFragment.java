package com.fahimahmed.bv.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fahimahmed.bv.R;
import com.fahimahmed.bv.database.DatabaseManager;
import com.fahimahmed.bv.database.Product;
import com.fahimahmed.bv.util.SharedData;

public class InsertProductFragment extends DialogFragment implements
		View.OnClickListener {

	private Activity mActivity;
	private EditText etProductName, etProductPrice, etProductQuantity;
	private Button btnSave;
	private DatabaseManager database;
	private ArrayList<Product> products;
	private Context context;
	private boolean isDialog = false;
	private Product productToEdit;
	SharedData sharedData = SharedData.getInstance();

	public static InsertProductFragment newInstance() {
		return new InsertProductFragment();
	}

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
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		isDialog = true;
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		return dialog;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
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
		if (isDialog) {
			btnSave.setText("Update");
			productToEdit = sharedData.getProduct();
			etProductName.setText(productToEdit.name);
			etProductPrice.setText(productToEdit.price);
			etProductQuantity.setText("" + productToEdit.quantity);
		}
		btnSave.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSave:

			if (!isDialog) {

				if (!etProductName.getText().toString().equalsIgnoreCase("")
						&& !etProductPrice.getText().toString()
								.equalsIgnoreCase("")
						&& !etProductQuantity.getText().toString()
								.equalsIgnoreCase("")) {
					products = new ArrayList<Product>();
					Product product = new Product();
					product.name = etProductName.getText().toString();
					product.price = etProductPrice.getText().toString();
					product.quantity = Integer.parseInt(etProductQuantity
							.getText().toString());
					product.isEmailSent = 0;
					products.add(product);
					database.insertProducts(products);

					etProductName.setText("");
					etProductPrice.setText("");
					etProductQuantity.setText("");

					Toast.makeText(context, "Product Added", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(context, "Every field is required!",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				if (!etProductName.getText().toString().equalsIgnoreCase("")
						&& !etProductPrice.getText().toString()
								.equalsIgnoreCase("")
						&& !etProductQuantity.getText().toString()
								.equalsIgnoreCase("")) {
					Product product = new Product();
					product.id = productToEdit.id;
					product.name = etProductName.getText().toString();
					product.price = etProductPrice.getText().toString();
					product.quantity = Integer.parseInt(etProductQuantity
							.getText().toString());
					product.isEmailSent = 0;

					boolean flag = database.updateProduct(product);
					if (flag) {
						Toast.makeText(context, "Product Updated",
								Toast.LENGTH_SHORT).show();
						AllProductsFragment.setListAdapter();
						DialogFragment dialogFragment = (DialogFragment) getFragmentManager()
								.findFragmentByTag("dialog");
						if (dialogFragment != null) {
							dialogFragment.dismiss();
						}
					} else {
						Toast.makeText(context, "Product not Updated!",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(context, "Every field is required!",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}
	}
}