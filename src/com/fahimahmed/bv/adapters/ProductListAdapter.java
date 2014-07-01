package com.fahimahmed.bv.adapters;

import java.util.ArrayList;

import android.app.DialogFragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.fahimahmed.bv.MainActivity;
import com.fahimahmed.bv.R;
import com.fahimahmed.bv.contentprovider.ProductsContract;
import com.fahimahmed.bv.database.DatabaseManager;
import com.fahimahmed.bv.database.Product;
import com.fahimahmed.bv.fragment.AllProductsFragment;
import com.fahimahmed.bv.fragment.InsertProductFragment;
import com.fahimahmed.bv.search.SearchActivity;
import com.fahimahmed.bv.util.SharedData;

public class ProductListAdapter extends ArrayAdapter<Product> {

	private Context context;
	private int layoutResc;
	private ArrayList<Product> products;
	private DatabaseManager database;
	private boolean fromAllProductFragment = true;

	public ProductListAdapter(Context context, int resource,
			ArrayList<Product> products, boolean fromAllProductFragment) {
		super(context, resource, products);
		this.context = context;
		this.layoutResc = resource;
		this.products = products;
		this.fromAllProductFragment = fromAllProductFragment;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		database = DatabaseManager.getInstance(context);

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

					PopupMenu popup = new PopupMenu(context, imgPopUpMenu);

					popup.getMenuInflater().inflate(R.menu.popup_menu,
							popup.getMenu());

					popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
						public boolean onMenuItemClick(MenuItem item) {

							switch (item.getItemId()) {
							case R.id.menuProductDelete:
//								database.deleteProduct(products.get(position).id);
								context.getContentResolver().delete(ProductsContract.CONTENT_URI, Product.PRODUCT_ID+"=?", new String[]{String.valueOf(products.get(position).id)});
								AllProductsFragment.setListAdapter();

								break;
							case R.id.menuProductEdit:
								SharedData sharedData = SharedData
										.getInstance();
								sharedData.setProduct(products.get(position));
								DialogFragment dialog = InsertProductFragment
										.newInstance();
								if (fromAllProductFragment) {
									dialog.show(((MainActivity) context)
											.getFragmentManager(), "dialog");
								} else {
									dialog.show(((SearchActivity) context)
											.getFragmentManager(), "dialog");
								}
								break;
							}
							return true;
						}
					});

					popup.show();
				}
			});
		}

		return rowView;
	}

}
