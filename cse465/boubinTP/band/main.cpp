#include "bandMatrix.h"
#include <iostream>

int main(int argc, char *argv[]) {
	bandMatrix b1(400);	
	bandMatrix b2(10);	
	bandMatrix b3 = b1 + b2;

	std::cout << b1.get(0, 0) << std::endl;
	std::cout << b2.get(0, 0) << std::endl;
	std::cout << b3.get(0, 0) << std::endl;

	b1.set(3, 4, 0);
	b2.set(3, 4, 0);
	b3.set(3, 4, 0);

	return 0;
}
