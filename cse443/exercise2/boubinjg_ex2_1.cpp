// Copyright 2016 
// Jayson Boubin

#include <string>
#include <iostream>
using namespace std;
int main() {
std::string line = "";
cout << "enter a line: " << endl;
getline(cin, line);
cout << "length of line: " << line.length() << endl;
cout << "first character: " << line.front() << endl;
cout << "last character: " << line.back() << endl;
cout << "first word: " << line.substr(0 , line.find(" ")) << endl;
if(line.rfind(" ") != string::npos)
cout << "last word: " << line.substr(line.rfind(" ") , line.length()) << endl;
else
cout << "only one word" << endl;
return 0;
}
