#include <sys/time.h>
#include <stdio.h>
#include <unistd.h>

int procedure()
{
	return (1);
}
int convert(struct timeval t)
{
	 return((t.tv_sec*1000000+t.tv_usec)*1000);
}
int main()
{
	struct timeval t1, t2;
	int res, i, j, million = 1000000;
	res = gettimeofday(&t1, NULL); 
	for(i=0; i<million; i++)
	{
		j = getpid();
	}
	res = gettimeofday(&t2, NULL);
	int systime = (convert(t2)-convert(t1))/million;
	printf("average nanosecond execution for systemcall: %d\n",systime);

	res = gettimeofday(&t1,NULL);
	for(i = 0; i<million; i++)
	{
		j = procedure();
	}
	res = gettimeofday(&t2, NULL);
	systime = (convert(t2)-convert(t1))/million;
	printf("average nanosecond execution for procedure: %d\n",systime);
}
