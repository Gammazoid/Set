package set;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Set {

// variable used by all set types	
	
	
	
	
	

	
	private double threshold=1;
	
	private int[][] superhash = new int[16777256][];
	
	private Point[] set;
	private BufferedImage original;
	private int[] hashColor;
	private SetType typeOfSet;
	private int end=0;
	
	/*
	 * A set is defined recursively, that is, there are 2 steps: 1. initializing the set, 2. choosing a threshold value to get oterh point in the set
	 * 
	 * 
	 * 2 type of sets: XY and RGB,
	 * 
	 * XY set are transfer of points between the image and the set itself : P( x, y , hashCode)  [maybe even use base width]
	 * 
	 * RGB set are transfer between the hashCode and the set itself
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	public Set() {
		
	}
	
	
	
	public Set(BufferedImage original , SetType typeOfSet) {
		this.typeOfSet = typeOfSet;
		this.original = original;
		set = new Point[original.getWidth()*original.getHeight()];
		end=0;
	}
	
	
	
	
	
	public Set( BufferedImage original , SetType typeOfSet , Point initial) {
		
		this.original = original;
		this.typeOfSet = typeOfSet;
		set = new Point[original.getWidth()*original.getHeight()];
		
		if( typeOfSet == SetType.XY ) {
			if(initial != null) {
				set[0] = new Point( initial.getX() , initial.getY() , original.getRGB( (int)initial.getX() , (int)initial.getY() ) );
				original.setRGB( (int)initial.getX() , (int)initial.getY() , Color.WHITE.hashCode());
				end = 1;
			}
		}
		else if( typeOfSet == SetType.RGB ) {
			superhash = SetOperation.IMGtoShash( original );
			if( initial != null ) {
				int hashCode = new Color( original.getRGB( (int)initial.getX() , (int)initial.getY() ) ).hashCode();
				set[0] = new Point( new Color(hashCode) , superhash[hashCode+16777256] );
				superhash[hashCode+16777256]=null;
				end=1;
			}
		}
		
		
	}
	
	
	
	
	
	
	
	
	//Getters
	public int getEnd() {
		return this.end;
	}
	
	public Point[] getSet() {
		return this.set;
	}
	
	public int[] getHash() {
		return this.hashColor;
	}
	
	public SetType getType() {
		return this.typeOfSet;
	}
	
	public double getThreshold() {
		return this.threshold;
	}
	
	public BufferedImage getOriginal() {
		return this.original;
	}
	
	public int[][] getShash(){
		return this.superhash;
	}
	
	//Setters
	public void setEnd(int end) {
		this.end = end;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	
	public void setSet(Point[] set) {
		this.set=set;
	}
	
	public void setHash(int[] hashColor) {
		this.hashColor = hashColor;
	}
	
	
	//*****************************   TERTIARY METHOD  **********************************************************   TERTIARY METHOD  *******************************************************	
	
	
	
	
	
	
	
		public static BufferedImage makeWhite(int wid, int heh) {
				
			BufferedImage toRet=new BufferedImage(wid, heh, BufferedImage.TYPE_INT_ARGB);
			
			for(int i=0;i<wid;i++) {
				for(int j=0;j<heh;j++) {
					toRet.setRGB(i, j, -1);
				}
			}
				
			return toRet;
		}//END makeWhite()
		
		
		
		public static int[] IMGtoHash(BufferedImage bimg) {
			
			
			int[] hashRGB=new int[16777256];
			
			for(int i=0;i<bimg.getWidth();i++) {
				for(int j=0;j<bimg.getHeight();j++) {
					
					if(bimg.getRGB(i,j)!=-1) {
						hashRGB[bimg.getRGB(i,j)+16777256]++;
					}
					
				}
			}
			
			return hashRGB;
		}//END IMGtoHash()
				
		
		
	
}
