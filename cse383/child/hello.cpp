#include <iostream>
#include <string>
#include <algorithm>
#include <vector>
#include <math.h>
#include "bigInt.h"
int main()
{
	int count = 0;
	for(int i = 0; i<1000; i++)
	{
		if(i%3 == 0 || i%5 == 0)
			count += i;	
	}
	std::cout << count;
	return 0;
}

