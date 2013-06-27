package maine.epscor.angler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
	
	public void onClickWhatIs(View v) {
		((Button) findViewById(R.id.what_is)).setBackgroundColor(Color.parseColor("#33b5e5"));
		
		/*Intent launchFragment = new Intent();
		launchFragment.setClass(getApplicationContext(), ItemListActivity.class);
		launchFragment.putExtra("fragmentType", "fish");
		startActivity(launchFragment);*/
		
		
	}
	
}
