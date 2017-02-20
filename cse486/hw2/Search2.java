import java.io.*;
import java.util.*;
public class Search2
{
	Map m;
	public class NodeComparator implements Comparator<Pair>
	{
		@Override
		public int compare(Pair first, Pair second)
		{
			return m.getWeight(first) - m.getWeight(second);	
		}
	}
	public class Pair
	{
		int x, y;
		String route = "";
		boolean visited = false;
		public Pair(int inx, int iny)
		{
			x = inx;
			y = iny;
		}
		public int getX()
		{
			return x;
		}
		public int getY()
		{
			return y;
		}
		public void setRoute(String s)
		{
			route += s;
		}
		public String getRoute()
		{
			return route;
		}
		
	}
	public class Map
        {
                int startx, starty, width, height, numMessages;
		int  map[][];
		double weight[][];
		boolean visit[][];
		boolean found[];
                public Map()
                {
			
                }
                public void setStart(int x, int y)
                {
                        startx = x;
                        starty = y;
                }
		public void setDimensions(int x, int y)
		{
			height = x; 
	 		width = y;
			weight = new double[height][width];
			map = new int[height][width];
			populateVisited();
		}
		public void populateVisited()
		{
			visit = new boolean[height][width];
			for(int i = 0; i<height; i++)
				for(int j = 0; j<width; j++)
				{
					visit[i][j] = false;
				}
		}
		public void setVisited(Pair p)
		{
			visit[p.getX()][p.getY()] = true;
		}
		public boolean getVisited(int x, int y)
		{
		//	System.out.println(x + " " + y);
			return visit[x][y];
		}
		public void setNumMessages(int num)
		{
			numMessages = num;
			found = new boolean[num+1];
			found[0] = true;
			for(int i = 0; i<num; i++)
				found[i] = false;
		}  
		public boolean found(int num)
		{
			if(!found[num])
			{
				found[num] = true;
				return true;
			}
			else
				return false;
				
		} 
		public int getNumMessages()
		{
			return numMessages;
		}
		public int getStartx()
		{
			return startx;
		}
		public int getStarty()
		{
			return starty; 
		}
		public int getHeight()
		{
			return height;
		}
		public int getWidth()
		{
			return width;
		}
		public void setMap(int height, int width, int value)
		{
			map[height][width] = value;
		}
		public void setWeight(int height, int width, double value)
		{
			weight[height][width] = value;
		}
		public int getWeight(Pair p)
		{
			return (int)weight[p.getX()][p.getY()];
		}
		public int getMessage(int height, int width)
		{
			return map[height][width];
		}
		public void printMap()
		{
			for(int i = 0; i<height; i++)
			{	
				for(int j = 0; j<width; j++)
				{	
					System.out.print(map[i][j]+" ");
				}
				System.out.println();
			}
	
			for(int k  = 0; k<height; k++)
			{
				for(int l = 0; l<width; l++)
				{
					System.out.print(weight[k][l]+ " ");
				}
				System.out.println();
			}
		}
        }
	public static void main(String args[])
	{
		if(args.length != 2)
		{
			System.out.println("Please provide both a search type and a file");
		}
		else
		{
			Search s = new Search();
			Map m = s.readFile(args[1]);
			if(args[0].equals("BFS"))
				s.bfs();
			else if(args[0].equals( "UCS"))
				s.ucs();
			else
			{
				System.out.println("Please enter a valid search type.");
				System.out.println("Valid search types include: BFS, UCS");
			}
		}
	}
	public Map readFile(String map)
	{

		m = new Map();
		try{
		FileReader fr = new FileReader(map);
		BufferedReader br = new BufferedReader(fr);
		String s;
		String split[];
		int i = 0,j=0,k=1;
		while((s = br.readLine())!=null)
		{
			if(i == 0)//first line
			{	
				m.setDimensions(Character.getNumericValue(s.charAt(0)),Character.getNumericValue(s.charAt(2)));
			}
			else if(i == 1)//second line: starting position
				m.setStart(Character.getNumericValue(s.charAt(0)),Character.getNumericValue(s.charAt(2)));
			else if(i<m.getHeight()+2)
			{
				while(j<m.getWidth())
				{
					if(s.charAt(j)=='.')
						m.setMap(m.getHeight()-i+1,j,0);
					else
					{
						m.setMap(m.getHeight()-i+1,j,k);
						k++;
						m.setNumMessages(k-1);
					}
					j++;
				}
				j = 0;
			}
			else
			{
				split = s.split("\\s+");
				while(j<m.getWidth())
                                {
                             		m.setWeight(2*m.getHeight() -i+1, j, Double.parseDouble(split[j]));
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
		
		return m;
	}
	public void bfs()
	{
		int i = 1;
		int startx = m.getStartx();
		int starty = m.getStarty();
		boolean solution=false;
		int j = 0;
		String route = "";
		//System.out.println(i);
		while( i <= m.getNumMessages()+1)
		{
			solution = false;
			m.populateVisited();
		 
			//create the starting point
			Pair root = new Pair(startx, starty);
			//create the data structure
			Queue<Pair> frontier = new LinkedList<Pair>();	
			//add the root to the data structure
			frontier.add(root);
			//denote that the root is visited
			m.setVisited(root);
			//while the data structure is not empty and there is no solution...
			while(!frontier.isEmpty()&& !solution)
			{
				//pop the top node off the queue
					
				Pair node = (Pair)frontier.remove();
				//create a new node which will hold a child of the top node
				Pair child = null;
				//while there are valid unvisited children and there is no solution
				while((child = getUnvisitedChildNode(node))!=null&&!solution)
				{
					//System.out.println(child.getX()+ " " + child.getY());
					//set the child as visited
					m.setVisited(child);
					//print the child node
					printNode(node,child);
					//check for solution
					if(m.getMessage(child.getX(),child.getY()) == i && i<=m.getNumMessages())
					{	
						solution = true;
						i++;
						startx = child.getX();
						starty = child.getY();
						route += child.getRoute();
						//System.out.println(route);
					}
					else if(child.getX() == m.getStartx() && child.getY() == m.getStarty() && i>m.getNumMessages())
					{
						solution = true;
						i++;
						route += child.getRoute();
					}
						
					//add the child to the data structure
					frontier.add(child);
				}	
			}
		}	
		System.out.println(route);
	}
	public void ucs()
	{
		
		int i = 1;
                int startx = m.getStartx();
                int starty = m.getStarty();
                boolean solution=false;
                int j = 0;
                String route = "";
                //System.out.println(i);
                while( i <= m.getNumMessages()+1)
                {
                        solution = false;
                        m.populateVisited();

                        //create the starting point
                        Pair root = new Pair(startx, starty);
                        //create the comparator
			Comparator<Pair> comparator = new NodeComparator();
			//create the data structure
                        PriorityQueue<Pair> frontier = new PriorityQueue<Pair>(1,comparator);
                        //add the root to the data structure
                        frontier.add(root);
                        //denote that the root is visited
                        m.setVisited(root);
                        //while the data structure is not empty and there is no solution...
                        while(!frontier.isEmpty()&& !solution)
                        {
                                //pop the top node off the queue

                                Pair node = (Pair)frontier.remove();
                                //create a new node which will hold a child of the top node
                                Pair child = null;
                                //while there are valid unvisited children and there is no solution
                                while((child = getUnvisitedChildNode(node))!=null&&!solution)
                                {
                                        //System.out.println(child.getX()+ " " + child.getY());
                                        //set the child as visited
                                        m.setVisited(child);
                                        //print the child node
                                        printNode(node,child);
                                        //check for solution
                                        if(m.getMessage(child.getX(),child.getY()) == i && i<=m.getNumMessages())
                                        {
                                                solution = true;
                                                i++;
                                                startx = child.getX();
                                                starty = child.getY();
                                                route += child.getRoute();
                                                //System.out.println(route);
                                        }
                                        else if(child.getX() == m.getStartx() && child.getY() == m.getStarty() && i>m.getNumMessages())
                                        {
                                                solution = true;
                                                i++;
                                                route += child.getRoute();
                                        }

                                        //add the child to the data structure
                                        frontier.add(child);
                                }
                        }
                }
		System.out.println(route);
	}
	public Pair getUnvisitedChildNode(Pair node)
	{
		if(node.getX()+1 < m.getHeight() && !m.getVisited(node.getX()+1,node.getY()))
			return new Pair(node.getX()+1, node.getY());
		else if(node.getX() != 0 && !m.getVisited(node.getX()-1,node.getY()))
			return new Pair(node.getX()-1,node.getY());
		else if(node.getY()+1 < m.getWidth() && !m.getVisited(node.getX(),node.getY()+1))
			return new Pair(node.getX(),node.getY()+1);
		else if(node.getY() != 0 && !m.getVisited(node.getX(),node.getY()-1))
			return new Pair(node.getX(),node.getY()-1);
		else 
		{	
			return null;
		}
	}
	public void printNode(Pair parent, Pair child)
	{	
		if(parent.getX() > child.getX())
			child.setRoute(parent.getRoute() + "S");
		else if(parent.getX() < child.getX())
			child.setRoute(parent.getRoute() + "N");
		else if(parent.getY() > child.getY())
			child.setRoute(parent.getRoute() + "E");
		else 
			child.setRoute(parent.getRoute() + "W");
	}
}


