import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.ArrayList;
class var{
	int ival;
	String sval;
	boolean isInt;
	boolean isString;
}
class zpm{
	static int linen;
	static Pattern variable = Pattern.compile("[a-zA-Z.*]");
	static Hashtable<String, var> variables = new Hashtable<String, var>();
	public static void concat(String var, String varval, String s, String manip)
	{
		if(manip.equals("+="))
		{
			varval = varval.substring(0,varval.length()-1);
			s = s.substring(1, s.length());
			var v = new var();
			v.isString = true;
			v.isInt = false;
			v.sval = varval + s;
			variables.remove(var);
			variables.put(var, v);
		}
		else
			System.out.println("ILLEGAL STRING OPERATION: Line "+linen);
	}	
	public static void manipInt(int i, int j, String s, String manip)
	{
		var v = new var();
		v.isInt = true;
		v.isString = false;
		if(manip.equals("+="))
			i+=j;
		else if(manip.equals("-="))
			i-=j;
		else if(manip.equals("*="))
			i*=j;
		else
			System.out.println("ILLEGAL INT OPERATION: Line "+linen);
		v.ival = i;
		variables.remove(s);
		variables.put(s, v);
	}
	public static void insertInt(int i, String pos)
	{
		//System.out.println("Int");
		var v = new var();
		v.ival = i;
		v.isString = false;
		v.isInt = true;
		variables.remove(pos);
		variables.put(pos, v);
	}
	public static void insertString(String in, String pos)
	{
		//System.out.println("String");
		var v = new var();
		v.sval = in;
		v.isString = true;
		v.isInt = false;
		variables.remove(pos);
		variables.put(pos, v);
	}
	public static void print(String[] tokens)
	{
		var v = variables.get(tokens[1]);
		if(v.isInt)
			System.out.println(tokens[1] + " = " + v.ival);
		else
			System.out.println(tokens[1] + " = " + v.sval);
	}
	public static void assign(String[] tokens)
	{
		var pos = variables.get(tokens[0]);
		if(tokens.length > 4)
		{
			for(int i = 3; i<tokens.length-1; i++)
				tokens[2] += " " + tokens[i];
		}
		if(pos == null && tokens[1].equals("="))
		{
			String s = tokens[2];
			var v = new var();
			if(s.charAt(0)=='\"')
			{
				v.isString = true;
				v.isInt = false;
				v.sval = tokens[2];
				variables.put(tokens[0], v);
			}
			else if(variables.get(tokens[2]) != null && tokens[1].equals("="))
			{
				var nv = variables.get(tokens[2]);
				if(nv.isString)
					insertString(nv.sval, tokens[0]);
				else
					insertInt(nv.ival, tokens[0]);
				
			}
			else
			{
				v.isInt = true;
				v.isString = false;
				Matcher m = variable.matcher(tokens[2]);	
				if(m.matches())
				{
					var nv = variables.get(tokens[2]);
					if(nv == null)
						System.out.println("VARIABLE REFERENCED BEFORE ASSIGNMENT: Line "+linen);
					else
						v.ival = nv.ival;
				}
				else
					v.ival = Integer.parseInt(tokens[2]);
				variables.put(tokens[0], v);
			}
		}
		else if(pos != null && tokens[1].equals("="))
		{
			Matcher m = variable.matcher(tokens[2]);
			if(m.matches())
			{
				var nv = variables.get(tokens[2]);
				if(nv == null)
				{
					System.out.println("VARIABLE REFERENCED BEFORE INIT: Line "+linen);
				}
				else if(nv.isInt)
					insertInt(nv.ival, tokens[0]);
				else
					insertString(nv.sval, tokens[0]);
			}
			else
			{
				if(tokens[2].charAt(0) == '\"')
					insertString(tokens[2], tokens[0]);
				else
					insertInt(Integer.parseInt(tokens[2]), tokens[0]);
			}
		}
		else if(pos != null && tokens[1].length() == 2)
		{
			Matcher m = variable.matcher(tokens[2]);
			if(m.matches())
			{
				var vpos = variables.get(tokens[2]);
				if(vpos == null)
					System.out.println("VARIABLE REFERENCED BEFORE INIT: Line "+linen);
				else
				{
					if(pos.isInt && vpos.isInt)
						manipInt(pos.ival, vpos.ival, tokens[0], tokens[1]);
					else if(pos.isString && vpos.isString)
						concat(tokens[0], pos.sval, vpos.sval, tokens[1]);
					else
						System.out.println("TYPE MISMATCH: Line " + linen);
				}
			}
			else
			{
				if(pos.isInt && tokens[2].charAt(0) != '\"')
					manipInt(pos.ival, Integer.parseInt(tokens[2]), tokens[0], tokens[1]);
				else if(pos.isString && tokens[2].charAt(0) == '\"')
					concat(tokens[0], pos.sval, tokens[2], tokens[1]);
				else
					System.out.println("TYPE MISMATCH: Line "+linen);
			}
		}
		else
			System.out.println("ILLEGAL OPERATION Line: " + linen);
	}
	public static void forloop(String[] tokens)
	{
		int count = Integer.parseInt(tokens[1]);
		for(int i = 0; i<count; i++)
		{
			for(int j = 2; j<tokens.length-1;)
			{
				if(tokens[j].equals("FOR"))
				{
					ArrayList<String> newtokens = new ArrayList<String>();
					for(int k = j; !tokens[k-1].equals("ENDFOR"); k++)
					{
						newtokens.add(tokens[k]);
						j++;
					}
					String[] newArr = Arrays.copyOf(newtokens.toArray(), newtokens.size(), String[].class);
					forloop(newArr);
				}
				else if(tokens[j] == "PRINT")
				{
					ArrayList<String> newtokens = new ArrayList<String>();
					newtokens.add(tokens[j]);
					newtokens.add(tokens[j+1]);
					String[] newArr = Arrays.copyOf(newtokens.toArray(), newtokens.size(), String[].class);
					print(newArr);
					j+= 3;
				}
				else
				{
					ArrayList<String> newtokens = new ArrayList<String>();
					newtokens.add(tokens[j]);
					newtokens.add(tokens[j+1]);
					newtokens.add(tokens[j+2]);
					String[] newArr = Arrays.copyOf(newtokens.toArray(), newtokens.size(), String[].class);
					assign(newArr);
					j+=4;
				}
			}
		}
	}
	public static void main(String args[])
	{
		linen = 1;
		if(args.length <1)
			System.out.println("More Args Please");
		String line = null;
		try{
			FileReader reader = new FileReader(args[0]);
			BufferedReader br = new BufferedReader(reader);
			linen = 0;
			while((line = br.readLine()) != null)
			{
				String tokens[] = line.split(" ");
				if(tokens[0].equals("PRINT"))
					print(tokens);
				else if(tokens[0].equals("FOR"))
					forloop(tokens);
				else
				{
					Matcher m = variable.matcher(tokens[0]);
					if(m.matches())
						assign(tokens);
				}
				linen++;
			}
			br.close();
		}
		catch(IOException ex)	
		{
			System.out.println("Error Reading File");
		}	
	}
}
