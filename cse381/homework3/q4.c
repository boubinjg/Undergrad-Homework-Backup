#include <pthread.h>
#include <stdio.h>
#include <time.h>
int counter = 0;
void *threadone(void *arg)
{
	int count = 0;
	while(counter<100000000)
	{
		counter+=1;
		printf(".");
	}
}
void *threadtwo(void *arg)
{
	int a;
	while(counter < 100000000)
	{
		if(counter %10000000 == 0)
		{
			scanf("%d", &a);
			printf("thanks for the input");
		}
	}
}
int main()
{
	pthread_t tid[2];
	pthread_create(&tid[0], NULL, threadone, NULL);
	pthread_create(&tid[1], NULL, threadtwo, NULL);
	for(int i = 0; i<2; i++)
	{
		pthread_join(tid[i], NULL);
	}
}
