public class hc
{
	public static void main(String args[])
	{
		hc queens = new hc();
		char[][] board = queens.generateRandomBoard();
		queens.printBoard(board);
		System.out.println(queens.collisions(board));
		for(int i = 0; i<1000000; i++)
		{
			board = queens.findSteepestAscent(board);
		}
		queens.printBoard(board);
		System.out.println(queens.collisions(board));
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
		char[][]  newBoard;
		for(int i = 0; i<8; i++)
			for(int j = 0; j<8; j++)
			{
				if(board[i][j] == 'Q')
				{	
					newBoard = generateRandomBoard();
					if(collisions(newBoard) < collisions(board))
					{		
						board = newBoard;
					}
				}
			}
		return board;
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
