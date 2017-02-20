#include <iostream>
int main()
{
	int A = 1;
	for(int i = 0; i<1000000000; i++)
		A += 1;
	std::cout<<A<<std::endl;
}
