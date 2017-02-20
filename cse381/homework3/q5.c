#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define I 4
#define J 3
#define K 3

int first [I][K] = { {1,2,3}, {2,3,4}, {3,4,5},{4,5,6} };
int second [K][J] = { {1,2,3}, {4,5,6}, {7,8,9} };
int result [I][J];

struct rowcol {
   	int i;
   	int j; 
};
void *runner(void *arg)
{
	struct rowcol *rc = arg;
	int sum = 0;
	for(int n = 0; n<K; n++)
		sum +=first[rc->i][n] * second[n][rc->j];
	result[rc->i][rc->j] = sum;
	pthread_exit(0);
}
int main()
{
   	int i,j, count = 0;
   	for(i = 0; i < I; i++) 
      		for(j = 0; j < J; j++) 
		{
         
       			struct rowcol *data = (struct rowcol *) malloc(sizeof(struct rowcol));
         		data->i = i;
         		data->j = j;
         		pthread_t tid;             
         		pthread_create(&tid,NULL,runner,data);
         		pthread_join(tid, NULL);
         		count++;
      		}	
  	for(i = 0; i < I; i++)
	{
      		for(j = 0; j < J; j++) 
         		printf("%d ", result[i][j]);
      		printf("\n");
	}
}	
