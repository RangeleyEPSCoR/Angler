package umf.research.angler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LakesMore extends Activity {

	TextView t;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
      setContentView(R.layout.lakes_more);

	  t = (TextView) findViewById(R.id.textView1);

//latitude, longitude, maxDepth, meanDepth, area, elevation, boatInfo, fishingInfo, iceFishingInfo     	
	  Bundle extras = getIntent().getExtras();
	  t.append("Lat: " + extras.getFloat("latitude") + "\n");
	  t.append("Long: " + extras.getFloat("longitude") + "\n");
	  t.append("Max Depth: " + extras.getInt("maxDepth") + "\n");
	  t.append("Avg Depth: " + extras.getInt("meanDepth") + "\n");
	  t.append("Area: " + extras.getInt("area") + "\n");
	  t.append("Elevation: " + extras.getInt("elevation") + "\n");
	  t.append("Boating: " + extras.getString("boatInfo") + "\n");
	  t.append("Fishing: " + extras.getString("fishingInfo") + "\n");
	  t.append("Ice Fishing: " + extras.getString("iceFishingInfo") + "\n");
	  

      //setListAdapter(new ArrayAdapter<String>(this, R.layout.lakes_more_list_item, hello));
	  //ListView lv = getListView();
	  //lv.setTextFilterEnabled(true);
	  
	  
	  /*
	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	      // When clicked, show a toast with the TextView text
	      Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
	          Toast.LENGTH_SHORT).show();
	    }
	  });
	  */
	}
	
	
}
