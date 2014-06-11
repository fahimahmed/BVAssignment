package com.fahimahmed.bv.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fahimahmed.bv.R;
import com.fahimahmed.bv.adapters.ProductListAdapter;
import com.fahimahmed.bv.database.DatabaseManager;
import com.fahimahmed.bv.database.Product;

public class AllProductsFragment extends Fragment {

	private static ListView mListView;
	private static ArrayList<Product> products;
	private static DatabaseManager database;
	private static Context context;
	private Activity mActivity;
	private static final String KEY_QUERY = "query";
	private boolean fromSearch = true;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_all_products, container,
				false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		context = this.mActivity;
		mListView = (ListView) view.findViewById(R.id.list);
		database = DatabaseManager.getInstance(context);

		if (getArguments().containsKey(KEY_QUERY)) {
			String query = getArguments().getString(KEY_QUERY);
			doSearch(query);
		} 
		
		if(getArguments().containsKey("fromSearch")) {
			fromSearch = getArguments().getBoolean("fromSearch");
			if(!fromSearch){
				setListAdapter();
			}
		}

	}

	public static void setListAdapter() {
		products = getProducts();
		ProductListAdapter adapter = new ProductListAdapter(context,
				R.layout.product_list_item, products);
		mListView.setAdapter(adapter);

	}

	protected void doSearch(String query) {
		ArrayList<Product> results = new ArrayList<Product>();
		products = getProducts();
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).name.toLowerCase()
					.contains(query.toLowerCase())) {
				results.add(products.get(i));
			}
		}
		ProductListAdapter adapter = new ProductListAdapter(context,
				R.layout.product_list_item, results);
		mListView.setAdapter(adapter);
	}

	public static ArrayList<Product> getProducts() {
		ArrayList<Product> products = database.getAllProducts();
		return products;
	}

	public void showDialog() {
		// Create the fragment and show it as a dialog.
		DialogFragment newFragment = InsertProductFragment.newInstance();
		newFragment.show(getFragmentManager(), "dialog");
	}

	public static AllProductsFragment newInstance(String query) {
		AllProductsFragment fragment = new AllProductsFragment();
		Bundle bundle = new Bundle();
		bundle.putString(KEY_QUERY, query);
		fragment.setArguments(bundle);
		return fragment;
	}
}
