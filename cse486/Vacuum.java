
/*Jayson Boubin
  CSE 486 Homework 1
  Most efficient Vacuum program ever 3000

  To compile: in the directory cse486: javac Vacuum.java
  To run with command line arguments: java Vacuum <s> <d>
  	returns the vacuums performance at the specified s and d
  To run without command line arguments: java Vacuum -> returns all
	s and d between 0 and 1 in intervals of .05  
 
  the program performs the specified actions by testing s and d 
  at all numbers between 0 and 1 at intervals of .05 and writing them
  to a csv called results.csv which can be imported into excel fairly 
  easily for analysis. 
*/

//package boubinjg;

import java.util.*;
import java.io.*;
public class Vacuum{
	public double doVacuum(double s, double d)
	{
		boolean[][] b = new boolean[4][2]; // [x][0] == cleanliness, [x][1] = vacuum position, b = board
		b[0][1] = true;//vacuum is on square A 
		int v = 0,performance = 0; //vacuums current position, number of clean squares
		boolean forward = true; //denotes the vacuums direction of movement 
		for(int i = 0; i<10000; i++)
		{
			//timestep action 1: dirty the squares
			for(int j = 0; j<4; j++)
			{
				if(Math.random()<d)	
					b[j][0] = true;
				//increase performance for all squares not dirty 
				if(!b[j][0])
					performance++;
			}
			//timestep action 2: move?
			if(!b[v][0])
			{
				//move the vacuum off of v
				b[v][1] = false;
				//switch direction if at an edge
				if(forward && v == 3)
					forward = false;
				else if(!forward && v == 0)
					forward = true;
				//move v
				if(forward)
					v++;
				else
					v--;
				b[v][1] = true;
			}	
			//timestep action 3: clean?
			if(b[v][0]&&Math.random()<s)
			{
				//clean the square
				b[v][0] = false;
				//increase performance if square is cleaned
				performance++;
			}
		}
		return performance;
	}
	public static void main(String args[])
	{

		try{
		Vacuum v = new Vacuum();
		
		if(args.length == 2)

			System.out.println(v.doVacuum(Double.parseDouble(args[0]), Double.parseDouble(args[1])));
		else
		{
			FileWriter writer = new FileWriter("results.csv");	
			for(double s = 0; s<=20; s++)
			{		
				for(double d = 0; d<=20; d++)
				{
					writer.append(v.doVacuum(.05*s,.05*d)+ ",");
				}
				writer.append("\n");
			}
			writer.flush();
			writer.close();
		}
		}catch(IOException err)
		{
			System.out.println("Oh No!");
			err.printStackTrace();
		}	
	}
}
