#include <iostream>
#include <string>
#include <algorithm>
#include <vector>
#include <math.h>
#include <fstream>
#include <limits.h>
#include <iterator>
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
void dijkstra(std::vector<std::string> vertices, 
	      std::vector<std::vector<int> > graph,
	      int src, int dest)
{
	//djikstra cost algorithm
	std::vector<int> dist, parents;
	std::vector<bool> exp;
	for(int i = 0; i<vertices.size(); i++)
	{
		dist.push_back(INT_MAX);
		exp.push_back(false);
		parents.push_back(-1);
	}
	
	dist[src] = 0;
	parents[src] = src;
	for(int count = 0; count<vertices.size()-1; count++)
	{
	
		//std::cout<<src;	
		int u = minDist(dist, exp);
		exp[u] = true;
		//std::cout<<u;
		for(int v = 0; v<vertices.size(); v++)
		{
			if(!exp[v] && graph[u][v]!=0 && dist[u] != INT_MAX
				   && dist[u] + graph[u][v] < dist[v])
			{
				dist[v] = dist[u] + graph[u][v];
				parents[v] = u;
			}
		}
	}
	if(dist[dest] != INT_MAX)
	{
		std::cout<<"The nodes are connected, and the cost to traverse from source to destination is: ";
		std::cout<<dist[dest]<<std::endl; 
		std::cout<<"The shortest path is: ";
		std::vector<std::string> print;
		int node = dest;	
		while(node != src)
		{
			print.push_back(vertices[node]);
			node = parents[node];
		}
		print.push_back(vertices[node]);
	
		std::copy(print.rbegin(), print.rend(), std::ostream_iterator<std::string>(std::cout, " "));
	}
	else
		std::cout<<"These nodes are not connected";
}
void prim(std::vector<std::vector<int> > graph, std::vector<std::string> vertices)
{
	std::vector<int> parent;
	std::vector<int> weight;
	std::vector<bool> treeSet;
	
	for(int i = 0; i<graph.size(); i++)
	{
		weight.push_back(INT_MAX);
		treeSet.push_back(false);
		parent.push_back(-1);
	}
	weight[0] = 0;

	for(int count = 0; count < graph.size()-1; count++)
	{
		int u = minDist(weight, treeSet);
		treeSet[u] = true;

		for(int i = 0; i<graph.size(); i++)
		{	
			if(graph[u][i] != 0 && treeSet[i]==false && graph[u][i] < weight[i])
			{
				parent[i] = u;
				weight[i] = graph[u][i];
			}
		}
	}
	for(int i = 1; i<graph.size(); i++)
	{
		std::cout<<"edge: "<<vertices[parent[i]] <<"-"<<vertices[i];
		std::cout<<" weight: "<<graph[i][parent[i]]<<std::endl;
	} 
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
	dijkstra(vertices, adjacencyMatrix,srcPos,destPos);	
	std::cout<<std::endl;
	//print the minimum spanning tree
	prim(adjacencyMatrix, vertices);
	return 0;
}

