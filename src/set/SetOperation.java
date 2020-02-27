package set;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class SetOperation {

	public static void main(String[] args) {
		System.out.println("Hello World!");
		
		
		BufferedImage a = loadImage("C:\\Users\\Dr.Manhattan\\eclipse-workspace\\Darwinin\\test/test8.JPG");
		BufferedImage b = loadImage("C:\\Users\\Dr.Manhattan\\eclipse-workspace\\Darwinin\\4D/motion4.JPG");

		
		
		Set s = new Set(a, SetType.RGB , new Point(43,35));
		Point max = findHighestFrequency( s.getShash() , b.getWidth() );		System.out.println(max.show() );
		Set s2 = new Set(a, SetType.RGB , new Point(43,35));
		
		clusterTo(s,14);
		clusterWithFrequencyTo(s2, 30);
		
		BufferedImage bimg = SettoIMG(s);		new display(bimg);
		BufferedImage bimg2 = SettoIMG(s2);		new display(bimg2);
		
		
	}
	
	
	
	
	
	//   **************************************   accessible method  ***********************************************
	
	
	
	
	/**
	 * 
	 * Calls the private method in charge of the clustering depending on the SetType of the set gicen in parameter
	 * 
	 * @param s the set that must be clustered
	 * @param threshold the value defining the recursivity of the set
	 */
	public static void clusterTo(Set s , double threshold) {
		
		if(s.getType() == SetType.XY) {
			clusterXYTo( s , threshold);
		}
		else if( s.getType() == SetType.RGB ) {
			clusterRGBTo( s , threshold);
		}
		
	}
	
	
	
	
	
	
	/**Call the private method in charge of clustering with additional condition on the frequency of each color, depending on the type of set (only support RGB set atm)
	 * 
	 * @param s the set that must be clustered
	 * @param threshold the value defining the recursivity of the set
	 */
	public static void clusterWithFrequencyTo(Set s , double threshold) {
		if(s.getType() == SetType.RGB ) {
			clusterRGBwithFrequencyTo(s, threshold);
		}
	}
	
	
	
	
	
	
	/**
	 * Calls the private method responsible for finding the expected color, depends on the SetType of the set passed as argument
	 * 
	 * @param s the set for which we want the expected color
	 * @return the Point representing the expected color
	 */
	public static Point expectedSetinRGB(Set s) {
		
		
		if( s.getType() == SetType.XY) {
			return MyMath.expectedPointXYinRGB( s.getSet() );
		}
		else {
			return MyMath.expectedPointRGBinRGB( s.getSet() );
		}
		
		
	}
	
	
	
	
	
	
	
	
	/**
	 * Calls the private method responsible for finding the variance of the color, depends on the SetType of the set passed as argument
	 * 
	 * @param s the set for which we want the variance of the  color
	 * @return the Point representing the variance of the color
	 */
	public static Point varianceSetinRGB(Set s) {
		
		
		if( s.getType() == SetType.XY ) {
			return MyMath.variancePointXYinRGB( s.getSet() );
		}
		else {
			return MyMath.variancePointRGBinRGB( s.getSet() );
		}
		
		
	}
	
	
	
	
	
	
	




	/**
	 * Find the intersection in the color space of the two images passed as arguments and returns a hashtable
	 * The hashtable contains all the location of the pixel for each color, these location depend on the image
	 * thus, specify which image the location should be used
	 * 
	 * @param bimg1
	 * @param bimg2
	 * @param onFirstImg true; the hashtable will contains the location of the color on the first image, false; the hashtable will contains the location of the color on the first image
	 * @return hashtable (int[][]) containing the intersection with array location depending on the value of param onFirstImg
	 */
	public static int[][] intersectIMG( BufferedImage bimg1 , BufferedImage bimg2 , boolean onFirstImg) {
		
		int[][] shash1 = IMGtoShash( bimg1 );
		int[][] shash2 = IMGtoShash( bimg2 );
		
		int[][] shashIntersect = new int[16777256][];
		
		for(int a = 0 ; a<16777256 ;a++) {
			
			if(shash1[a]!=null  &&  shash2[a]!=null) {
				
				if(onFirstImg) {
					shashIntersect[a] = shash1[a];
				}
				else {
					shashIntersect[a] = shash2[a];
				}
				
				
			}
			
		}//for(a)
		
		
		return shashIntersect;
	}//end intersectIMG


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Cluster the set in RGB space using a recursive definition under the condition x^2+y^2+z^2<=r^2
	 * x,y,z are the distance for between each color red,green,blue
	 * 
	 * @param s the set for which we want the expected color
	 * @param threshold the radius defining the maximum distance between two colors
	 */
	private static void clusterRGBTo(Set s , double threshold) {
		
		double tau = Math.sqrt(threshold);
		int[][] shash = s.getShash();
		Point[] set = s.getSet();
		int end=s.getEnd();
		int start=0;

		if(end!=0) {
			while(start<end) {
				
				int x=(int)set[start].getX();
				int y=(int)set[start].getY();
				int z=(int)set[start].getZ();
				
				for(double i=-Math.floor(tau);i<=Math.floor(tau);i++) {
					for(double j=-Math.floor(tau);j<=Math.floor(tau);j++) {
						for(double k=-Math.floor(tau);k<=Math.floor(tau);k++) {
							
							if((x+i)>=0  &&  (x+i)<256  &&  (y+j)>=0  &&  (y+j)<256  &&  (z+k)>=0  &&  (z+k)<256) {
								int hashCode=new Color((int)(x+i), (int)(y+j), (int)(z+k)).hashCode();
								
								if(shash[hashCode+16777256]!=null  &&  Math.sqrt(Math.pow(i,2)+Math.pow(j,2)+Math.pow(k,2))<Math.sqrt(threshold)  ) {  // The condition for adding a point: distance of color<threshold
									set[end]=new Point(x+i, y+j, z+k, shash[hashCode+16777256]);
									shash[hashCode+16777256]=null;
									end++;
								}
							}
							
						}
					}
				}
				
				start++;
			}//END while()
			
			
			s.setEnd(end);System.out.println(end);
			s.setThreshold(threshold);
		}
		else{
			System.out.println("Set not initialized");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Cluster the set in RGB space using a recursive definition under the condition x^2+y^2+z^2<=r^2 AND an additional condition requiring that the color must have a lower frequency than the previous one
	 * x,y,z are the distance for between each color red,green,blue
	 * 
	 * @param s the set for which we want the expected color
	 * @param threshold the radius defining the maximum distance between two colors
	 */
	private static void clusterRGBwithFrequencyTo(Set s , double threshold) {
		
		double tau = Math.sqrt(threshold);
		int[][] shash = s.getShash();
		Point[] set = s.getSet();
		int end=s.getEnd();
		int start=0;

		if(end!=0) {
			while(start<end) {
				
				int x=(int)set[start].getX();
				int y=(int)set[start].getY();
				int z=(int)set[start].getZ();
				
				for(double i=-Math.floor(tau);i<=Math.floor(tau);i++) {
					for(double j=-Math.sqrt( Math.pow(tau,2)-(i*i) );j<=Math.sqrt( Math.pow(tau,2)-(i*i) );j++) {
						for(double k=-Math.sqrt( Math.pow(tau,2)-(i*i)-(j*j) );k<=Math.sqrt( Math.pow(tau,2)-(i*i)-(j*j) );k++) {
							
							if((x+i)>=0  &&  (x+i)<256  &&  (y+j)>=0  &&  (y+j)<256  &&  (z+k)>=0  &&  (z+k)<256) {
								int hashCode=new Color((int)(x+i), (int)(y+j), (int)(z+k)).hashCode();
								
								if(shash[hashCode+16777256]!=null  &&  Math.sqrt(Math.pow(i,2)+Math.pow(j,2)+Math.pow(k,2))<Math.sqrt(threshold)  &&   shash[hashCode+16777256].length<=set[start].getLoc().length ) {      // The condition for adding a point: distance of color<threshold  AND frequency1<frequency2
									set[end]=new Point(x+i, y+j, z+k, shash[hashCode+16777256]); 
									shash[hashCode+16777256]=null;
									end++;
								}
							}
							
						}
					}
				}
				
				start++;
			}//END while()
			
			
			s.setEnd(end);						//System.out.println(end);
			s.setThreshold(threshold);
		}
		else{
			System.out.println("Set not initialized");
		}
		
	}









	
	
/**
 * 
 * Cluster the point in the XY space under the condition that the points must be nearest neighbor AND the distance between their color satisfy 
 * x^2+y^2+z^2<=r^2, where r is the threshold
 * 
 * @param s
 * @param threshold
 */
	private static void clusterXYTo(Set s , double threshold) {
		
		BufferedImage original = s.getImage();
		Point[] set = s.getSet();
		int end = s.getEnd();
		int start = 0;
		double distanceXY = 1;
		
		
		while(start<end) {
			
			int x=(int)set[start].getX();
			int y=(int)set[start].getY();//
			int hashCode = (int)set[start].getZ();
			
			for(double i=-distanceXY;i<=distanceXY;i++) {
				for(double j=-distanceXY;j<=distanceXY;j++) {
																														// The condition for adding a point: nearest neighbor  AND distance of color<threshold
					if( ( (i+x)>=0  &&  (i+x)<original.getWidth()  &&  j+y>=0  &&  j+y<original.getHeight())  &&  !(i==0  && j==0)  &&  original.getRGB((int)(x+i), (int)(y+j))!=Color.WHITE.hashCode()  &&  Point.dist(  new Point(new Color(hashCode)) , new Point(new Color(original.getRGB((int)(x+i), (int)(y+j))))  )<Math.abs(threshold)) {
						set[end]=new Point(x+i, y+j , original.getRGB((int)(x+i), (int)(y+j)));
						original.setRGB( (int)(x+i) , (int)(y+j) , Color.WHITE.hashCode() );
						end++;
					}

				}
			}
			
			start++;
		}//END while()
		
		s.setThreshold(threshold);
		s.setEnd(end);
	
		
	}
	
	
	
	
	
	
	




	//          ************************************    secondary methods      ****************************************
	
	
	/**The method calls the private method for turning the set into an image depending on the SetType of the set passed as argument
	 * 
	 * @param s the set for which we want to get the image
	 * @return a bufferedimage of the set
	 */
	public static BufferedImage SettoIMG( Set s ) {

		BufferedImage b=null;
		
		if( s.getType() == SetType.XY ) {
			b=XYtoIMG(s);
		}
		else if( s.getType() == SetType.RGB ) {
			b=RGBtoIMG(s);
		}
		
		return b;
	}
	
	

	
	
	
	
	
	
	
	
	
	
	/**Turn the array of point of the set in the XY space into an image
	 * 
	 * @param s the set to be drawn
	 * @return the bufferedimage of the set
	 */
	private static BufferedImage XYtoIMG(Set s) {
		
		
		Point[] set = s.getSet();
		int end = s.getEnd();
		BufferedImage bimg = makeWhite( s.getImage().getWidth() , s.getImage().getHeight() );
		
		
		for(int a=0 ; a<end ; a++) {
			Point p = set[a];
			bimg.setRGB( (int)p.getX(), (int)p.getY() , (int)p.getZ() );
		}
		
		
		
		return bimg;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**Turn the array of point of the set in the RGB space into an image
	 * 
	 * @param s the set to be drawn
	 * @return the bufferedimage of the set
	 */
	private static BufferedImage RGBtoIMG(Set s) {
		
		Point[] set = s.getSet();
		int W=s.getImage().getWidth(), H=s.getImage().getHeight();
		BufferedImage bimg = makeWhite( W , H );
		
		for(int a=0;a<s.getEnd();a++) {
			
			Point p = set[a];
			int[] location = set[a].getLoc();
			
			if(location!=null) {
				for(int b=0;b<location.length;b++) {
					
					int x = location[b]%W;
					int y = (location[b]-x)/W;
					
					bimg.setRGB( x , y , new Color( (int)p.getX() , (int)p.getY() , (int)p.getZ() ).hashCode() );
					
				}//for(b)
			}
			
		}//for(a)

		
		return bimg;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// uses base Width : n = j*W+i  ---> i = n%W , j = (n-i)/W
	// O( W*H+16777256+W*H*frequency )
	
	/**transform an image into an hashtable[][] for which the index are the hashcode of the color and the array associated the location in the image of each color
	 * 
	 * @param bimg bufferedimage to be made into a hashtable
	 * @return a hastable
	 */
	public static int[][] IMGtoShash(BufferedImage bimg) {
		
		//System.out.print("Sashing...");
		
		int[][] superHash = new int[16777256][];
		
		int[] hash = new int[16777256];
		
		for(int i=0;i<bimg.getWidth();i++) {
			for(int j=0;j<bimg.getHeight();j++) {
				if( bimg.getRGB(i,j)!=Color.WHITE.hashCode() ) {
					hash[bimg.getRGB(i,j)+16777256]++;
				}
			}
		}
		
		
		
		for(int a=0;a<16777256;a++) {
			if(hash[a]!=0) {
				superHash[a]=new int[hash[a]];
			}
			else {
				superHash[a]=null;
			}
		}
		
		
		
		for(int i=0;i<bimg.getWidth();i++) {
			for(int j=0;j<bimg.getHeight();j++) {
				
				//if the color is not white
				if( bimg.getRGB(i,j)!=Color.WHITE.hashCode() ) {
					int indexA = bimg.getRGB(i,j)+16777256;
					
					//go through that color set of point xy
					for(int a=0;a<superHash[indexA].length;a++) {
						
						//the first one that is empty, put it there
						if(superHash[indexA][a]==0) {
							superHash[indexA][a] = j*bimg.getWidth()+i;
							break;
						}
						
					}//for(a)
					
				}//if(!white)
			}//for(j)
		}//for(i)
		
		
		//System.out.println("...done");
		return superHash;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**Turns a hashtable into an image
	 * 
	 * @param shash the hashtable contains the location of each color in the image
	 * @param W the width of the image
	 * @param H the height of the image
	 * @return the image of the hashtable
	 */
	
	public static BufferedImage shashtoIMG(int[][] shash, int W, int H) {
		
		BufferedImage bimg =  makeWhite( W , H );
		
		for(int a = 0 ; a< 16777256 ; a++ ) {
			
			if(shash[a]!=null) {
				
				for(int b=0;b<shash[a].length;b++) {
					
					int x = shash[a][b]%W;
					int y = (shash[a][b]-x)/W;
					bimg.setRGB( x , y , a-16777256 );
					
				}//for(b)
				
			}//if(!null)
			
		}//for(a)
		

		return bimg;
	}
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @param shash the hashtable to be parsed
	 * @param W the width of the image associated with the hashtable
	 * @return the Color with the highest frequency
	 */
	public static Point findHighestFrequency(int[][] shash , int W) {
		
		
		int biggestLength = 0;
		int indexBiggestLength=-1;
		
		for(int a=0;a<16777256;a++) {
			if(shash[a]!=null  &&  shash[a].length>biggestLength) {
				biggestLength=shash[a].length;
				indexBiggestLength=a;
			}
		}
		
		int x=shash[indexBiggestLength][1]%W;
		int y=(shash[indexBiggestLength][1]-x)/W;
		
		return new Point(x,y);
	}
	
	
	
	
	
	
	//   ***************************************************	Tertiary Method     **********************************
	
	
	
	
	
	
	
	/**
	 * 
	 * @param width the width of the bufferedimage to be returned
	 * @param height the height of the bufferedimage to be returned
	 * @return a bufferedimage of white pixel
	 */
	
	private static BufferedImage makeWhite(int width, int height) {
		
		BufferedImage toRet=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				toRet.setRGB(i, j, -1);
			}
		}
		
		return toRet;
	}//END makeWhite()


	
	
	
/**Make a copy of the bufferedimage passed as arguments
 * 
 * @param bimg the bufferedimage to be copied
 * @return the dopy of the bufferedimage passes as arguments
 */
	private static BufferedImage makeCopy(BufferedImage bimg) {
		
		BufferedImage copy=new BufferedImage(bimg.getWidth(), bimg.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for(int i=0;i<bimg.getWidth();i++) {
			for(int j=0;j<bimg.getHeight();j++) {
				copy.setRGB(i,j,bimg.getRGB(i, j));
			}
		}
		//new display(copy);
		return copy;
	}//END of makeCopy()


	
	
	
	
	
/**
 * 	
 * @param img the image to be turned into a bufferedimage
 * @return a bufferedimage generated from the image
 */
	private static BufferedImage toBufferedImage(Image img){
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** Loads an image from the computer and turn it into a bufferedimage
	 * 
	 * @param name the location of the image in the computer
	 * @return the bufferedimage of the image loaded
	 */
	private static BufferedImage loadImage(String name) {
	
	
	BufferedImage bimg=null;

    try {     
    	Image img = ImageIO.read(new File(name));
		bimg=toBufferedImage(img);
    } catch (IOException e) {      
    	e.printStackTrace();    
    } 
	
	return bimg;
	}
	
	
	
	
}
