import java.io.*;
import java.util.*;
public class Hidato
{
	public class HidatoBoard
	{
		LinkedList<Integer> empty = new LinkedList<Integer>();
		LinkedList<LinkedList<Integer>> constraintList;
		int numLeft = 0;
		int[][] map;
		public HidatoBoard(int[][] mapIn)
		{
			map = mapIn;
			LinkedList<LinkedList<Integer>> list = getConstraints(map);
			constraintList = narrowConstraints(list, map);
			numLeft = empty.size();
			
		}
		public LinkedList<LinkedList<Integer>> narrowConstraints(LinkedList<LinkedList<Integer>> list, int[][] map)
		{
			LinkedList<LinkedList<Integer>> retList = new LinkedList<LinkedList<Integer>>();

			ListIterator<LinkedList<Integer>> it = list.listIterator();
			
			LinkedList<Integer> sto = new LinkedList<Integer>();
			
			ListIterator<Integer> rowIt;
			
			ListIterator<Integer> emptyIt = empty.listIterator();

			LinkedList<Integer> ret;
			int i = 0, index = 0,number = 0;
			while(it.hasNext())
			{
				ret = new LinkedList<Integer>();
				sto = it.next();
				index = emptyIt.next();
				rowIt = sto.listIterator();
				System.out.println("Index "+index);
				while(rowIt.hasNext())
				{
					number = rowIt.next();
					if(canExist(index,number,map))
					{	
						ret.add(number);
					}
					i++;
				}
				System.out.println();
				retList.add(ret);
			}
			return retList;
					
		}
		
		public boolean canExist(int index, int number, int[][] map)
		{
			int size = map.length*map.length;
			int r = index/3, c = index%3;
			for(int i = 0; i<map[0].length; i++)
				for(int j = 0; j<map[0].length; j++)
				{
				
					if(map[i][j] == (number-1)%size  || map[i][j] == (number +1)%size)
					{
						if(Math.abs(i-r) > 1 || Math.abs(j-c) > 1)
						{
							return false;
						}
					}
				}
			System.out.println(number);	
			return true;
		}
		public LinkedList<LinkedList<Integer>> getConstraints(int[][] map)
		{
			LinkedList<LinkedList<Integer>> myList = new LinkedList<LinkedList<Integer>>();
			ListIterator<LinkedList<Integer>> it = myList.listIterator();
			LinkedList<Integer> possible = new LinkedList<Integer>();
			LinkedList<Integer> actual = new LinkedList<Integer>();
			int[] total = new int[map[0].length * map[0].length];
			int count = map[0].length * map[0].length;
			for(int i = 0; i<map[0].length; i++)
				for(int j = 0; j<map[0].length; j++)
				{
					if(map[i][j] != 0)
					{
						possible.add(map[i][j]);
						count--;
					}
					else
					{
						empty.add(i*3+j);
					}
					total[i*3 + j] = i*3 + j + 1;
					
				}
			for(int k = 0; k<total.length; k++)
				if(!possible.contains(k+1))
					actual.add(k+1);
		
			for(int l = 0; l<count; l++)
				myList.add(actual);
			return myList;
		}
	}	
	HidatoBoard b;
	public static void main(String args[])
	{
		Hidato h = new Hidato();
		int[][] map = h.readFile(args[0]);
		h.printMap(map);
		h.main(map);
	}
	public void main(int[][] map)
	{
		System.out.println(0%9);
		b = new HidatoBoard(map);
		solve(b);
	}
	int i = 0;
	public void solve(HidatoBoard board)
	{
		if(board.numLeft == 0)
			printMap(board.map);
		else
		{
			//create a list of targets and its iterators (available board squares)
			LinkedList<Integer> targList = new LinkedList<Integer>();
			targList = board.empty;
			ListIterator<Integer> it = targList.listIterator();
			
			
			//create a domain and iterator (constraints for board squares)
			LinkedList<LinkedList<Integer>> domain = board.constraintList;
			ListIterator<LinkedList<Integer>> domainIt = domain.listIterator();
			ListIterator<Integer> targIt;
			List<LinkedList<Integer>> list = new ArrayList<LinkedList<Integer>>(board.constraintList);
			
			//next available board square
			int targPlacement = it.next();
			//set the next targets domain to its constraints
			//LinkedList<Integer> targDomain = domainIt.next();
			targDomain = list.get(i);
			//iterate through those constraints
			targIt = targDomain.listIterator();
			while(targIt.hasNext())	
			{
				HidatoBoard copy = board;
				
				copy.map[targPlacement/copy.map.length][targPlacement%copy.map.length] = targIt.next();
				
				printMap(board.map);
				if(!valid(board))
				{	
					System.out.println("invalid");				
					targIt.remove();
				}
				else
				{
					System.out.println("valid");
					
					System.out.println(domain.size());
					i++;
					board.numLeft--;
					it.remove();
				}
				
				solve(copy);
			}
		}	
	}
	public void futureCheck(HidatoBoard board)
	{
		//board.constraintSet
	}
	public boolean valid(HidatoBoard board)
	{
		int[][] map = board.map;
		int size = board.map.length;
		for(int i = 0; i<size*size; i++)
			for(int j = 0; j<size*size; j++)
			{
				int r1 = i/size, r2 = j/size, c1 = i%size, c2 = j%size;
				if(Math.abs(map[r1][c1]-map[r2][c2]) == 1 || Math.abs(map[r1][c1] - map[r2][c2]) == size*size-1 ) 
				{
					if(Math.abs(r2-r1) > 1 || Math.abs(c2-c1)>1)
					{	
						System.out.println(map[r1][c1] + " " + map[r2][c2]);
						return false;	
					}
				}	
				else if(map[r1][c1] == map[r2][c2] && (r1!=r2 || c1!=c2) && map[r1][c1]!= 0)
					return false;
			}
		return true;
	}
	public void printMap(int[][] map)
	{
		for(int i = 0; i<map[0].length; i++)
		{
			for(int j = 0; j<map[0].length; j++)
			{
				System.out.print(map[i][j]);
			}
			System.out.println("");
		}
	}
	public int[][] readFile(String file)
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
}
