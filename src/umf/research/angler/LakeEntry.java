package umf.research.angler;

import com.google.android.maps.GeoPoint;

public class LakeEntry {
	public String title;
	public String imgSrc;
	public GeoPoint location;
	public int [] imageIDs;
	
	//Additional Information??
	
	
	
	
	
	public LakeEntry(String conTitle, String conImgSrc, GeoPoint conLocation, int [] conImageIDs){
		title = conTitle;
		imgSrc = conImgSrc;
		location = conLocation;
		imageIDs = conImageIDs;
		
	}
	
	
	public String getTitle() {
		return title;
	}
	
	public String getImgSrc() {
		return imgSrc;
	}
	
	public GeoPoint getLocation() {
		return location;
	}
	
	public int [] getImageIDs() {
		return imageIDs;
	}
	
}
