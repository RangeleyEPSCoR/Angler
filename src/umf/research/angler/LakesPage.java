package umf.research.angler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LakesPage extends Activity {
    /** Called when the activity is first created. */
	
	//CREATING VIEWS
	Button navigate;
	RelativeLayout layoutFull;
	ImageView iv;
	TextView tv;
	TextView fishName;
	Button more;
	RelativeLayout tbrl;
	Gallery fishGallery;
	Integer [] fishPics;
	
	
	int lakeID;
	AnglerDbAdapter db;
	
	@Override
	public void onResume() {
		super.onResume();
		//lakeID = homescreen.history.pop();
	}
	
	public void onFinish() {
		super.finish();
		
	}
	
	@Override
	public void onPause() {
		super.onPause();
		homescreen.history.add(lakeID);
		lakeID = 0;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lakes_page);
        
        //SETTING VIEWS
        iv = (ImageView) findViewById(R.id.lakes_page_image);
	    navigate = (Button) findViewById(R.id.navigate);
	    tv = (TextView) findViewById(R.id.lakes_page_title);
	    layoutFull = (RelativeLayout) findViewById(R.id.lakes_page_full_layout);
	    tbrl = (RelativeLayout) findViewById(R.id.textbuttonlayout);
	    fishName = (TextView) findViewById(R.id.lakes_page_gallery_description);
	    
	    
	    //BUNDLE///////////////////////////////////////////////////////////////////////////////////////////////
	    
	    
	    if (homescreen.history.size() != 0) { // As long as the history stack contains a lakeID, we will load it into the LakesPage
	    	lakeID = homescreen.history.pop();
	    }
	    else { // 
	    	Intent i = new Intent();
			i.setClass(getApplicationContext(), homescreen.class);
			startActivity(i);
	    }
	    
	    
	    
	    
	    
	    //Bundle extras = null;
	    //extras = getIntent().getExtras();
	    //lakeID = -1; 
	    //lakeID = extras.getInt("lakeID");
	    
	    //extras.clear();
        
        //DATABASE STUFF
	    db = new AnglerDbAdapter(this);
	    db.open();
	    
	    Cursor moreInfo = db.getLakeInfo(lakeID);     	
	  	moreInfo.moveToFirst();
	  	iv.setImageResource(moreInfo.getInt(9));
	  	moreInfo.close();
	    
	    Cursor lakeInfo = db.getLakeTitle(lakeID);
	    lakeInfo.moveToFirst();
	    tv.setText(lakeInfo.getString(0));
	    //iv.setImageResource(lakeInfo.getInt(4));
	    lakeInfo.close();
 
    	//POPULATE FISH GALLERY
    	Cursor fishCursor = db.getLakesFishPics(lakeID);
		fishCursor.moveToFirst();
		fishPics = new Integer [fishCursor.getCount()];
		int fishPicsCounter = 0;        
		while (!fishCursor.isLast()){
			fishPics[fishPicsCounter] = fishCursor.getInt(0);
			fishCursor.moveToNext();
			fishPicsCounter++;
		}
		fishPics[fishPicsCounter] = fishCursor.getInt(0);
		fishCursor.close();
		fishPicsCounter = 0;
		db.close();
		
	
		CustomImageAdapter fishImageAdapter = new CustomImageAdapter(this, fishPics);
		fishGallery = (Gallery)findViewById(R.id.lakes_page_gallery);
		fishGallery.setAdapter(fishImageAdapter);
	    fishGallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	boolean solved = false;
            	db.open();
            	Cursor findFishID = db.getFishPicsandIDandName(fishPics[position]);
            	findFishID.moveToFirst();
            	fishName.setText(findFishID.getString(2));

            	Intent fishIntent = new Intent();
            	fishIntent.setClass(getApplicationContext(), FishPage.class);
            	fishIntent.putExtra("fishID", findFishID.getInt(1));
            	findFishID.close();
            	db.close();
            	startActivity(fishIntent);
    
            }
        });
	    
	    fishGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				boolean solved = false;
            	db.open();
            	Cursor findFishID = db.getFishPicsandIDandName(fishPics[position]);
            	findFishID.moveToFirst();
            	fishName.setText(findFishID.getString(2));


			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}	
	    });

        //GEOPOINT AND MAPS STUFF
        navigate.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				db.open();
				Cursor latLon = db.getLakePosition(lakeID);
				latLon.moveToFirst();
				Intent i = new Intent();
				i.setClass(getApplicationContext(), MapDraw.class);
				i.putExtra("lat", latLon.getFloat(0));
				i.putExtra("lon", latLon.getFloat(1));
				latLon.close();
				db.close();
				startActivity(i);
			}
        });
        
    	
        
        
        //"MORE" BUTTON STUFF
        more = (Button)findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {			
				Intent i = new Intent();
				db.open();
				Cursor moreInfo = db.getLakeInfo(lakeID);
//latitude, longitude, maxDepth, meanDepth, area, elevation, boatInfo, fishingInfo, iceFishingInfo     	
				moreInfo.moveToFirst();

				i.setClass(getApplicationContext(), LakesMore.class);
				i.putExtra("latitude", moreInfo.getFloat(0));
				i.putExtra("longitude", moreInfo.getFloat(1));
				i.putExtra("maxDepth", moreInfo.getInt(2));
				i.putExtra("meanDepth", moreInfo.getInt(3));
				i.putExtra("area", moreInfo.getInt(4));
				i.putExtra("elevation", moreInfo.getInt(5));
				i.putExtra("boatInfo", moreInfo.getString(6));
				i.putExtra("fishingInfo", moreInfo.getString(7));
				i.putExtra("iceFishingInfo", moreInfo.getString(8));

				moreInfo.close();
				db.close();
				
				startActivity(i);
			}
        });
        

    }
    
	public int getGalleryHeight() {
	    int galleryHeight = (int) (getGalleryWidth()/1.5); 
	    if (galleryHeight > layoutFull.getHeight() - (tv.getHeight() + navigate.getHeight() + 4)) {
	    	//galleryHeight = layoutFull.getHeight() - (tv.getHeight() + navigate.getHeight() + 4);
	    }
	    return galleryHeight;
	}
	
	public int getGalleryWidth() {
	    int galleryWidth = (int) (layoutFull.getWidth()/2);
	    return galleryWidth;
	}
    
    public class CustomImageAdapter extends BaseAdapter {
        int mGalleryItemBackground;
        private Context mContext;
        private Integer[] ImageIds;
        public CustomImageAdapter(Context c, Integer[] mImageIds) {
	    	ImageIds = mImageIds;
	        mContext = c;
	        TypedArray attrs = mContext.obtainStyledAttributes(R.styleable.HelloGallery);
	        mGalleryItemBackground = attrs.getResourceId(
	                R.styleable.HelloGallery_android_galleryItemBackground, 3);
	        attrs.recycle();
	    }

        public int getCount() {
            return ImageIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(ImageIds[position]);
            imageView.setLayoutParams(new Gallery.LayoutParams(getGalleryWidth(), getGalleryHeight()));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(mGalleryItemBackground);
            return imageView;
        }
    }
    
	
	public int fetchViewName(View view) {
		return 0;
	}
}
