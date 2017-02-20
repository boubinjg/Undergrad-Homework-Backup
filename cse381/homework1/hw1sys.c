#include <sys/time.h>
#include <stdio.h>
int main()
{
	struct timeval t1, t2;
	int res;
	for(int i = 0; i<100000000; i++)
		res = gettimeofday(&t1, NULL);

}
