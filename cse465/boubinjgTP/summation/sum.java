import java.util.*;

class Sum
{
	public static int sum(ArrayList<Integer> list)
	{
		int count = 0;
		for(int i : list)
			count+=i;
		return count;
	}
	public static void getPset(HashSet<ArrayList<Integer>> pset, ArrayList<Integer> list)
	{
		pset.add(list);
		for(int i = 0; i<list.size(); i++)
		{
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for(int j : list)
				temp.add(j);
			temp.remove(i);
			getPset(pset, temp);
		}
	}
	public static boolean sum(ArrayList<Integer> list, int num)
	{
		HashSet<ArrayList<Integer>> pset = new HashSet<ArrayList<Integer>>();
		getPset(pset, list);
		boolean ret = false;
		for(ArrayList<Integer> i : pset)
			if(i.size() != 0)
				ret = ret || (num == sum(i));
		return ret;
	}
	public static void main(String args[])
	{
		ArrayList<Integer> i = new ArrayList<Integer>(Arrays.asList(1,2,3,4));
		System.out.println(sum(i,10));
		System.out.println(sum(i,14));
		System.out.println(sum(i,0));
		i.add(0);
		System.out.println(sum(i,0));
		i = new ArrayList<Integer>(Arrays.asList(1,2,3,-4));
		System.out.println(sum(i,-3));
		i = new ArrayList<Integer>(Arrays.asList(1,2,8,2));
		System.out.println(sum(i,5));
		
	}
}
