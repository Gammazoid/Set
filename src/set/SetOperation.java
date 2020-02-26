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
		
		
		BufferedImage a = loadImage("C:\\Users\\Dr.Manhattan\\eclipse-workspace\\Darwinin\\4D/motion1.JPG");
		BufferedImage b = loadImage("C:\\Users\\Dr.Manhattan\\eclipse-workspace\\Darwinin\\4D/motion4.JPG");

		int[][] shash = intersectIMG(a,b,!true);
		
		new display(shashtoIMG(shash , a.getWidth(), a.getHeight()));
		
	}
	
	
	
	
	
	//   **************************************   accessible method  ***********************************************
	
	
	
	public static void clusterTo(Set s , double threshold) {
		
		if(s.getType() == SetType.XY) {
			clusterXYTo( s , threshold);
		}
		else if( s.getType() == SetType.RGB ) {
			clusterRGBTo( s , threshold);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static Point expectedSetinRGB(Set s) {
		
		
		if( s.getType() == SetType.XY) {
			return MyMath.expectedPointXYinRGB( s.getSet() );
		}
		else {
			return MyMath.expectedPointRGBinRGB( s.getSet() );
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	public static Point varianceSetinRGB(Set s) {
		
		
		if( s.getType() == SetType.XY ) {
			return MyMath.variancePointXYinRGB( s.getSet() );
		}
		else {
			return MyMath.variancePointRGBinRGB( s.getSet() );
		}
		
		
	}
	
	
	
	
	
	
	







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
					for(double j=-Math.sqrt( Math.pow(tau,2)-(i*i) );j<=Math.sqrt( Math.pow(tau,2)-(i*i) );j++) {
						for(double k=-Math.sqrt( Math.pow(tau,2)-(i*i)-(j*j) );k<=Math.sqrt( Math.pow(tau,2)-(i*i)-(j*j) );k++) {
							
							if((x+i)>=0  &&  (x+i)<256  &&  (y+j)>=0  &&  (y+j)<256  &&  (z+k)>=0  &&  (z+k)<256) {
								int hashCode=new Color((int)(x+i), (int)(y+j), (int)(z+k)).hashCode();
								
								if(shash[hashCode+16777256]!=null  &&  Math.sqrt(Math.pow(i,2)+Math.pow(j,2)+Math.pow(k,2))<Math.sqrt(threshold)  ) {//&&  setRGB[start].getW()>hashColor[hashCode+16777256]
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static void clusterXYTo(Set s , double threshold) {
		
		BufferedImage original = s.getOriginal();
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
					//System.out.println(Point.dist(  new Point(new Color(hashCode)) , new Point(new Color(original.getRGB((int)(x+i), (int)(y+j))))  ));
					if( ( (i+x)>=0  &&  (i+x)<original.getWidth()  &&  j+y>=0  &&  j+y<original.getHeight())  &&  !(i==0  && j==0)  &&  original.getRGB((int)(x+i), (int)(y+j))!=Color.WHITE.hashCode()  &&  Point.dist(  new Point(new Color(hashCode)) , new Point(new Color(original.getRGB((int)(x+i), (int)(y+j))))  )<Math.abs(threshold)) {
						set[end]=new Point(x+i, y+j , original.getRGB((int)(x+i), (int)(y+j)));
						original.setRGB( (int)(x+i) , (int)(y+j) , Color.WHITE.hashCode() );
						end++;//System.out.println(end);
					}

				}
			}
			
			start++;
		}//END while()
		
		s.setThreshold(threshold);
		s.setEnd(end);
	
		
	}
	
	
	
	
	
	
	




	//          ************************************    secondary methods      ****************************************
	
	
	
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
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static BufferedImage XYtoIMG(Set s) {
		
		
		Point[] set = s.getSet();
		int end = s.getEnd();
		BufferedImage bimg = makeWhite( s.getOriginal().getWidth() , s.getOriginal().getHeight() );
		
		
		for(int a=0 ; a<end ; a++) {
			Point p = set[a];
			bimg.setRGB( (int)p.getX(), (int)p.getY() , (int)p.getZ() );
		}
		
		
		
		return bimg;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static BufferedImage RGBtoIMG(Set s) {
		
		Point[] set = s.getSet();
		int W=s.getOriginal().getWidth(), H=s.getOriginal().getHeight();
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
	// O( W*H+16777256+W*H*expected(occurence) )
	public static int[][] IMGtoShash(BufferedImage bimg) {
		
		System.out.print("Sashing...");
		
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
		
		
		System.out.println("...done");
		return superHash;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
	
	
	
	
	
	
	
	
	
	
	
	//   ***************************************************	Tertiary Method     **********************************
	
	
	
	
	
	
	
	
	
	public static BufferedImage makeWhite(int wid, int heh) {
		
		BufferedImage toRet=new BufferedImage(wid, heh, BufferedImage.TYPE_INT_ARGB);
		
		for(int i=0;i<wid;i++) {
			for(int j=0;j<heh;j++) {
				toRet.setRGB(i, j, -1);
			}
		}
		
		return toRet;
	}//END makeWhite()


	
	
	

	public static BufferedImage makeCopy(BufferedImage bimg) {
		
		BufferedImage copy=new BufferedImage(bimg.getWidth(), bimg.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for(int i=0;i<bimg.getWidth();i++) {
			for(int j=0;j<bimg.getHeight();j++) {
				copy.setRGB(i,j,bimg.getRGB(i, j));
			}
		}
		//new display(copy);
		return copy;
	}//END of makeCopy()


	
	
	
	
	
	
	public static BufferedImage toBufferedImage(Image img){
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static BufferedImage loadImage(String name) {
	
	
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
