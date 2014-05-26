package s6.prog6.obichouvine.models;

import s6.prog6.obichouvine.models.Block.BlockState;
import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.Pawn.TypeSuedois;

public class GameState {
	
	public GameState(){
		
		
	}
	
	
	
	public Board Sauver(Board b)
	{
		Block[][] blocks = new Block[b.GetxBoard()][b.GetyBoard()];
		Board board = new Board(b.GetxBoard(),b.GetyBoard(),b.GetParameter());
		
		board.SetBlocks(b.getBlock());
		/*
		for (int i = 0; i < b.GetxBoard(); i++)
		{
			for (int j = 0; j < b.GetyBoard(); j++)
			{		
				BlockState state = b.board[i][j].getState();
				Pawn p =  b.board[i][j].getPawn();
				Parameter param = b.GetParameter();

				blocks[i][j].setState(state); 
				blocks[i][j].setPawn(p);
			}
		}
		*/
		return board;
	}
	
	

}
