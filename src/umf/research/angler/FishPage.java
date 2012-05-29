package umf.research.angler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FishPage extends Activity {
	
	int fishID;
	AnglerDbAdapter db;
	LinearLayout ll;
	ImageView iv;
	TextView title;
	Gallery lakesGallery;
	Integer [] lakesPics;
	TextView description;
	
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fish_page);
        
        //VIEWS
        ll = (LinearLayout) findViewById(R.id.fish_page_full_layout);
        title = (TextView) findViewById(R.id.fish_page_title);
        iv = (ImageView) findViewById(R.id.fish_page_image);
        description = (TextView) findViewById(R.id.lakes_page_gallery_description);
        
        //BUNDLE
	    Bundle extras = getIntent().getExtras();
	    fishID = extras.getInt("fishID");
//	    extras.clear();

	    db = new AnglerDbAdapter(this);
	    
	    db.open();
	    Cursor fishInfo = db.getFishInfo(fishID);
	    fishInfo.moveToFirst();
	    
	    title.setText(fishInfo.getString(1));
	    iv.setImageResource(fishInfo.getInt(3));
	   
 	    

	    fishInfo.close();
	    
	    Cursor lakesCursor = db.getFishsLakePics(fishID);
	    //Toast.makeText(getApplicationContext(),lakesCursor.getCount() + "" ,Toast.LENGTH_SHORT).show();
		lakesCursor.moveToFirst();
		lakesPics = new Integer [lakesCursor.getCount()];
		int lakePicsCounter = 0;        
		while (!lakesCursor.isLast()){
			lakesPics[lakePicsCounter] = lakesCursor.getInt(0);
			lakesCursor.moveToNext();
			lakePicsCounter++;
		}
		try {
		lakesPics[lakePicsCounter] = lakesCursor.getInt(0);
		}
		catch (CursorIndexOutOfBoundsException e) {
			Toast.makeText(getApplicationContext(),"That species of fish cannot be found\nin any of the current lakes" ,Toast.LENGTH_SHORT).show();
		}
		lakesCursor.close();
		lakePicsCounter = 0;
	    
	    
	    
	    db.close();
      
	    CustomImageAdapter lakeImageAdapter = new CustomImageAdapter(this, lakesPics);
		lakesGallery = (Gallery)findViewById(R.id.fish_page_gallery);
		lakesGallery.setAdapter(lakeImageAdapter);
	    lakesGallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	boolean solved = false;
            	db.open();
            	Cursor findLakeID = db.getLakePicsandID();
            	findLakeID.moveToFirst();
            	for (int i = 0;i < findLakeID.getCount(); i++) {
            		for (int j = 0; j < lakesPics.length; j++) {
	            		if (lakesPics[j] == findLakeID.getInt(0)){
	            			solved = true;
	            			Intent lakeIntent = new Intent();
	    	            	lakeIntent.setClass(getApplicationContext(), LakesPage.class);
	    	            	lakeIntent.putExtra("lakeID", findLakeID.getInt(1));
	    	            	findLakeID.close();
	    	            	db.close();
	    	            	startActivity(lakeIntent);
	    	            	break;
	            		}
            		}
            		if (solved == true) {
            			break;
            		}
            		else {
            			findLakeID.moveToNext();
            		}
            	}
            }
        });
	    
	    lakesGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				boolean solved = false;
            	db.open();
            	Cursor findLakeID = db.getLakePicsandID();
            	findLakeID.moveToFirst();
            	for (int i = 0;i < findLakeID.getCount(); i++) {
            		for (int j = 0; j < lakesPics.length; j++) {
	            		if (lakesPics[j] == findLakeID.getInt(0)){
	            			solved = true;
	            			Intent lakeIntent = new Intent();
	    	            	lakeIntent.setClass(getApplicationContext(), LakesPage.class);
	    	            	//lakeIntent.putExtra("lakeID", findLakeID.getInt(1));
	    	            	homescreen.history.add(findLakeID.getInt(1));
	    	            	findLakeID.close();
	    	            	db.close();
	    	            	break;
	            		}
            		}
            		if (solved == true) {
            			break;
            		}
            		else {
            			findLakeID.moveToNext();
            		}
            	}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}	
	    });
	    
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
    
	public int getGalleryHeight() {
	    int galleryHeight = (int) (getGalleryWidth()/1.5); 
	    if (galleryHeight > ll.getHeight() - (iv.getHeight() + title.getHeight() + 4)) {
	    	//galleryHeight = layoutFull.getHeight() - (tv.getHeight() + navigate.getHeight() + 4);
	    }
	    return galleryHeight;
	}
	
	public int getGalleryWidth() {
	    int galleryWidth = (int) (ll.getWidth()/2);
	    return galleryWidth;
	}
}
