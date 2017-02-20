/**
 * threadpool.c
 *
 * This file will contain your implementation of a threadpool.
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

#include "threadpool.h"

// _threadpool is the internal threadpool structure that is
// cast to type "threadpool" before it given out to callers
typedef struct job{
  int active;
  dispatch_fn fn;
  void* arg;
} job;

typedef struct _threadpool_st {
   // you should fill in this structure with whatever you need
   int numThreads;
   int numDispatched;
   pthread_t* threads;
   pthread_cond_t jobAvailable;
   pthread_cond_t jobInProgress;
   pthread_mutex_t lock;
   job* j;   
   int kill;
 
} _threadpool;

void *go(void* arg)
{
	_threadpool *pool = (_threadpool *)arg;
	job curJob;

	pthread_mutex_lock(&(pool->lock));

	while(1)
	{
		while(!(pool->j->active))
			pthread_cond_wait(&(pool->jobAvailable), &(pool->lock));
		if(pool->kill)
			break;
                pthread_cond_signal(&(pool->jobInProgress));
	
                pthread_mutex_unlock(&(pool->lock));
		
		pool->j->active = 0;
		pool->j->fn(pool->j->arg);
		
		
		pthread_mutex_lock(&(pool->lock));
	}
        pool->numDispatched--;
        pthread_cond_signal(&pool->jobInProgress);
        pthread_mutex_unlock(&(pool->lock));
}
threadpool create_threadpool(int num_threads_in_pool) {
  _threadpool *pool;
 
  // sanity check the argument
  if ((num_threads_in_pool <= 0) || (num_threads_in_pool > MAXT_IN_POOL))
    return NULL;

  pool = (_threadpool *) malloc(sizeof(_threadpool));
  if (pool == NULL) {
    fprintf(stderr, "Out of memory creating a new threadpool!\n");
    return NULL;
  }
  
  // add your code here to initialize the newly created threadpool
  pool->numThreads = num_threads_in_pool;
  pool->numDispatched = 0;
  pool->threads = malloc(sizeof(pthread_t)*num_threads_in_pool);
  pool->j = malloc(sizeof(job));
  pool->kill = 0;
  pthread_cond_init(&(pool->jobAvailable), NULL);
  pthread_cond_init(&(pool->jobInProgress), NULL);
  pthread_mutex_init(&(pool->lock), NULL);

  for(int i = 0; i<num_threads_in_pool; i++)
  {
	pthread_mutex_lock(&(pool->lock));
	pthread_create(&(pool->threads[i]), NULL, go, (void *) pool);
	pthread_mutex_unlock(&(pool->lock));
  }
  return (threadpool) pool;
}


void dispatch(threadpool from_me, dispatch_fn dispatch_to_here,
	      void *arg) {
  _threadpool *pool = (_threadpool *) from_me;
  
  pthread_mutex_lock(&(pool->lock));

  while(pool->numThreads == pool->numDispatched)
  {
    pthread_cond_signal(&(pool->jobAvailable));
    pthread_cond_wait(&(pool->jobInProgress), &(pool->lock));
  } 
  while(pool->j->active)
  {
    pthread_cond_signal(&(pool->jobAvailable));
    pthread_cond_wait(&(pool->jobInProgress), &(pool->lock));
  }
  pool->j->fn = dispatch_to_here;
  pool->j->arg = arg;
  pool->j->active = 1;
  pthread_cond_signal(&(pool->jobInProgress));
  pthread_mutex_unlock(&(pool->lock));
}

void destroy_threadpool(threadpool destroyme) {
  _threadpool *pool = (_threadpool *) destroyme;
  
  // add your code here to kill a threadpool
  pthread_mutex_lock(&(pool->lock));
  pool->kill = 1;
  while(pool->numDispatched != 0)
  {
    pthread_cond_signal(&(pool->jobAvailable));
    pthread_cond_wait(&(pool->jobInProgress),&(pool->lock)); 
  }
  pthread_mutex_unlock(&(pool->lock));
  free(pool->j);
  free(pool->threads);
  pthread_cond_destroy(&(pool->jobAvailable));
  pthread_cond_destroy(&(pool->jobInProgress));
  pthread_mutex_destroy(&(pool->lock));

  free(pool);
}

