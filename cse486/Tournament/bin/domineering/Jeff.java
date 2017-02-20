package domineering;
import game.*;

import java.util.*;


public class Jeff extends GamePlayer {
	
	protected ScoredMove[] mvStack;
	public final int MAX_DEPTH = 50;
	public int depthLimit;	
	public static final int MAX_SCORE = 1000; // up for debate
	public Jeff(String n, int d) 
	{
		super(n, "Domineering");
		depthLimit = d;
		
	}
	
	public void init()
	{
		mvStack = new ScoredMove[MAX_DEPTH];
		for(int i = 0; i<MAX_DEPTH; i++)
			mvStack[i] = new ScoredMove(0); // move with score of 0 and no position
	}
	public GameMove getMove(GameState brd, String lastMove)
	{
		alphaBeta((DomineeringState)brd, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		return mvStack[0];
	}
	public static int evalBoard(DomineeringState brd)
	{
		return 1;
	}
	protected class ScoredMove extends DomineeringMove
	{
		public double score;
		public ScoredMove(double score)
		{
			super();
		}
		public void set(int r1, int c1, int r2, int c2, double s)
		{
			row1=r1;
			row2=r2;
			col1=c1;
			col2=c2;
			score=s;		
		}
	}
	public boolean terminalValue(DomineeringState brd, ScoredMove mv)
	{
		//essentially just returns of the game is over.
		GameState.Status status = brd.getStatus(); 
		
		boolean isTerminal = true;
		//states 
		//GAME_ON
		//HOME_WIN
		//AWAY_WIN
		if(status == GameState.Status.HOME_WIN)
			mv.set(0,0,0,0,MAX_SCORE);
		else if(status == GameState.Status.AWAY_WIN)
			mv.set(0,0,0,0,-MAX_SCORE);
		else
			isTerminal = false;
		return isTerminal;
	}
	public void alphaBeta(DomineeringState brd, int currDepth, double alpha, double beta)
	{
		int dc, dr;
		if (brd.who == GameState.Who.HOME) 
		{
                        dr = 0;
                        dc = 1;
                } 
		else 
		{
                        dr = 1;
                        dc = 0;
                }


		//decides who to maximize and minimize
		boolean toMaximize = (brd.who == GameState.Who.HOME);
		boolean toMinimize = !toMaximize;
		
		//finds the state of the game
		boolean isTerminal = terminalValue(brd,mvStack[currDepth]);
		
		
		if(isTerminal)
		{
			;
		}
		else if(currDepth == depthLimit)
		{
			//evalBoard doesnt exist yet, not sure about the location either
			mvStack[currDepth].set(0,0,0,0,evalBoard(brd));
		}
		else
		{
			//alpha beta
			ScoredMove tempMv = new ScoredMove(0);//just the score, may mot be correct
			double bestScore = (toMaximize ?
						Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
			ScoredMove bestMove = mvStack[currDepth];
			ScoredMove nextMove = mvStack[currDepth+1];

			bestMove.set(0,0,0,0,bestScore);
			
			GameState.Who currTurn = brd.getWho();

			//randomize the row or column chosen 
			//do that right here
	
			for (int i=0; i<DomineeringState.COLS; i++) 
			{
				for(int j = 0; j<DomineeringState.ROWS; j++)
				{	 
					tempMv.row1 = i; // initialize move 
					tempMv.row2 = i+dr;
					tempMv.col1 = j;
					tempMv.col2 = j+dc;
					if(brd.moveOK(tempMv))
					{	
						brd.makeMove(tempMv); 
						alphaBeta(brd, currDepth+1, alpha, beta);  // Check out move 

						// Undo move 
						//brd.numInCol[c]--a; 
						//int row = brd.numInCol[c]; 
						brd.board[i][j] = DomineeringState.emptySym; 
						brd.board[i+dr][j+dc] = DomineeringState.emptySym;
						brd.numMoves--;
						brd.status = GameState.Status.GAME_ON;
						brd.who = currTurn; 

						// Check out the results, relative to what we've seen before 
						if (toMaximize && nextMove.score > bestMove.score) 
						{	 
							bestMove.set(i,j,i+dr,j+dc, nextMove.score); 
						} 	
						else if (!toMaximize && nextMove.score < bestMove.score) 
						{ 
							bestMove.set(i,j,i+dr,j+dc, nextMove.score); 
						} 

						// Update alpha and beta. Perform pruning, if possible. 

						if (toMinimize) 
						{ 
							beta = Math.min(bestMove.score, beta); 
							if(bestMove.score <= alpha || bestMove.score == -MAX_SCORE)
							{ 
								return;
							}
						} 
						else 
						{	 
							alpha = Math.max(bestMove.score, alpha); 
							if (bestMove.score >= beta || bestMove.score == MAX_SCORE) 
							{ 
								return;
							} 
						}
					}
				}
			}
		}
	}
	public static void main(String [] args)
	{
		GamePlayer jeff = new Jeff("Jeff",1); // the starting depth limit is 1, change this later
		jeff.compete(args, 1);
	}
}
