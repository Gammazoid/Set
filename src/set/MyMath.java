package set;

import java.awt.Color;

public class MyMath{
	
	
	

	
	
	
	
	
	
	//find the expected XY location of a set of point in XY
	
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
	
	
	
	
	
	
	
	

	
	
	// find the expected value of the color given a set of points in XY
		
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// find the expected location of any one color in XY
	
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
// Given a set of point in RGB-cube, find the expected value of the set
	
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