
package com.fahimahmed.bv.search;

import android.content.SearchRecentSuggestionsProvider;

public class RecentSuggestionsProvider extends SearchRecentSuggestionsProvider {

	public static final String AUTHORITY = RecentSuggestionsProvider.class.getName();
	public static final int MODE = DATABASE_MODE_QUERIES;
	
	public RecentSuggestionsProvider() {
		setupSuggestions(AUTHORITY, MODE);
	}
	
}
