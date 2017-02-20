import java.io.*;
import java.util.*;
public class Bayes
{
	public static class ConditionalProb
	{
		double probability;
		String attribute, classification;
		public ConditionalProb(String cls, double prob)
		{
			attribute = "";
			classification = cls;
			probability = prob;
		}	
		public ConditionalProb(String att, String cls, double prob)
		{
			attribute = att;
			classification = cls;
			probability = prob;
		}
	}
	public static void main(String args[])
	{
		ArrayList<ArrayList<String>> fileList = readData(args[0]);
		ArrayList<ConditionalProb> probList = calculateProbs(fileList);
		Test(fileList,probList);
		/*for(int o = 0; o<probList.size(); o++)
			System.out.println("class " + probList.get(o).classification + " att " + probList.get(o).attribute + " prob "+probList.get(o).probability );
		ArrayList<ArrayList<String>> testList = new ArrayList<ArrayList<String>>();
		//ew
		testList.add(new ArrayList<String>());
		testList.add(new ArrayList<String>());
		testList.add(new ArrayList<String>());
		
		testList.get(0).add("L");
		testList.get(0).add("M");
		testList.get(0).add("S");
		
		testList.get(1).add("B");
		testList.get(1).add("A");
		testList.get(1).add("B");

		testList.get(2).add("+");
		testList.get(2).add("-");
		testList.get(2).add("+");

		for(int i = 0; i<fileList.get(0).size(); i++)
		{	
			for(int j = 0; j<fileList.size(); j++)
			{	
				System.out.print(fileList.get(j).get(i));
			}
			System.out.println();
		}
		for(int k = 0; k<testList.size(); k++)
		{
			for(int l = 0; l<testList.get(0).size(); l++)
				System.out.print(testList.get(l).get(k));
			System.out.println();
		}
		Test(testList,probList);*/
	}

	public static void Test(ArrayList<ArrayList<String>> fileList, ArrayList<ConditionalProb> probList)
	{
		HashMap<String, Double> classList = new HashMap<String, Double>();
		ArrayList<Double> rowValue = new ArrayList<Double>(); 
		ArrayList<String> classValue = new ArrayList<String>();
		for(int i = 0; i<fileList.get(0).size(); i++)
                {
                        if(!classList.containsKey(fileList.get(fileList.size()-1).get(i)))
                        {
				for(ConditionalProb p : probList)
				{
					if(p.attribute.equals("") && p.classification.equals(fileList.get(fileList.size()-1).get(i)))
					{
						classList.put(fileList.get(fileList.size()-1).get(i),p.probability);
						rowValue.add(p.probability);
						classValue.add(fileList.get(fileList.size()-1).get(i));
					}
					
				}
			}
                }
		ArrayList<Double> store = new ArrayList<Double>(rowValue);
		double correct = 0, total = 0;
		for(int j = 0; j<fileList.get(0).size(); j++)
		{
			rowValue = new ArrayList<Double>(store);
			for(int k = 0; k<fileList.size(); k++)
			{
				if(k != fileList.size()-1)
				{
			
					int count = 0;
					for(Map.Entry<String, Double> entry : classList.entrySet())
					{	
						//count from rowValue.size()-count-1
						System.out.print("row Value " + (rowValue.get(count)));
						//System.out.print("row Value " + (rowValue.get(rowValue.size()-count-1)));
						for(ConditionalProb p : probList)
						{
							if(p.classification.equals(entry.getKey()) &&
						   	   p.attribute.equals(fileList.get(k).get(j)) )
							{
								
								//rowValue.set(rowValue.size()-count-1, rowValue.get(rowValue.size()-count-1)*p.probability);	
								
								rowValue.set(count, rowValue.get(count)*p.probability);
								System.out.println("*"+p.probability);
							}
						}
						count++;
					}
				}		
			}
			int index = 0; 
			double greatestValue = 0;
			for(int l = 0; l < rowValue.size(); l++)
			{
				if(rowValue.get(l) > greatestValue)
				{
					greatestValue = rowValue.get(l);
					index = l;
				}
			}
			System.out.println(greatestValue);
			System.out.println("chosen value "+ classValue.get(index) + " orig value" + fileList.get(fileList.size()-1).get(j));

			if(classValue.get(index).equals(fileList.get(fileList.size()-1).get(j)))
			{
				correct++;
				total++;
			}
			else
				total++;
		}
		
		System.out.println(correct/total);
		System.out.println("correct: " + correct + " total: "+ total );
	}

	public static ArrayList<ConditionalProb> calculateProbs(ArrayList<ArrayList<String>> fileList)
	{
		ArrayList<ConditionalProb> probList = new ArrayList<ConditionalProb>();
		
		//find the classes and their probabilities
		ArrayList<String> classList = new ArrayList<String>();
		for(int i = 0; i<fileList.get(0).size(); i++)
		{
			if(!classList.contains(fileList.get(fileList.size()-1).get(i)))
			{
				classList.add(fileList.get(fileList.size()-1).get(i));
			}
		}
		for(int j = 0; j<classList.size(); j++)
		{
			double attCount = 0;
			for(int k = 0; k<fileList.get(0).size();k++)
			{
				if(fileList.get(fileList.size()-1).get(k).equals(classList.get(j)))
				{
					attCount++;
				}
			}
			ConditionalProb c = new ConditionalProb(classList.get(j),attCount/fileList.get(0).size());
			probList.add(c);
		}


		//find the probabilities of all of the attributes
		
		//for each column in the attribute list except the last
		ArrayList<String> attributeList = new ArrayList<String>();
		for(int l = 0; l<fileList.size()-1; l++)
		{
			attributeList.clear();
			//find the attributes in the column
			for(int m = 0; m<fileList.get(l).size(); m++)
			{
				if(!attributeList.contains(fileList.get(l).get(m)))
 				{
                         	       attributeList.add(fileList.get(l).get(m));
				}
			}
			//generate their conditional probabilities for each classification
			double classCount = 0, classAttCount = 0;
			for(String classifier : classList)
				for(String att : attributeList)
				{	
					classCount = 0; 
					classAttCount = 0;
					for(int o = 0; o<fileList.get(0).size(); o++)
					{
						//quadruply nested for loop? Maybe this can be better.
						//update: maybe not.
						//System.out.println(fileList.get(fileList.size()-1).get(o) + " "+ classifier);
						if(fileList.get(fileList.size()-1).get(o).equals(classifier))
						{
							classCount++;
							if(fileList.get(l).get(o).equals(att))
							{
								classAttCount++;
							}
						}
					}
					ConditionalProb c = new ConditionalProb(att,classifier,classAttCount/classCount);
					probList.add(c);
				}
		}
		return probList;
		
	}
	
	public static ArrayList<ArrayList<String>> readData(String fileName)
	{
		String line = null;
		ArrayList<ArrayList<String>> fileList = new ArrayList<ArrayList<String>>();
		try
		{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fileReader);
			
			//generate arraylist based on the first line
			line = br.readLine();
			StringTokenizer st;
			
			st = new StringTokenizer(line);
			while(st.hasMoreTokens())
			{
				ArrayList<String> s = new ArrayList<String>();
				s.add(st.nextToken());
				fileList.add(s);
			}
			while((line = br.readLine()) != null)
			{
				int i = 0;
				st = new StringTokenizer(line);
				while(st.hasMoreTokens())
				{
					fileList.get(i).add(st.nextToken());
					i++;
				}
			}
		}
		catch(IOException ex)
		{
			System.out.println("error");
		}
		return fileList;
	}
}
