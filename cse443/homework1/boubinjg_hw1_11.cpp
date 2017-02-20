#include <iostream>
using namespace std;

int main()
{
	cout << "please enter a month: ";
	std::string month;
	std::cin >> month;

	cout<< "Month: ";
	if(month == "january")
		cout << "1\n";
	else if(month == "february")
		cout << "2\n";
	else if(month == "march")
                cout << "3\n";
	else if(month == "april")
                cout << "4\n";
	else if(month == "may")
                cout << "5\n";
	else if(month == "june")
                cout << "6\n";
	else if(month == "july")
                cout << "7\n";
	else if(month == "august")
                cout << "8\n";
	else if(month == "september")
                cout << "9\n";
	else if(month == "october")
                cout << "10\n";
	else if(month == "november")
                cout << "11\n";
	else if(month == "december")
                cout << "12\n";
	return 0;
}
