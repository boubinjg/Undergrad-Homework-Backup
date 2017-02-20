#include <pthread.h>
#include <stdio.h>
#include <string.h>
#define _GNU_SOURCE
void PrintStackInfo (void)
{   
   pthread_attr_t Attributes;
   void *StackAddress;
   int StackSize;

   // Get the pthread attributes
   memset (&Attributes, 0, sizeof (Attributes));
   pthread_getattr_np (pthread_self(), &Attributes);

   // From the attributes, get the stack info
   pthread_attr_getstack (&Attributes, &StackAddress, &StackSize);

   // Done with the attributes
   pthread_attr_destroy (&Attributes);

   printf ("Stack top:     %p\n", StackAddress);
   printf ("Stack size:    %u bytes\n", StackSize);
   printf ("Stack bottom:  %p\n", StackAddress + StackSize);
}
int main()
{
	pthread_t tid;
	pthread_create(&tid, NULL, PrintStackInfo, NULL);
}


