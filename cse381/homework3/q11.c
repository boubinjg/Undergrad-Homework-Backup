#define _GNU_SOURCE
#include <pthread.h>
#include <stdio.h>
#include <time.h>
#include <string.h>
int counter = 1;
void check()
{
	pthread_attr_t att;
	void *stackAddress;
	size_t stackSize;
	
	memset (&att, 0, sizeof(att));
	pthread_getattr_np(pthread_self(), &att);
	pthread_attr_getstack(&att, &stackAddress, &stackSize);
	pthread_attr_destroy (&att);

	printf("stack top: %p\n", stackAddress);
	printf("stack size: %zu\n", stackSize);
}
int recurse(int i)
{
	counter++;
	for(int j = 0; j<10000; j++)
	{
		
	}
	if(counter % 100 == 0)
		check();
	return 1 + recurse(i);
}
void *threadtwo(void *arg)
{
	recurse(0);
}
int main()
{
	pthread_t tid[1];
	pthread_create(&tid[0], NULL, threadtwo, NULL);
	pthread_join(tid[0], NULL);
}
