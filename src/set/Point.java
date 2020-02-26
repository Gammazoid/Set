package set;

import java.awt.Color;


public class Point {
	
	private double x,y,z,w;
	private int[] location;
	
	public Point(double x, double y) {
		
		this.x=x;
		this.y=y;
		this.z=0;
	}
	
	public Point(double x, double y, double z) {
		
		this.x=x;
		this.y=y;
		this.z=z;
		
	}
	
	public Point(double x, double y, double z, int[] location) {
		
		this.x=x;
		this.y=y;
		this.z=z;
		this.location=location;
		
	}
	
	public Point(double x, double y, double z, double w) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.w=w;
	}
	
	public Point(Color clr) {
		
		this.x=clr.getRed();
		this.y=clr.getGreen();
		this.z=clr.getBlue();
		
	}
	
	
	public Point(Color clr, double weight) {
		
		this.x=clr.getRed();
		this.y=clr.getGreen();
		this.z=clr.getBlue();
		this.w=weight;
		
	}
	
	public Point(Color clr , int[] locationSet) {
		
		this.x=clr.getRed();
		this.y=clr.getGreen();
		this.z=clr.getBlue();
		this.location=locationSet;
	}
	
	
	//SETTERS*************************************SETTERS*******************************
	public void setX(double x){
		this.x=x;
	}
	public void setY(double y){
		this.y=y;
	}
	public void setZ(double z) {
		this.z=z;
	}
	public void setW(double w) {
		this.w=w;
	}
	public void setLoc(int[] location) {
		this.location=location;
	}
	
	//GETTERS***************************GETTERS********************************
	public double getX(){
		return this.x;
	}
	public double getY(){
		return this.y;
	}
	public double getZ() {
		return this.z;
	}
	public double getW() {
		return this.w;
	}
	public int[] getLoc() {
		return this.location;
	}
	
	//OTHERS************************************************************
	
	
	public static double dist(Point A, Point B) {
		return Math.sqrt(Math.pow((A.getX()-B.getX()),2)+Math.pow((A.getY()-B.getY()), 2)+Math.pow((A.getZ()-B.getZ()),2));
	}
		
		
		
		
		
		
	//takes pointA as the zero and find the angle B makes with the x-axis	
	public static double angle(Point A, Point B) {
		
		Point pt = new Point( B.getX()-A.getX() , B.getY()-A.getY() );
		double theta = Math.atan( pt.getY()/pt.getX() );
		
		return theta*180/Math.PI;
	}
		
		
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public double length() {
		return Math.sqrt(this.x*this.x+this.y+this.y);
	}
	
	
	
	
	
	
	
	
	public String show(){
		return ""+this.x+" "+this.y+" "+this.z+"";	
	}

}
