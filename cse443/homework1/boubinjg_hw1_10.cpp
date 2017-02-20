#include <iostream>
using namespace std;

int main()
{
	int one, two, three;
	cout << "please enter three integers: ";
	cin >> one;
	cin >> two;
	cin >> three;

	cout << "smallest: " << std::min(std::min(one,two),three) << "\n";
	cout << "largest:  " << std::max(std::max(one,two),three) << "\n";
	return 0;
}
