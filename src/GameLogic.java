
public class GameLogic {
	
	int[][] ticTacToeBoard;
	
	char player;
	
	public GameLogic(){
		
		ticTacToeBoard = new int[3][3];
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				ticTacToeBoard[i][j] = -1;
			}
		}
		
		if((int)((Math.random() * 2)) == 2){
			player = 'x';
		} else {
			player = 'o';
		}
		System.out.println("Game has started and player " + player + " is first to play");
	}
	
	public void makeSelection(int x_pntr, int y_pntr){
		if(player == 'x' && ticTacToeBoard[x_pntr][y_pntr] < 0){
			ticTacToeBoard[x_pntr][y_pntr] = 1;
		} else if(ticTacToeBoard[x_pntr][y_pntr] < 0){
			ticTacToeBoard[x_pntr][y_pntr] = 2;
		}
	}
	
	public int getSelection(int x, int y){
		return ticTacToeBoard[x][y];
	}
	
	public char getPlayer(){
		return player;
	}
	
	public boolean isWinner(){
		// Check the horizontal rows
		for(int i = 0; i < 3; i++){
			if(ticTacToeBoard[0][i] == ticTacToeBoard[1][i] && 
					ticTacToeBoard[1][i] == ticTacToeBoard[2][i] &&
					ticTacToeBoard[0][i] > 0 &&
					ticTacToeBoard[1][i] > 0 &&
					ticTacToeBoard[2][i] > 0){
				System.out.println("Player " + player + " is the winner! Congratulations"); 
				return true;
			}
		}
		// Check the vertical rows
		for(int i = 0; i < 3; i++){
			if(ticTacToeBoard[i][0] == ticTacToeBoard[i][1] &&
					ticTacToeBoard[i][1] == ticTacToeBoard[i][2] &&
					ticTacToeBoard[i][0] > 0 &&
					ticTacToeBoard[i][1] > 0 &&
					ticTacToeBoard[i][2] > 0){
				System.out.println("Player " + player + " is the winner! Congratulations"); 
				return true;
			}
		}
		// Check the diagonals
		if(ticTacToeBoard[0][0] == ticTacToeBoard[1][1] &&
                ticTacToeBoard[1][1] == ticTacToeBoard[2][2] &&
                ticTacToeBoard[0][0] > 0 &&
                ticTacToeBoard[1][1] > 0 &&
                ticTacToeBoard[2][2] > 0){
			System.out.println("Player " + player + " is the winner! Congratulations!");
			return true;
		}
		if(ticTacToeBoard[0][2] == ticTacToeBoard[1][1] &&
                ticTacToeBoard[1][1] == ticTacToeBoard[2][0] &&
                ticTacToeBoard[0][2] > 0 &&
                ticTacToeBoard[1][1] > 0 &&
                ticTacToeBoard[2][0] > 0){
			System.out.println("Player " + player + " is the winner! Congratulations!");
			return true;
		}
		
		// Switch player
				if(player == 'x')
					player = 'o';
				else 
					player = 'x';
		
		return false;
	}
	
	public boolean isCatsGame(){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(ticTacToeBoard[i][j] < 0)
					return false;
			}
		}
		System.out.println("Cats game");
		return true;
	}
}
