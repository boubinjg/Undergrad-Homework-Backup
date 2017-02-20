#include <iostream>
#include <fstream>
#include <cstdlib>
int main()
{
	std::ofstream myfile;
	myfile.open("fiftyThousand.txt");
	for(int i = 0; i<50000; i++)
	{
		myfile << rand() % 50000;
		myfile << "\n";
	}
	return 0;
}
