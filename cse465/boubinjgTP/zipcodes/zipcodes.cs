using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
namespace hw4{
 public class zipcodes{
	public static HashSet<string> findCities(string state, List<string> file)
	{
		HashSet<string> cities = new HashSet<string>();
		foreach(string line in file)
		{
			string[] info =  line.Split('\t');
			if(info[4].Equals(state))
			{
				cities.Add(info[3]);
			}
		}
		return cities;
	}
	public static void FindCommonCities(List<string> file)
	{
		List<string> states = File.ReadAllLines("states.txt").ToList();
		List<HashSet<string> > cities = new List<HashSet<string> >();
		for(int i = 0; i<states.Count; i++)
		{
			HashSet<string> s = findCities(states[i], file);
			cities.Add(s);
		}

		SortedSet<string> commonCities = new SortedSet<string>();
		foreach(string city in cities[0])
			commonCities.Add(city);
		for(int i = 1; i<cities.Count; i++)
		{
			List<string> removalList = new List<string>();
			foreach(string s in commonCities)
				if(!cities[i].Contains(s))
					removalList.Add(s);

			foreach(string s in removalList)
				commonCities.Remove(s);
		}
		File.WriteAllLines("CommonCityNames.txt", commonCities);
	}
	public static void FindLatLon(List<string> file)
	{
		List<string> zips = File.ReadAllLines("zips.txt").ToList();
		HashSet<string> cities = new HashSet<string>();
		List<string> ret = new List<string>();
		foreach(string line in file)
		{
			string[] info = line.Split('\t');
			string key = info[1];
			if(!cities.Contains(key) && zips.Contains(info[1]))
			{
				cities.Add(key);
				ret.Add(info[6] + " " + info[7]);
			}
		}
		File.WriteAllLines("LatLon.txt",ret);
	}
	public static void CityStates(List<string> file)
	{
		List<string> cities = File.ReadAllLines("cities.txt").ToList();
		List<SortedSet<string> > cityState = new List<SortedSet<string> >();
		foreach(string city in cities)
		{
			SortedSet<string> states = new SortedSet<string>();
			foreach(string zip in file)
			{
				string[] info = zip.Split('\t');
				if(city.Equals(info[3], StringComparison.OrdinalIgnoreCase))
					states.Add(info[4]);
			}
			cityState.Add(states);
		}
		string [] lines = new string[3];
		for(int i = 0; i<cityState.Count; i++)
		{
			string line = "";
			for(int j = 0; j<cityState[i].Count; j++)
			{
				line += cityState[i].ElementAt(j) + " ";	
			}
			lines[i] = line;

		}
		File.WriteAllLines("CityStates.txt",lines);
	}
	public static void Main(String [] args)
	{
		List<string> file = File.ReadAllLines("zipcodes.txt").ToList();
		FindCommonCities(file);
		FindLatLon(file);
		CityStates(file);
	}
 }
}
