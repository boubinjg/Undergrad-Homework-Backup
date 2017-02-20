#include "bandMatrix.h"
int main()
{
	bandMatrix b(3);
	b.set(0,0,1);
	b.set(0,1,2);
	b.set(1,0,3);
	b.set(1,1,4);
	b.set(1,2,5);
	b.set(2,1,6);
	b.set(2,2,7);
	
	bandMatrix c(3);
	c = b;
	bandMatrix d = std::move(b);
	d = c + d;
	//b = std::move(c);

	return 0;
}
