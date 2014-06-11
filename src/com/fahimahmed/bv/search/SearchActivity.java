
package com.fahimahmed.bv.search;

import android.app.SearchManager;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;

import com.fahimahmed.bv.R;
import com.fahimahmed.bv.fragment.AllProductsFragment;

public class SearchActivity extends BaseSearchActivity {

	protected void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);

			SearchRecentSuggestions suggestions = 
				new SearchRecentSuggestions(this, RecentSuggestionsProvider.AUTHORITY, RecentSuggestionsProvider.MODE);
			suggestions.saveRecentQuery(query, null);
			
			AllProductsFragment fragment = AllProductsFragment.newInstance(query);
			getFragmentManager()
			      .beginTransaction()
			      .replace(R.id.fragment_container, fragment)
			      .commit();
		}
	}    
}
