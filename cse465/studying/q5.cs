using System;
using System.IO;
namespace test{
class t{
public static void f(int a, ref int b, out int c)
{
	c = 0;
	a++; b++; c++;
}
static void Main(String [] args)
{
	int x = 1, y = 2, z = 3;
	f(x, ref y, out z);
	Console.WriteLine("{0} {1} {2]", x, y, z);

	int m = 1;
	f(m, ref m, out m);
	Console.WriteLine("{0}", m);
}
}
}
