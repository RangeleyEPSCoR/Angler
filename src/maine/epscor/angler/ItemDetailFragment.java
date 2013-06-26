package maine.epscor.angler;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";
	AnglerDbAdapter db;
	Cursor dataObject;
	/**
	 * The dummy content this fragment is presenting.
	 */
	private String mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		 db = new AnglerDbAdapter(getActivity());
	     db.open();
	     
	     CursorLoader mCursorLoader = new CursorLoader(getActivity());
		
		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = getArguments().getString(
					ARG_ITEM_ID);
		}
		
		String[] fragmentArgs = getArguments().getString(ARG_ITEM_ID).split(" ");
		
		
		if (fragmentArgs[1].equals("lakes")) {
			dataObject = db.getLake(Integer.parseInt(fragmentArgs[0]));
			populateLakeFragment();
		}
		
		else {
			dataObject = db.getFish(Integer.parseInt(fragmentArgs[0]) + 1);
			populateFishFragment();
		}
		
		mItem = fragmentArgs[1];
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = null;
		

		// Show the dummy content as text in a TextView.
		if (mItem != null && mItem.equals("lakes")) {
			rootView = inflater.inflate(R.layout.fragment_lake,
					container, false);
			dataObject.moveToFirst();
			
			int lakePictureResource = dataObject.getInt(4);
			
			String lakeName = dataObject.getString(1);
			
			((ImageView) rootView.findViewById(R.id.lake_detail_picture)).setImageResource(lakePictureResource);
			((TextView) rootView.findViewById(R.id.lake_detail_title)).setText(lakeName);

			String extraInfo = "";
			extraInfo += dataObject.getString(11);
			extraInfo += dataObject.getString(12);
			extraInfo += dataObject.getString(13);
			
			
			((TextView) rootView.findViewById(R.id.extra_info)).setText(extraInfo);
			
		}
		
		else if (mItem != null && mItem.equals("fish")) {
			rootView = inflater.inflate(R.layout.fragment_fish,
					container, false);
			dataObject.moveToFirst();
			
			int fishPictureResource = dataObject.getInt(3);
			
			String fishName = dataObject.getString(1);
			
			((ImageView) rootView.findViewById(R.id.fish_detail_picture)).setImageResource(fishPictureResource);
			((TextView) rootView.findViewById(R.id.fish_detail_title)).setText(fishName);
			
		}

		return rootView;
	}
	
	static void populateLakeFragment() {
		
	}
	
	static void populateFishFragment() {
		
		
		
	}
}
