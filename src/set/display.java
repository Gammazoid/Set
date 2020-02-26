package set;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class display extends JFrame{
	
	static int x=50, y=50;
	
	// shows a buffered image
	public display(BufferedImage bimg) {
		
		this.setTitle("fenetre 1");
		this.setLocation(x,y);
		int wih=0, heh=0;
		if(bimg.getWidth()<250) {wih=270;}
		else {wih=bimg.getWidth()+20;}
		
		if(bimg.getHeight()<150) {heh=220;}
		else {heh=bimg.getHeight()+80;}
		
		
		this.setSize(wih, heh);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setAlwaysOnTop(true);

		
		//x=(x+110)%1650;
	//	if(x%1050==0) {y=y+150;}
		
		JPanel pan=new JPanel() {	public void paintComponent(Graphics g) {g.drawImage(bimg, 0,0, this);}};
		
		this.setContentPane(pan);
		this.setVisible(true);
		
	}
	
	
	
	
	
	
	
	
	
	









	
	
	
	
	
	
	
	
	public display(BufferedImage bimg, String str) {
		
		this.setTitle("fenetre 1");
		this.setLocation(x,y);
		int wih=0, heh=0;
		if(bimg.getWidth()<250) {wih=270;}
		else {wih=bimg.getWidth()+20;}
		
		if(bimg.getHeight()<150) {heh=220;}
		else {heh=bimg.getHeight()+80;}
		
		
		this.setSize(wih, heh);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setAlwaysOnTop(true);

		
		//x=(x+110)%1650;
	//	if(x%1050==0) {y=y+150;}
		
		JPanel pan=new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(bimg, 0,0, this);
				g.setColor(Color.RED);
				g.drawString(str, 0, 20);
			}};
		
		this.setContentPane(pan);
		this.setVisible(true);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	static int xGraph=50, yGraph=300;
	//shows a  graph of the array of integer, where the index is the x-value and array[index] is the y-value
	public display(int[] array) {
		
		
		int maxValue=0;
		
		
		
		for(int q=0;q<array.length;q++) {
			if(array[q]>maxValue) {maxValue=array[q];}
		}
		
		
		int height=500;
		int[] scaledArray = new int[array.length];
		
		if(maxValue>height) {
			scaledArray = MyMath.rescaleTo(array, 400);
		}
		else {
			scaledArray = array;
		}

		this.setTitle("graph of t_analysis");
		this.setLocation(xGraph, yGraph);
		this.setSize(array.length+20, 650);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setAlwaysOnTop(true);
		
		BufferedImage bimg=SetOperation.makeWhite(array.length, height+1);
		for(int i=0;i<array.length;i++) {
			bimg.setRGB(i, height-scaledArray[i], -16600000);
		}
		
		JPanel pan=new JPanel() {public void paintComponent(Graphics g) {g.drawImage(bimg, 0 , 0, this);}};
		
		this.setContentPane(pan);
		this.setVisible(true);
		
		
		xGraph+=270;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	int xPt=50, yPt=50;
	// shows the points in the array
	public display(Point[] arrPt) {
		
		this.setTitle("edge");
		this.setLocation(xPt,yPt);;
		this.setSize(300, 300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		double minX=100, minY=1000, maxY=0, maxX=0;
		for(Point pt: arrPt) {
			if(pt.getX()<minX) {minX=pt.getX();}
			
			if(pt.getX()>maxX) {maxX=pt.getX();}
			
			if(pt.getY()<minY) {minY=pt.getY();}
			
			if(pt.getY()>maxX) {maxY=pt.getY();}
		}
		
		
		int weh=(int)(maxX-minX);
		int heh=(int)(maxY-minY);
		
		BufferedImage bimg=SetOperation.makeWhite(weh, heh);
		
		
		
		JPanel pan=new JPanel() {
			public void paintComponent(Graphics g) {
				for(int h=0;h<arrPt.length;h++) {
					if(arrPt[h]!=null){g.drawRect((int)arrPt[h].getX(), (int)arrPt[h].getY(), 1, 1);
					}
				}
			}
		};
		
		this.setContentPane(pan);
		this.setVisible(true);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
