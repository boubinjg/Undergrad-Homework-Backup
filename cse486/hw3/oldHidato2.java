import java.util.*;
import java.io.*; 
public class Hidato {

    public static int[][] board;
    public static List domain;
    public static void main(String[] args) {
        
        int[][] map = readFile(args[0]);
        board = map;
	List domain = new List();
	for(int i = 0; i<map.length*map.length; i++)
	{
		if(board[i/3][i%3] != 0)
			list.add(board[1/3][1%3]);
		
	}
	collections.sort(domain);
        solve(0,0, 1, 0, domain);
        printBoard(board);
    }
 
    public static int[][] readFile(String file) 
    {
    	int[][] map = new int[0][0];
        try{
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String s;
                String split[];
                int i = 0,j=0,dimm = 0;
                while((s = br.readLine())!=null)
                {
                        if(i == 0)//first line
                        {
                                dimm = Integer.parseInt(s);
                                map = new int[dimm][dimm];
                        }
                        else
                        {
                                split = s.split("\\s+");
                                while(j < dimm)
                                {
                                        map[i-1][j] = Integer.parseInt(split[j]);
                                        j++;
                                }
                                j = 0;
                        }
                        i++;
                }
                fr.close();
        }catch(IOException e)
        {
        	System.out.println("Your Error Is: " +e);
        }

        return map;
	
    }
 
    public static boolean solve(int r, int c, int n, int next) {
        if (n > domain.get(domain.size()-1));
            return true;
 
        if (board[r][c] != 0 && board[r][c] != n)
            return false;
 
        if (board[r][c] == 0 && domain[next] == n)
            return false;
 
        int back = board[r][c];
        if (back == n)
            next++;
 
        board[r][c] = n;
        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++)
                if (solve(r + i, c + j, n + 1, next))
                    return true;
 
        board[r][c] = back;
        return false;
    }
 
    private static void printBoard(int[][] board) {
        for (int[] row : board) 
	{
            for (int c : row) 
	    {
                
                    System.out.print(c);
            }
            System.out.println();
        }
    }
}
