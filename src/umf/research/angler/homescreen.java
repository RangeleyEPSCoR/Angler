package umf.research.angler;

import java.util.Stack;

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
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class homescreen extends Activity {
	
	
	
	RelativeLayout rl;
	
	Gallery lakesGallery;
	Gallery fishGallery;
	
	TextView laketv;
	TextView fishtv;
	
	CustomImageAdapter lakesImageAdapter;
	CustomImageAdapter fishImageAdapter;
	
	AnglerDbAdapter db;
	
	EditText et;
	
	 static Stack <Integer> history;
	
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        
        history = new Stack<Integer>();
        
        //CREATE AND OPEN DATABASE
        db = new AnglerDbAdapter(this);
        db.open();
        
	    //VIEWS
        rl = (RelativeLayout) findViewById(R.id.homescreen_layout_full);
        laketv = (TextView) findViewById(R.id.lake_text);
        fishtv = (TextView) findViewById(R.id.fish_text);
        et = (EditText) findViewById(R.id.search_box);
        
        //POPULATE LAKES GALLERY
        Cursor lakesCursor = db.getAllLakesPics();
        lakesCursor.moveToFirst();
        Integer [] lakesPics = new Integer [lakesCursor.getCount()];
        int lakesPicsCounter = 0;        
        while (!lakesCursor.isLast()){
        	lakesPics[lakesPicsCounter] = lakesCursor.getInt(0);
        	lakesCursor.moveToNext();
        	lakesPicsCounter++;
        }
    	lakesPics[lakesPicsCounter] = lakesCursor.getInt(0);
    	lakesCursor.close();
    	lakesPicsCounter = 0;
        
        //POPULATE FISH GALLERY
    	Cursor fishCursor = db.getAllFishPics();
		fishCursor.moveToFirst();
		final Integer [] fishPics = new Integer [fishCursor.getCount()];
		int fishPicsCounter = 0;        
		while (!fishCursor.isLast()){
			fishPics[fishPicsCounter] = fishCursor.getInt(0);
			fishCursor.moveToNext();
			fishPicsCounter++;
		}
		fishPics[fishPicsCounter] = fishCursor.getInt(0);
		fishCursor.close();
		fishPicsCounter = 0;
        
		//CLOSE DATABASE
        db.close();
        
        //LAKES GALLERY
        lakesImageAdapter = new CustomImageAdapter(this, lakesPics);
	    lakesGallery = (Gallery)findViewById(R.id.lakes_gallery);
	    lakesGallery.setAdapter(lakesImageAdapter);
	    lakesGallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	Intent lakesIntent = new Intent();
            	lakesIntent.setClass(getApplicationContext(), LakesPage.class);
            	history.add(position+1);
            	lakesIntent.putExtra("lakeID", position+1);
            	startActivity(lakesIntent);
            }
        });
	    
	    lakesGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				db.open();
				Cursor lakeTitle = db.getLakeTitle(position+1);
				lakeTitle.moveToFirst();
				laketv.setText(lakeTitle.getString(0));
				lakeTitle.close();
				db.close();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}	
	    });
	    
	    //FISH GALLERY
	    fishImageAdapter = new CustomImageAdapter(this, fishPics);
	    fishGallery = (Gallery)findViewById(R.id.fish_gallery);
	    fishGallery.setAdapter(fishImageAdapter);
	    fishGallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	Intent fishIntent = new Intent();
            	fishIntent.setClass(getApplicationContext(), FishPage.class);
            	fishIntent.putExtra("fishID", position+1);
            	startActivity(fishIntent);
            }
        });
	    
	    fishGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				db.open();
				Cursor fishTitle = db.getFishTitle(position+1);
				fishTitle.moveToFirst();
				fishtv.setText(fishTitle.getString(0));
				fishTitle.close();
				db.close();
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
	    if (galleryHeight > rl.getHeight() - (laketv.getHeight() + fishtv.getHeight() + et.getHeight() + 20)) {
	    	galleryHeight = rl.getHeight() - (laketv.getHeight() + fishtv.getHeight() + et.getHeight() + 20);
	    }
	    return galleryHeight;
	}
	
	public int getGalleryWidth() {
	    int galleryWidth = (int) (rl.getWidth() / 3);
	    return galleryWidth;
	}
}