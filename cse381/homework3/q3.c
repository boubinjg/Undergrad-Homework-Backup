#include <pthread.h>
#include <stdio.h>
#include <time.h>
void *threadfunc(void *arg)
{
	pthread_exit(0);
}
int main()
{
	clock_t begin = clock();
	pthread_t tid[1000];
	for(int i = 0; i<1000; i++)
	{
		pthread_create(&tid[i], NULL, threadfunc, NULL);
	}
	for(int i = 0; i<1000; i++)
	{
		pthread_join(tid[i], NULL);
	}
	clock_t end = clock();
	double time_spent = (double)(end-begin) /CLOCKS_PER_SEC;
	printf("%f", time_spent);
}
