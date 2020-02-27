package set;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Set {

// variable used by all set types	
	
	
	
	
	

	
	private double threshold=1;
	
	private int[][] superhash = new int[16777256][];
	
	private Point[] set;
	private BufferedImage image;
	private int[] hashColor;
	private SetType typeOfSet;
	private int end=0;
	
	/*
	 * A set is defined recursively, that is, there are 2 steps: 1. initializing the set, 2. choosing a threshold value to get oterh point in the set
	 * 
	 * 
	 * 2 type of sets: XY and RGB, as defined in the enumeration
	 * 
	 *
	 * A set has 2 associated entity
	 * 		XY. Possess an image and an array of point: methods transfer points between the two. 
	 *			The intersection of both form the complete image. 
	 * 
	 * 		RGB. Possess a hashtable and an array of point: Methods transfer points between the two.
	 * 			The intersection of both form the complete image. 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * More precisely: 
	 * 	 XY: A set of type XY has its array point containing P( x, y, color ): the position of the point on the image and the color of the point
	 * 
	 * 	RGB: A set of type RGB has its array point containint P( red, green, blue, int[] location) : the location array contains all the position of this color on the image
	 * 
	 * 
	 */
	

	
	
	
	
	
	
	/**Initialize a set from an image. As a recursively defined set, an initial point must be provided. Also the typy of set is defined here permanently as it defines the behavior of a set
	 * 
	 * @param original 
	 * @param typeOfSet
	 * @param initial
	 */
	public Set( BufferedImage image , SetType typeOfSet , Point initial) {
		
		this.image = image;
		this.typeOfSet = typeOfSet;
		set = new Point[image.getWidth()*image.getHeight()];
		
		if( typeOfSet == SetType.XY ) {
			if(initial != null) {
				set[0] = new Point( initial.getX() , initial.getY() , image.getRGB( (int)initial.getX() , (int)initial.getY() ) );
				image.setRGB( (int)initial.getX() , (int)initial.getY() , Color.WHITE.hashCode());
				end = 1;
			}
		}
		else if( typeOfSet == SetType.RGB ) {
			superhash = SetOperation.IMGtoShash( image );
			if( initial != null ) {
				int hashCode = new Color( image.getRGB( (int)initial.getX() , (int)initial.getY() ) ).hashCode();
				set[0] = new Point( new Color(hashCode) , superhash[hashCode+16777256] );
				superhash[hashCode+16777256]=null;
				end=1;
			}
		}
		
		
	}
	
	
	
	
	
	
	
	
	// ********************************************		Getters		************************************************************
	
	/** 
	 * @return the index of the last non null point of the array of point 
	 */
	public int getEnd() {
		return this.end;
	}
	
	/**
	 * 
	 * @return the array of point representing the set
	 */
	public Point[] getSet() {
		return this.set;
	}
	
	/**
	 * 
	 * @return the SetType of the Set
	 */
	public SetType getType() {
		return this.typeOfSet;
	}
	
	/**
	 * @return the threshold defining the condition generating the set
	 */
	public double getThreshold() {
		return this.threshold;
	}
	
	/**
	 * 
	 * @return the image of the set: if XY-space, the image less the array of point; if RGB, the image from which the set is generated
	 */
	public BufferedImage getImage() {
		return this.image;
	}
	
	/**
	 * 
	 * @return the hashtable containing the location of every color on the image
	 */
	public int[][] getShash(){
		return this.superhash;
	}
	
	//		******************************************			Setters  		********************************************************
	
	/**Set the index of the last non null point of the array of point
	 * 
	 * @param end
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**Set the threshold defining the recursive condition of the Set
	 * 
	 * @param threshold
	 */
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	
	/**Set a new array of point for the Set
	 * 
	 * @param set
	 */
	public void setSet(Point[] set) {
		this.set=set;
	}
	

	
	
	
	
	
	

		
		
		
}
