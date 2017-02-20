#include <iostream>
#include <string>
#include <algorithm>
#include <vector>
#include <math.h>
#include <fstream>
#include <limits.h>
void import(std::string fname, std::vector<std::string> &vertices, 
	    std::vector<std::vector<int> > &adjacencyMatrix)
{
	int vertexCount, weight;
	
	std::string line;
	std::ifstream myfile(fname);
	
	myfile>>vertexCount;
	for(int i = 0; i<vertexCount; i++)
	{
		myfile>>line;
		vertices.push_back(line);	
	}
	for(int i = 0; i<vertexCount; i++)
	{
		std::vector<int> v;
		adjacencyMatrix.push_back(v);
		for(int j = 0; j<vertexCount; j++)
		{
			myfile>>weight;
			adjacencyMatrix[i].push_back(weight);
		}
	}
}
int minDist(std::vector<int> dist, std::vector<bool> exp)
{
	int min = INT_MAX, min_index;
	for(int v = 0; v<dist.size(); v++)
	{
		if(!exp[v] && dist[v]<=min)
		{
			min = dist[v];
			min_index = v;
		}
	}
	return min_index;
}
void isConnected(std::vector<std::string> vertices, 
	      std::vector<std::vector<int> > graph,
	      int src, int dest)
{
	//djikstra cost algorithm
	std::vector<int> dist;
	std::vector<bool> exp;
	std::vector<int> tempPath, path;
	for(int i = 0; i<vertices.size(); i++)
	{
		dist.push_back(INT_MAX);
		exp.push_back(false);
	}
	
	dist[src] = 0;
	
	for(int count = 0; count<vertices.size()-1; count++)
	{
		tempPath.clear();
		tempPath.push_back(src);
		//std::cout<<src;	

		int u = minDist(dist, exp);
		exp[u] = true;
		tempPath.push_back(u);		
		//std::cout<<u;

		for(int v = 0; v<vertices.size(); v++)
		{
			if(!exp[v] && graph[u][v] && dist[u] != INT_MAX
				   && dist[u] + graph[u][v] < dist[v])
			{
				dist[v] = dist[u] + graph[u][v];
				tempPath.push_back(v);
		//		std::cout<<v;	
			}
		}
	
		for(int i = 0; i<tempPath.size(); i++)
			std::cout<<tempPath[i];
		std::cout<<std::endl;
	}
	
	if(dist[dest] != INT_MAX)
	{
		std::cout<<"The nodes are connected, and the cost to traverse from source to destination is: ";
		std::cout<<dist[dest]<<std::endl; 
	}
	else
		std::cout<<"these nodes are not connected"<<std::endl;
	std::cout<<"The shortest path is: ";
	for(int i = 0; i<path.size(); i++)
		std::cout<<vertices[path[i]]<<" ";
	std::cout<<vertices[dest]<<std::endl;
}
int main(int argc, char* argv[])
{
	//get the file name 
	std::cout<<"Please provide a graph in the form of a text document"<<std::endl;
	std::cout<<"The accepted format is detailed in the readme file in this directory."<<std::endl;;
	std::string fname;
	std::cin>>fname;
	//create the data structure for the graph
	std::vector<std::string> vertices;
	std::vector<std::vector<int> > adjacencyMatrix;
	//import the graph into the data structure
	import(fname, vertices, adjacencyMatrix);
	//read in two vertices from the user
	std::cout<<"Enter two vertices to see if they are connected: ";
	std::string src, dest;
	std::cin>>src>>dest;
	//find the integer counterparts for the entered vertices
	int srcPos = INT_MAX, destPos = INT_MAX;
	srcPos = std::find(vertices.begin(), vertices.end(), src) - vertices.begin();
	destPos = std::find(vertices.begin(), vertices.end(), dest) - vertices.begin();
	//see if they are connected
	isConnected(vertices, adjacencyMatrix,srcPos,destPos);	
	
	return 0;
}

