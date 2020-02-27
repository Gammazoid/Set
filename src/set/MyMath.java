package set;

import java.awt.Color;

public class MyMath{
	
	
	

	
	
	
	
	
	
	/**Give the expected position in the image (XY-space) for a given set of point having SetType.XY
	 * 
	 * @param pts the array of point P( x ,y )
	 * @return the expected position in the image of the set of point 
	 */
	public static Point expectedPointXYinXY(Point[] pts) {
		
		double muX=0, muY=0;
		double counter=0;
		
		for(int a=0;a<pts.length;a++) {
			
			if(pts[a]!=null) {
				muX+=pts[a].getX();
				muY+=pts[a].getY();
				counter++;
			}
			
		}
		
		
		return new Point( muX/counter , muY/counter );
	}
	
	
	
	
	
	
	
	

	
	

	/**Return the expected color of a set of point having type SetType.XY
	 * 	
	 * @param pts an array of point consisting of P( red, green , blue )
	 * @return the expected color of the set
	 */
	public static Point expectedPointXYinRGB(Point[] pts) {
		
		double muR=0, muG=0, muB=0;
		double counter=0;
		
		for(int a=0;a<pts.length;a++) {
			
			if(pts[a]!=null) {
				
				Point c = new Point( new Color( (int)pts[a].getZ() ) );
				muR+=c.getX();
				muG+=c.getY();
				muB+=c.getZ();
				counter++;
								
			}
			
		}
		
		
		return new Point( muR/counter ,  muG/counter , muB/counter );
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/**Return the expected location in the image (XY-space) of each color present in the set having type RGB
 * 
 * @param pts the array of point having P( red ,green , blue)
 * @param W the width of the image from which the set was generated
 * @return the expected location of each of the color in the set
 */
	public static Point[] expectedPointRGBinXY(Point[] pts , int W) {

		Point[] expected = new Point[pts.length];
		
		for(int a=0;a<pts.length;a++) {
			
			if(pts[a]!=null) {
				
				int[] location = pts[a].getLoc();
				double muX = 0 , muY = 0;
				
				for(int b=0;b<location.length;b++) {
					
					int x = location[b]%W;
					int y = (location[b]-x)/W;
					
					muX+=x/(double)location.length;
					muY+=y/(double)location.length;
					
				}//for(b)
				
				expected[a] = new Point( muX , muY );
				
			}//if(!null)
		
		}//for(a)
		
		
		return expected;
	}
	
	
	
	
	
	
	
	
	
	
	
	
/**Return the expected location in the image (XY-space) of each color present in the set having type RGB
 * 
 * @param shash the hashtable of an image
 * @param W	the width of the image from which the set was generated 
 * @return the expected location of each of the color in the set
 */
	public static Point[] expectedPointRGBinXY(int[][] shash , int W) {
		
		Point[] expected = new Point[16777256];
		
		for(int a=0;a<16777256;a++) {
			
			
			
			if(shash[a]!=null) {
				
				int[] location = shash[a];
				double muX=0,muY=0;
				
				for(int b=0;b<location.length;b++) {
					double x = location[b]%W;
					double y= (location[b]-x)/W;
					muX+=x/(double)location.length;
					muY+=y/(double)location.length;
				}//for(b)
				
				expected[a] = new Point( muX , muY );
				
			}//for(!null)
			
		}//for(a)
		
		
		return expected;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/**Return the expected color of a set
 * 
 * @param pts the array of point having P( red , green , blue)
 * @return the Point representing the expected color of a set of color
 */
	public static Point expectedPointRGBinRGB(Point[] pts) {
		
		
		
		double muX = 0 , muY = 0 , muZ = 0;
		double counter=0;
		
		for(int a=0;a<pts.length;a++) {
			
			if(pts[a]!=null) {
				
				muX+=pts[a].getX();
				muY+=pts[a].getY();
				muZ+=pts[a].getZ();
				counter++;
				
			}
			
		}//for(a)
		

		return new Point(muX/counter , muY/counter , muZ/counter);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// using sigma^2 = (1/N)*SUM (Xi-Xavg)
	// and return sigma, not sigma square
	

	/**Return the variance position of a set of point having type SetType.XY from sigma^2 = (1/N)*SUM (Xi-Xavg)
	 * 	
	 * @param pts an array of point consisting of P( x , y )
	 * @return the variance position of the set
	 */
	
	public static Point variancePointXYinXY(Point[] pts) {
		
		Point mu = expectedPointXYinXY(pts);
		double counter=0;
		double sigmaX=0, sigmaY=0;
		
		for(int a=0;a<pts.length;a++) {
			
			if(pts[a]!=null) {
				
				counter++;
				sigmaX+=Math.pow( pts[a].getX()-mu.getX() , 2);
				sigmaY+=Math.pow( pts[a].getY()-mu.getY() , 2 );
				
			}
			
		}
		
		return new Point( Math.sqrt(sigmaX/counter) , Math.sqrt(sigmaY/counter) );
	}
	
	
	
	
	
	
	
	
	

	/**Return the variance of the colors of a set of point having type SetType.XY from sigma^2 = (1/N)*SUM (Xi-Xavg)
	 * 	
	 * @param pts an array of point consisting of P( red, green , blue )
	 * @return the variance of the set
	 */
	
	public static Point variancePointXYinRGB(Point[] pts) {
		
		Point mu = expectedPointXYinRGB(pts);			
		double counter = 0;
		double sigmaR=0,sigmaG=0,sigmaB=0;
		
		for(int a=0; a<pts.length;a++) {
			
			if( pts[a] != null ) {
				
				Point c = new Point( new Color( (int)pts[a].getZ() ) );
				counter++;
				sigmaR+=Math.pow( c.getX()-mu.getX() , 2);
				sigmaG+=Math.pow( c.getY()-mu.getY() , 2);
				sigmaB+=Math.pow( c.getZ()-mu.getZ() , 2);
				
			}//if(!null)
			
		}//for(a)
		
		
		return new Point( Math.sqrt(sigmaR/counter) , Math.sqrt(sigmaG/counter) , Math.sqrt(sigmaB/counter) );
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**Return the variance location in the image (XY-space) of each color present in the set having type RGB from sigma^2 = (1/N)*SUM (Xi-Xavg)
	 * 
	 * @param the array of point representing a set
	 * @param W	the width of the image from which the set was generated 
	 * @return the variance of each of the color in the set
	 */
	public static Point[] variancePointRGBinXY(Point[] pts , int W) {
		
		Point[] mu = expectedPointRGBinXY(pts, W);
		Point[] sigma = new Point[mu.length];
		
		for(int a=0;a<pts.length;a++) {
			
			if(pts[a]!=null) {
				
				int[] location = pts[a].getLoc();
				double sigmaX=0 , sigmaY=0;
				
				for(int b=0;b<location.length;b++) {
					
					double x = location[b]%W;
					double y = (location[b]-x)/W;
					
					sigmaX+=Math.pow( (x-mu[a].getX()) , 2 );
					sigmaY+=Math.pow( (y-mu[a].getY()) , 2 );
					
				}//for(b)
				
				sigma[a]=new Point( Math.sqrt(sigmaX/(double)location.length) , Math.sqrt(sigmaY/(double)location.length) );
				
			}//if(!null)
			
			
			
		}//for(a)

		
		return sigma;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**Return the variance of a set of color (type RGB) from sigma^2 = (1/N)*SUM (Xi-Xavg)
	 * 
	 * @param pts the array of point having P( red , green , blue)
	 * @return the Point representing the variance color of a set of color
	 */
	
	public static Point variancePointRGBinRGB(Point[] pts) {
		
		Point mu = expectedPointRGBinRGB(pts);
		double counter=0;
		double sigmaR=0,sigmaG=0,sigmaB=0;
		
		
		for(int a=0;a<pts.length;a++) {
			
			if(pts[a]!=null) {
				
				sigmaR+=Math.pow( pts[a].getX()-mu.getX() , 2);
				sigmaG+=Math.pow( pts[a].getY()-mu.getY() , 2);
				sigmaB+=Math.pow( pts[a].getZ()-mu.getZ() , 2);
				counter++;
				
			}//if(!null)
			
		}//for(a)
		
		
		return new Point( Math.sqrt(sigmaR/counter) , Math.sqrt(sigmaG/counter) , Math.sqrt(sigmaB/counter) );
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}//END class{}