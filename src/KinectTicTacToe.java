/*
 * Kinect TicTacToe is an application developed by Ted Henkels as part of a 
 * independent study project under the supervision of Marc Sosnick at San 
 * Francisco State University.  The application utilizes the Processing.org 
 * library and the SimpleOpenNI library in order to communicate with a Kinect, 
 * a gaming peripheral built by Microsoft for the XBox 360.  This application
 * will allow the user to play a simple tic tac toe game with hand gestures as
 * opposed to a typical controller or keyboard. In order to properly run this 
 * application one needs to first download the libraries found at 
 * http://processing.org/download/ and https://code.google.com/p/simple-openni/
 */

import processing.core.*;
import SimpleOpenNI.*;


public class KinectTicTacToe extends PApplet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	
	public UI userInterface; 
	
	PVector cursor;
	
	public SimpleOpenNI kinect;
	
	public static void main(String args[]){
		 PApplet.main(new String[] { "--present", "KinectTicTacToe" });
	}
	
	public void setup(){
		size(WIDTH, HEIGHT);
		userInterface = new UI(this);
		background(0);
		kinect = new SimpleOpenNI(this,SimpleOpenNI.RUN_MODE_MULTI_THREADED);
		cursor = new PVector();
	}
	
	public void draw(){
		//if(kinect.init()){
			
		//} else {
			cursor.x = mouseX;
			cursor.y = mouseY;
		//}
		userInterface.sketch(cursor);
	}
	
}
