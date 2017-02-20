#include "bandMatrix.h"
int main()
{
	bandMatrix mat1(4);
	bandMatrix mat2(50);
	mat1.set(0,1,9.2);
	double v = mat1.get(1,3);

	bandMatrix mat3 = mat1 + mat1;
	bandMatrix mat4 = mat1 + mat2;
	return 0;
}
