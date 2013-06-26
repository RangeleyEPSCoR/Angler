package maine.epscor.angler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Homescreen extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homescreen);
		
		
	}
	
	public void onClickLakesButton(View v) {
		//Toast.makeText(getApplicationContext(), "clicked lakes!", Toast.LENGTH_SHORT).show();
		Intent launchFragment = new Intent();
		launchFragment.setClass(getApplicationContext(), ItemListActivity.class);
		
		
		
		launchFragment.putExtra("fragmentType", "lakes");
		startActivity(launchFragment);
		
	}
	
	public void onClickFishButton(View v) {
		//Toast.makeText(getApplicationContext(), "clicked lakes!", Toast.LENGTH_SHORT).show();
		Intent launchFragment = new Intent();
		launchFragment.setClass(getApplicationContext(), ItemListActivity.class);
		
		
		
		launchFragment.putExtra("fragmentType", "fish");
		startActivity(launchFragment);
		
	}
}
