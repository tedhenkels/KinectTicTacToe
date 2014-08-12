import processing.core.*;


public class UI {
	
	public enum GameInterfaces{
		INTRODUCTION, MAINGAME, WINNER, CATSGAME 
	}
	
	private static final int DURATION = 2500;
	
	private int width;
	private int height;
	
	PApplet parent;
	PVector cursor;
	GameLogic gameRules;
	GameInterfaces gameIterator;
	int timer;
	
	PImage blackhand;
	PImage catsGame;
	
	// Game control boolean flags used for user interface 
	boolean introFlag 	= false;
	boolean exitFlag  	= false;
	boolean resetFlag 	= false;
	boolean NW_flag   	= false;
	boolean N_flag 		= false;
	boolean NE_flag		= false;
	boolean W_flag 		= false;
	boolean C_flag 		= false;
	boolean E_flag		= false;
	boolean SW_flag		= false;
	boolean S_flag 		= false;
	boolean SE_flag		= false;
	
	// The dimensions of a each region represented by the boundary lines 
	int[] NW_region	= { 95,	  15,  245,  165};
	int[] N_region	= {245,   15,  395,  165};   
	int[] NE_region	= {395,   15,  545,  165};
	int[] W_region	= { 95,  165,  245,  315};
	int[] C_region	= {245,  165,  395,  315};
	int[] E_region	= {395,  165,  545,  315};
	int[] SW_region = { 95,  315,  245,  465};
	int[] S_region	= {245,  315,  395,  465};
	int[] SE_region = {395,  315,  545,  465};
	
	
	public UI(PApplet p){
		parent = p;
		gameIterator = GameInterfaces.INTRODUCTION;
		gameRules = new GameLogic();
		blackhand = parent.loadImage("./Images/blackHand.gif");
		catsGame = parent.loadImage("./Images/happycat.gif");
		height = parent.getHeight();
		width = parent.getWidth();
	}
	
	public void sketch(PVector cursor){
		parent.background(0);
		// Introduction interface 
		if(gameIterator == GameInterfaces.INTRODUCTION){
			// Introduction prompt
			parent.strokeWeight(3);
			parent.stroke(255);
			parent.fill(100);
			parent.rectMode(PApplet.CENTER);
			parent.rect(320, 240, 600, 400);
			parent.textSize(24);
			parent.fill(255);
			parent.text("Welcome to Tic Tac Toe 3000! \n \nUsing the magic of the Kinect 2 players will be able to play a simple tic tac toe game", 320, 240, 560, 360);
			
			// If the user places the cursor in the "continue" action button for a long
			//	enough time that button will be activated sending the user to the next
			//	part of the game.  When the cursor is inside the button the dimensions 
			//	of the button will be reduced by 10% giving the illusion of it being
			//	pressed
			if(cursor.x > width/2-100 && cursor.x < width/2+100
					 && cursor.y > height/2+75 && cursor.y < height/2+125) {
				// The user has placed the cursor inside the action area for the first
				//	time
				if(!introFlag){
					timer = parent.millis();
					introFlag = true;
				}
				parent.fill(150);
				parent.rect(width/2,height/2+100,180,45);
				parent.textSize(21.6f);
				parent.fill(255);
				parent.text("Continue?", width/2, height/2+110, 125, 50);
				parent.noFill();
				parent.arc(cursor.x,cursor.y,60f,60f,0f,(float)((parent.millis() - timer)*Math.PI/(DURATION/2)));
				if(parent.millis() - timer >= DURATION){
					gameIterator = GameInterfaces.MAINGAME;
					introFlag = false;
				}
			} else {
				// The user has the cursor outside the "Continue" button and therefore the 
				//	dimensions are 100%
				introFlag = false;
				timer = 0; 
				parent.fill(150);
				parent.rect(width/2,height/2+100,200,50);
				parent.fill(255);
				parent.text("Continue?", width/2, height/2+110, 125, 50);
			}
			parent.image(blackhand, (float)(cursor.x - 17.5), (float)(cursor.y - 27.5));
		}
		// Main game 
		if(gameIterator == GameInterfaces.MAINGAME){
			if(gameRules.getPlayer() == 'x'){
				highlightRegion(cursor);
				xCursor(cursor);
			} else {
				highlightRegion(cursor);
				oCursor(cursor);
			}
			buildBoard();
			drawXsAndOs();
		}
		
		if(gameIterator == GameInterfaces.WINNER){
			parent.rectMode(PApplet.CENTER);
			parent.fill(150);
			parent.rect(320, 240, 400, 300);
			parent.fill(255);
			parent.textSize(24);
			parent.text("Congratulations player ", 195, 140);
			if(gameRules.getPlayer() == 'x')
				drawBigX(245,145);
			else if(gameRules.getPlayer() == 'o')
				drawBigO(245,145);
			else 
				System.out.println("Not a player option");
			parent.text("You truly are a champion!", 175, 315);
			// 
			if(cursor.x > 130 && cursor.x < 310 && cursor.y > 330 && cursor.y < 380){
				if(!resetFlag){
					timer = parent.millis();
					resetFlag = true;
				}
				parent.fill(150);
				parent.rect(220,355,162,45);
				parent.textSize(21.6f);
				parent.fill(255);
				parent.text("Play again?", 153, 363);
				parent.noFill();
				parent.arc(cursor.x, cursor.y, 80f, 80f, 0f, (float)((parent.millis() - timer)*Math.PI/(DURATION/2)));
				if((parent.millis() - timer) >= DURATION){
					resetFlag = false;
					gameRules = new GameLogic();
					gameIterator = GameInterfaces.MAINGAME;
				}
			} else {
				resetFlag = false;
				parent.fill(150);
				parent.rect(220, 355, 180, 50);
				parent.fill(255);
				parent.text("Play again?",153,363);
			}
			if(cursor.x > 330 && cursor.x < 510 && cursor.y > 330 && cursor.y < 380){
				if(!exitFlag){
					timer = parent.millis();
					exitFlag = true;
				}
				parent.fill(150);
				parent.rect(420, 355, 162, 45);
				parent.fill(255);
				parent.textSize(21.6f);
				parent.text("Exit?", 385, 363);
				parent.noFill();
				parent.arc(cursor.x, cursor.y, 80f, 80f, 0f, (float)((parent.millis() - timer)*Math.PI/(DURATION/2)));
				if((parent.millis() - timer) >= DURATION){
					exitFlag = false;
					parent.exit();
				}
			} else {
				exitFlag = false;
				parent.fill(150);
				parent.rect(420, 355, 180, 50);
				parent.fill(255);
				parent.textSize(24f);
				parent.text("Exit?", 385, 363);
			}
			parent.image(blackhand, (float)(cursor.x - 17.5), (float)(cursor.y - 27.5));
		}
		
		if(gameIterator == GameInterfaces.CATSGAME){
			parent.rectMode(PApplet.CENTER);
			parent.fill(150);
			parent.rect(320, 240, 400, 300);
			parent.image(catsGame, 195, 90);
			parent.fill(255);
			parent.textSize(24f);
			parent.text("Cats game", 195, 140);
			parent.text("You tow are an equal match", 175, 315);
			// The user selects an option
			if(cursor.x > 130 && cursor.x < 310 && cursor.y > 330 && cursor.y < 380){
				if(!resetFlag){
					timer = parent.millis();
					resetFlag = true;
				}
				parent.fill(150);
				parent.rect(220,355,162,45);
				parent.textSize(21.6f);
				parent.fill(255);
				parent.text("Play again?",153,363);
				parent.noFill();
				parent.arc(cursor.x, cursor.y, 80f, 80f, 0f, (float)((parent.millis() - timer)*Math.PI/(DURATION/2)));
				if((parent.millis() - timer) >= DURATION){
					resetFlag = false;
					gameRules = new GameLogic();
					gameIterator = GameInterfaces.MAINGAME;
				}
			} else {
				resetFlag = false;
				parent.fill(150);
				parent.rect(220, 355, 180, 50);
				parent.fill(255);
				parent.text("Play again?", 153, 363);
			}
			if(cursor.x > 330 && cursor.x < 510 && cursor.y > 330 && cursor.y < 380){
				if(!exitFlag){
					timer = parent.millis();
					exitFlag = true;
				}
				parent.fill(150);
				parent.rect(420,355,162,45);
				parent.fill(255);
				parent.textSize(21.6f);
				parent.text("Exit?", 385, 363);
				parent.noFill();
				parent.arc(cursor.x, cursor.y, 80f, 80f, 0f, (float)((parent.millis() - timer)*Math.PI/(DURATION/2)));
				if((parent.millis() - timer) >= DURATION){
					exitFlag  = false;
					parent.exit();
				}
			} else {
				exitFlag = false;
				parent.fill(150);
				parent.rect(420, 355, 180, 50);
				parent.fill(255);
				parent.textSize(24f);
				parent.text("Exit?", 385, 363);
			}
			parent.image(blackhand, (float) (cursor.x-17.5), (float) (cursor.y-27.5));
		}
	}
	
	private void buildBoard(){
		parent.strokeWeight(3);
		parent.stroke(255);
		parent.line(245,15,245,465);
		parent.line(395,15,395,465);
		parent.line(95, 165, 545, 165);
		parent.line(95, 315, 545, 315);
	}
	
	private void highlightRegion(PVector cursor){
		parent.rectMode(PApplet.CORNER);
		parent.fill(150);
		// Cursor is in the NW region of the board
		if(isCursorWithinRegion(cursor,NW_region)){
			if(!NW_flag){
				NW_flag = true;
				timer = parent.millis();
			}
			N_flag 	= false;
			NE_flag	= false;
			W_flag	= false;
			C_flag 	= false;
			E_flag 	= false;
			SW_flag	= false;
			S_flag 	= false;
			SE_flag	= false;
			
			if(gameRules.getSelection(0,0) < 0){
				parent.rect(95,15,150,150);
				parent.noFill();
				parent.arc(cursor.x, cursor.y, 80f, 80f, 0f, (float)((parent.millis() - timer)*Math.PI/(DURATION/2)));
			}
			if((parent.millis() - timer) >= DURATION && NW_flag){
				gameRules.makeSelection(0, 0);
				if(gameRules.isWinner())
					gameIterator = GameInterfaces.WINNER;
				else if(gameRules.isCatsGame())
					gameIterator = GameInterfaces.CATSGAME;
				NW_flag = false;
			}
		}
		// Cursor in the N region 
		if(isCursorWithinRegion(cursor,N_region)){
			if(!N_flag){
				N_flag = true;
				timer = parent.millis();
			}
			NW_flag	= false;
			NE_flag	= false;
			W_flag	= false;
			C_flag	= false;
			E_flag	= false;
			SW_flag	= false;
			S_flag	= false;
			SE_flag	= false;
			
			if(gameRules.getSelection(1, 0) < 0){
				parent.rect(245, 15, 150, 150);
				parent.noFill();
				parent.arc(cursor.x, cursor.y, 80f, 80f, 0f, (float)((parent.millis() - timer)*Math.PI/(DURATION/2)));
			}
			if((parent.millis() - timer) >= DURATION && N_flag){
				gameRules.makeSelection(1, 0);
				if(gameRules.isWinner())
					gameIterator = GameInterfaces.WINNER;
				if(gameRules.isCatsGame())
					gameIterator = GameInterfaces.CATSGAME;
				N_flag = false;
			}
		}
		// Cursor in the NE region
		if(isCursorWithinRegion(cursor, NE_region)){
			if(!NE_flag){
				NE_flag	= true;
				timer = parent.millis();
			}
			
			NW_flag	= false;
			N_flag	= false;
			W_flag 	= false;
			C_flag 	= false;
			E_flag	= false;
			SW_flag	= false;
			S_flag	= false;
			SE_flag	= false;
			
			if(gameRules.getSelection(2,0) < 0){
				parent.rect(395, 15, 150, 150);
				parent.noFill();
				parent.arc(cursor.x, cursor.y, 80f, 80f, 0f, (float)((parent.millis() - timer)*Math.PI/(DURATION/2)));
			}
			if((parent.millis() - timer) >= DURATION && NE_flag){
				gameRules.makeSelection(2, 0);
				if(gameRules.isWinner())
					gameIterator = GameInterfaces.WINNER;
				if(gameRules.isCatsGame())
					gameIterator = GameInterfaces.CATSGAME;
				NE_flag = false;
			}
		}
		// Cursor in the W region
		if(isCursorWithinRegion(cursor,W_region)){
			if(!W_flag){
				W_flag = true;
				timer = parent.millis();
			}
			NW_flag	= false;
			N_flag	= false;
			NE_flag	= false;
			C_flag	= false;
			E_flag	= false;
			SW_flag	= false;
			S_flag	= false;
			SE_flag	= false;
			
			if(gameRules.getSelection(0, 1) < 0){
				parent.rect(95, 165, 150, 150);
				parent.noFill();
				parent.arc(cursor.x, cursor.y, 80f, 80f, 0f, (float)((parent.millis() - timer)*Math.PI/(DURATION/2)));
			}
			if((parent.millis() - timer) >= DURATION && W_flag){
				gameRules.makeSelection(0, 1);
				if(gameRules.isWinner())
					gameIterator = GameInterfaces.WINNER;
				if(gameRules.isCatsGame())
					gameIterator = GameInterfaces.CATSGAME;
				W_flag = false;
			}
		}
		// Cursor in the C region
		if(isCursorWithinRegion(cursor, C_region)){
			if(!C_flag){
				C_flag = true;
				timer = parent.millis();
			}
			NW_flag	= false;
			N_flag 	= false;
			NE_flag	= false;
			W_flag	= false;
			E_flag	= false;
			SW_flag	= false;
			S_flag	= false;
			SW_flag	= false;
			
			if(gameRules.getSelection(1, 1) < 0){
				parent.rect(245,165,150,150);
				parent.noFill();
				parent.arc(cursor.x, cursor.y, 80f, 80f, 0f, (float)((parent.millis() - timer)*Math.PI/(DURATION/2)));
			}
			if((parent.millis() - timer) >= DURATION && C_flag){
				gameRules.makeSelection(1, 1);
				if(gameRules.isWinner())
					gameIterator = GameInterfaces.WINNER;
				if(gameRules.isCatsGame())
					gameIterator = GameInterfaces.CATSGAME;
				C_flag = false;
			}
		}
		// Cursor in the E region
		if(isCursorWithinRegion(cursor, E_region)){
			if(!E_flag){
				E_flag = true;
				timer = parent.millis();
			}
			NW_flag	= false;
			N_flag	= false;
			NE_flag	= false;
			W_flag	= false;
			C_flag 	= false;
			SW_flag	= false;
			S_flag	= false;
			SE_flag	= false;
			
			if(gameRules.getSelection(2, 1) < 0){
				parent.rect(395, 165, 150, 150);
				parent.noFill();
				parent.arc(cursor.x, cursor.y, 80f, 80f, 0f, (float)((parent.millis() - timer)*Math.PI/(DURATION/2)));
			}
			if((parent.millis() - timer) >= DURATION && E_flag){
				gameRules.makeSelection(2, 1);
				if(gameRules.isWinner())
					gameIterator = GameInterfaces.WINNER;
				if(gameRules.isCatsGame())
					gameIterator = GameInterfaces.CATSGAME;
				E_flag = false;
			}
		}
		// Cursor is in the SW region
		if(isCursorWithinRegion(cursor, SW_region)){
			if(!SW_flag){
				SW_flag = true;
				timer = parent.millis();
			}
			NW_flag	= false;
			N_flag	= false;
			NE_flag	= false;
			W_flag	= false;
			C_flag	= false;
			E_flag	= false;
			S_flag	= false;
			SE_flag	= false;
			
			if(gameRules.getSelection(0, 2) < 0){
				parent.rect(95, 315, 150, 150);
				parent.noFill();
				parent.arc(cursor.x, cursor.y, 80f, 80f, 0f, (float)((parent.millis() - timer)*Math.PI/(DURATION/2)));
			}
			if((parent.millis() - timer) >= DURATION && SW_flag){
				gameRules.makeSelection(0, 2);
				if(gameRules.isWinner())
					gameIterator = GameInterfaces.WINNER;
				if(gameRules.isCatsGame())
					gameIterator = GameInterfaces.CATSGAME;
				SW_flag = false;
			}
		}
		// Cursor in the S region
		if(isCursorWithinRegion(cursor, S_region)){
			if(!S_flag){
				S_flag = true;
				timer = parent.millis();
			}
			NW_flag	= false;
			N_flag	= false;
			NE_flag	= false;
			W_flag	= false;
			C_flag	= false;
			E_flag	= false;
			SW_flag	= false;
			SE_flag	= false;
			
			if(gameRules.getSelection(1, 2) < 0){
				parent.rect(245, 315, 150, 150);
				parent.noFill();
				parent.arc(cursor.x, cursor.y, 80f, 80f, 0f, (float)((parent.millis() - timer)*Math.PI/(DURATION/2)));
			}
			if((parent.millis() - timer) >= DURATION && S_flag){
				gameRules.makeSelection(1, 2);
				if(gameRules.isWinner())
					gameIterator = GameInterfaces.WINNER;
				if(gameRules.isCatsGame())
					gameIterator = GameInterfaces.CATSGAME;
				S_flag = false;
			}
		}
		// Cursor is in SE region
		if(isCursorWithinRegion(cursor, SE_region)){
			if(!SE_flag){
				SE_flag = true;
				timer = parent.millis();
			}
			NW_flag	= false;
			N_flag	= false;
			NE_flag	= false;
			W_flag	= false;
			C_flag	= false;
			E_flag	= false;
			SW_flag	= false;
			S_flag	= false;
			
			if(gameRules.getSelection(2,2) < 0){
				parent.rect(395,315,150,150);
				parent.noFill();
				parent.arc(cursor.x, cursor.y, 80f, 80f, 0f, (float)((parent.millis() - timer)*Math.PI/(DURATION/2)));
			}
			if((parent.millis() - timer) >= DURATION && SE_flag){
				gameRules.makeSelection(2, 2);
				if(gameRules.isWinner())
					gameIterator = GameInterfaces.WINNER;
				if(gameRules.isCatsGame())
					gameIterator = GameInterfaces.CATSGAME;
				SE_flag = false;
			}
		}
	}
	
	private void drawXsAndOs(){
		int xCoord = 95;
		int yCoord = 15;
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(gameRules.getSelection(j, i) == 1)
					drawBigX(xCoord,yCoord);
				if(gameRules.getSelection(j, i) == 2)
					drawBigO(xCoord,yCoord);
				xCoord += 150;
			}
			xCoord = 95;
			yCoord += 150;
		}
	}
	
	private void drawBigX(int xCoord, int yCoord){
		parent.line(xCoord+10, yCoord+10, xCoord+140, yCoord+140);
		parent.line(xCoord+140, yCoord+10, xCoord+10, yCoord+140);
	}
	
	private void drawBigO(int xCoord, int yCoord){
		parent.noFill();
		parent.ellipse(xCoord+75, yCoord+75, 140, 140);
	}
	
	private void xCursor(PVector cursor){
		parent.line(cursor.x-25, cursor.y-25, cursor.x+25, cursor.y+25);
		parent.line(cursor.x+25, cursor.y-25, cursor.x-25, cursor.y+25);
	}
	
	private void oCursor(PVector cursor){
		parent.noFill();
		parent.ellipse(cursor.x, cursor.y, 50, 50);
	}
	
	private boolean isCursorWithinRegion(PVector cursor, int[] region){
		if(cursor.x > region[0] && cursor.y > region[1] && cursor.x < region[2] && cursor.y < region[3])
			return true;
		return false;
	}
}
