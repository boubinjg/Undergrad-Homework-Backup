public class hcl
{
	public static void main(String args[])
	{
		hcl queens = new hcl();
		
		for(int i = 0; i<10000; i++)
		{	
			char[][] board = queens.generateRandomBoard();
			int col = queens.collisions(board);
			while(true)
			{
				board = queens.findSteepestAscent(board);
				if(queens.collisions(board) == col)
					break;
				else
				{	
					board = queens.findSteepestAscent(board);
					col = queens.collisions(board);
				}
			}
			if(col == 0)
			{	
				queens.printBoard(board);
				System.out.println();
			}
		}
		
		//queens.printBoard(board);
		//System.out.println(queens.collisions(board));
		
	}
	public char[][] generateRandomBoard()
	{
		char[][] c = new char[8][8];
		for(int i = 0; i<8; i++)
		{
			for(int j = 0; j<8; j++)
				c[i][j] = 'X';
			c[i][(int)(Math.random()*7)] = 'Q';
		}
		return c;
	}
	public void printBoard(char[][] c)
	{
		for(int k = 0; k<8; k++)
		{
			for(int l = 0; l<8; l++)
			{
				System.out.print(c[k][l] + " ");
			}
		        System.out.println();
		}
	}
	public char[][] findSteepestAscent(char[][] board)
	{
		char[][] newBoard = board, compareBoard; 
		for(int i = 0; i<8; i++)
			for(int j = 0; j<8; j++)
			{
				if(board[i][j] == 'Q')
				{	
					compareBoard = findSteepest(board, i, j);
					if(collisions(compareBoard) < collisions(newBoard))
					{		
						newBoard = compareBoard;
					}
				}
			}
		return newBoard;
	}
	public char[][] findSteepest(char[][] board, int r, int c)
	{
		char[][] ret = board;
		int cols = collisions(board);
		for(int i = -1; i<2; i++)
		{
			for(int j = -1; j<2; j++)
			{	
				if(r+i > -1 && r+i < 8 && c+j > -1 && c+j < 8)
				{	
					board[r][c] = 'X';
					board[r+i][c+j] = 'Q';
					if(collisions(board) < cols)
						return board;
					else
					{
						board[r+i][c+j] = 'X';
						board[r][c] = 'Q'; 
					}
				}	
			}
		}
		return ret;
	}
	public int collisions(char[][] board)
	{
		int [][] queens = new int[8][2];
		int k = 0, collisions =0;
		for(int i= 0; i<8; i++)
		{	
			for(int j = 0; j<8; j++)
			{
				if(board[i][j] == 'Q')
				{
					queens[k][0] = i;
					queens[k][1] = j;	
					k++;
				}			
				
			}
		}
		int r = 0, c = 0;
		for(int l = 0; l<8; l++)
		{
			r = queens[l][0];
			c = queens[l][1];
			for(int m = 0; m<8; m++)
			{
				if(l != m)
				{
					if(queens[m][0] == r || queens[m][1] == c)
						collisions++;
					else if(r - c == queens[m][0] - queens[m][1])	
					{
						collisions++;
					}
					else if(r + c == queens[m][0] + queens[m][1])
						collisions++;
							
				}
			}
		}
		//System.out.println(collisions);
		return collisions/2;
	}	

}
