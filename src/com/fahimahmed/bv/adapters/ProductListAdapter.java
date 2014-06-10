package com.fahimahmed.bv.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.fahimahmed.bv.R;
import com.fahimahmed.bv.database.Product;

public class ProductListAdapter extends ArrayAdapter<Product> {

	private Context context;
	private int layoutResc;
	private ArrayList<Product> products;

	public ProductListAdapter(Context context, int resource,
			ArrayList<Product> products) {
		super(context, resource, products);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.layoutResc = resource;
		this.products = products;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		;
		if (rowView == null) {
			rowView = inflater.inflate(layoutResc, parent, false);
			TextView tvProductName = (TextView) rowView
					.findViewById(R.id.tvListProductName);
			TextView tvProductPrice = (TextView) rowView
					.findViewById(R.id.tvListProductPrice);
			TextView tvProductQuantity = (TextView) rowView
					.findViewById(R.id.tvListProductQuantity);
			TextView tvProductSyncedOrNot = (TextView) rowView
					.findViewById(R.id.tvListSyncedOrNot);
			final ImageView imgPopUpMenu = (ImageView) rowView
					.findViewById(R.id.imgPopUpMenu);

			System.out.println("Product Name:" + products.get(position).name);

			tvProductName.setText(products.get(position).name);
			tvProductPrice.setText("Price: $" + products.get(position).price);
			tvProductQuantity.setText("In Stock: "
					+ products.get(position).quantity);
			if (products.get(position).isEmailSent == 0) {
				tvProductSyncedOrNot.setText("Not Synced");
				tvProductSyncedOrNot.setTextColor(R.color.Red);
			} else if (products.get(position).isEmailSent == 1) {
				tvProductSyncedOrNot.setText("Synced");
				tvProductSyncedOrNot.setTextColor(R.color.Green);
			}

			imgPopUpMenu.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// Creating the instance of PopupMenu
					PopupMenu popup = new PopupMenu(context, imgPopUpMenu);
					// Inflating the Popup using xml file
					popup.getMenuInflater().inflate(R.menu.popup_menu,
							popup.getMenu());

					// registering popup with OnMenuItemClickListener
					popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
						public boolean onMenuItemClick(MenuItem item) {
							Toast.makeText(context,
									"You Clicked : " + item.getTitle(),
									Toast.LENGTH_SHORT).show();
							return true;
						}
					});

					popup.show();// showing popup menu

				}
			});
		}

		return rowView;
	}

}
