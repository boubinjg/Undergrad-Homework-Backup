#include <algorithm>
#include <vector>
#include <iostream>
#include <string>
#include <fstream>
#include <array>
void selectionSort(std::vector<int> &list)
{
	int i, j, min, sto;
	for (i= 0; i < list.size()-1; i++)
	{
		min = i;               
		for (j=i+1; j<=list.size()-1; j++)  
          	{
             		if (list[j] < list[min])
                	min = j;
		}
         	if(min!=i)
		{	
			sto = list[min];  
        		list[min] = list[i];
         		list[i] = sto;
		}
     	}	
}
void merge(std::vector<int> &origList, std::vector<int> &list1, std::vector<int> &list2)
{
	std::vector<int> ret;
	int left = 0, right = 0;
	
	while(left<list1.size() && right < list2.size())
	{
		if(list1[left] < list2[right])
		{
			ret.push_back(list1[left]);
			left++;
		}
		else
		{
			ret.push_back(list2[right]);
			right++;
		}
	}
	while(left<list1.size())
	{
		ret.push_back(list1[left]);
		left++;
	}
	while(right<list2.size())
	{
		ret.push_back(list2[right]);
		right++;
	}
	origList = ret;
}
void mergeSort(std::vector<int> &list)
{
	if(list.size() == 1)
		return;	
	
	std::vector<int>::iterator middle = list.begin()+(list.size()/2);
	
	std::vector<int> list1(list.begin(),middle);
	std::vector<int> list2(middle,list.end());

	mergeSort(list1);
	mergeSort(list2);
	
	return merge(list, list1, list2);
}
std::vector<int> readFile(std::string fileName)
{
	std::ifstream myfile(fileName);
	std::string line;
	std::vector<int> ret;
	if(myfile.is_open())
	{
		while( getline (myfile,line ))
		{
			ret.push_back(stoi(line));	
		}
		myfile.close();
	}
	return ret;
}
int main(int argc, char *argv[])
{
	std::vector<int> v;
	std::string command(argv[1]);
	std::string fileName(argv[2]);
	std::string print(argv[3]);
	
	v = readFile(fileName);
	if(command == "mergesort")
	{	
		std::cout<<"merge sort on vector of size "<<v.size()<<std::endl;
		mergeSort(v); 
                std::cout<<v.size()<<std::endl;
	}
	if(command == "selectionsort")
	{
		std::cout<<"selection sort on vector of size "<<v.size()<<std::endl;
		selectionSort(v);
	}
	
	if(print == "print")
		for(int i = 0; i<v.size(); i++)
			std::cout<<v[i]<<' ';
	std::cout<<std::endl;
	
	
	return 0;
}
