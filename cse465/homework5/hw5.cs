using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Reflection;

namespace SearchReflection {
	public abstract class Media {
		public String title;
		public Media(String t) {
			title = t;
		}
		public bool Search(String target) {
			return title.IndexOf(target) >= 0;
		}
		public bool MegaSearch(String target) { // Fill this in
		
			var s = typeof(Media).Assembly.GetTypes().Where(type => type.IsSubclassOf(typeof(Media)));	
			foreach (var item in s)
			{
				
				foreach(FieldInfo info in item.GetFields())
				{
					try{
						if(info.FieldType.ToString() == "System.String")
						{
							if(info.GetValue(this).ToString().IndexOf(target)>=0)
								return true;
						}
					}
					catch{}
				}
			}
			return false;
		}
	}
	public class Movie : Media {
		public String director;
		public int runningTime;
		public String star;
		public Movie(String t, String d, int time, String s) : base(t) {
			director = d;
			runningTime = time;
			star = s;
		}
		new public bool Search(String target) 	{
			return director.IndexOf(target) >= 0 || 
					star.IndexOf(target) >= 0 || base.Search(target);
		}


	}
	public class Song : Media {
		public String genre;
		public String artist;
		public Song(String t, String a, String g) : base(t) {
			artist = a;
			genre = g;
		}
		new public bool Search(String target) {
			return genre.IndexOf(target) >= 0 ||
					artist.IndexOf(target) >= 0 || base.Search(target);
		}
	}
	class Program {
		static void Main(string[] args) {
			Song s1 = new Song("All you need is love", "Beatles", "Classic rock");
			Movie v1 = new Movie("Caddyshack", "Harold Ramis", 120, "Bill Murray");


			Console.WriteLine(s1.MegaSearch("love"));
			Console.WriteLine(s1.MegaSearch("rock"));
			Console.WriteLine(s1.MegaSearch("Stones"));
			Console.WriteLine(v1.MegaSearch("Murray"));
			Console.WriteLine(v1.MegaSearch("Chase"));
			Console.WriteLine(v1.MegaSearch("Caddyshack"));
		}
	}
}

