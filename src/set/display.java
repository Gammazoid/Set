package set;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class display extends JFrame{

	
	// shows a buffered image
	public display(BufferedImage bimg) {
		
		this.setTitle("Image display");
		this.setLocation(100,100);
		int wih=0, heh=0;
		if(bimg.getWidth()<250) {wih=270;}
		else {wih=bimg.getWidth()+20;}
		
		if(bimg.getHeight()<150) {heh=220;}
		else {heh=bimg.getHeight()+80;}
		
		
		this.setSize(wih, heh);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setAlwaysOnTop(true);

		

		
		JPanel pan=new JPanel() {	public void paintComponent(Graphics g) {g.drawImage(bimg, 0,0, this);}};
		
		this.setContentPane(pan);
		this.setVisible(true);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
