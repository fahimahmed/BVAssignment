
package com.fahimahmed.bv.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.fahimahmed.bv.MainActivity;
import com.fahimahmed.bv.R;

public abstract class BaseSearchActivity extends FragmentActivity {

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_fragment_container);
      getActionBar().setDisplayHomeAsUpEnabled(true);
      handleIntent(getIntent());      
   }

   @Override
   protected void onNewIntent(Intent intent) {
      setIntent(intent);
      handleIntent(intent);
   }

   protected abstract void handleIntent(Intent intent);

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         case android.R.id.home:
            NavUtils.navigateUpTo(this,
                  new Intent(this, MainActivity.class));
            return true;
      }
      return super.onOptionsItemSelected(item);
   }  

}