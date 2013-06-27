package maine.epscor.angler;

import android.app.ActionBar;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemDetailFragment extends Fragment {
	public static final String ARG_ITEM_ID = "item_id";
	static AnglerDbAdapter db;
	static Cursor dataObject;
	static ActionBar actionBar;

	private String mItem;

	public ItemDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBar = getActivity().getActionBar();
		db = new AnglerDbAdapter(getActivity());
	    db.open();

		if (getArguments().containsKey(ARG_ITEM_ID))
			mItem = getArguments().getString(ARG_ITEM_ID);
		
		String[] fragmentArgs = getArguments().getString(ARG_ITEM_ID).split(" ");
		
		if (fragmentArgs[1].equals("lakes")) 
			dataObject = db.getLake(Integer.parseInt(fragmentArgs[0]));
		else 
			dataObject = db.getFish(Integer.parseInt(fragmentArgs[0]) + 1);
		
		mItem = fragmentArgs[1];
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rView = null;
		
		//Now that the view is inflated we will populate with content
		if (mItem != null && mItem.equals("lakes")) {
			rView = inflater.inflate(R.layout.fragment_lake,container, false);
			populateLakeFragment(rView, getActivity());
		}
		
		else if (mItem != null && mItem.equals("fish")) {
			rView = inflater.inflate(R.layout.fragment_fish, container, false);
			populateFishFragment(rView, getActivity());
		}

		return rView;
	}
	
	static void populateLakeFragment(View rootView, Context activityContext) {
		dataObject.moveToFirst();
		int lakePictureResource = dataObject.getInt(4);
		String lakeName = dataObject.getString(1);
		((ImageView) rootView.findViewById(R.id.lake_detail_picture)).setImageResource(lakePictureResource);
		actionBar.setTitle(lakeName);
		
		((TextView) rootView.findViewById(R.id.boat_feature_text)).setText(dataObject.getString(11));
		
		
		((TextView) rootView.findViewById(R.id.lure_feature_text)).setText(dataObject.getString(12));
		((TextView) rootView.findViewById(R.id.icefishing_feature_text)).setText(dataObject.getString(13));
		
		Cursor fishInLake = db.getFishFromLake(dataObject.getInt(0));
		
		ListView fishList = (ListView)rootView.findViewById(R.id.list_fish_layout);
		
		String [] fromFields = {"name"};
		int [] toViews = {android.R.id.text1};
		
		fishList.setAdapter(new SimpleCursorAdapter(
				activityContext,
				android.R.layout.simple_list_item_activated_1,
				fishInLake,
				fromFields,
				toViews,
				0));
		
		
		
		//fishInLake.close();
	}
	
	static void populateFishFragment(View rootView, Context activityContext) {
		dataObject.moveToFirst();
		
		int fishPictureResource = dataObject.getInt(1);
		
		String fishName = dataObject.getString(2);
		
		((ImageView) rootView.findViewById(R.id.fish_detail_picture)).setImageResource(fishPictureResource);
		((TextView) rootView.findViewById(R.id.fish_detail_title)).setText(fishName);
		
		/*LinearLayout fishListLayout = (LinearLayout)rootView.findViewById(R.id.list_fish_layout); 			
		fishInLake.moveToFirst();
		do {
			TextView tv = new TextView(activityContext);
			tv.setText(fishInLake.getString(1));
			tv.setPadding(20, 10, 10, 10);
			
			//Calculate scale pixels for the padding because it only accepts standard pixels
			int padding_in_dp = 20; 
		    final float scale = activityContext.getResources().getDisplayMetrics().density;
		    int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
		    
			tv.setPadding(padding_in_px, 15, 0, 15);
			tv.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
			tv.setTextSize(20);
			fishListLayout.addView(tv);
		} while (fishInLake.moveToNext());*/
		
	}

	@Override
	public void onPause() {
	    super.onPause();
	    if (db != null) {
	        db.close();
	        db = null;
	    }
	    
	    if (dataObject != null) {
	    	dataObject.close();
	    	dataObject = null;
	    }
	}
}
