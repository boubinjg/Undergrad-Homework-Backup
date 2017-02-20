#include <algorithm>
#include <vector>
#include <iostream>
#include <string>
inline bool comp(int i, int j)
{
	return j%10>i%10;
}
void compareLambda()
{
	std::vector<int> v(2,1);
	auto compare = [](int i, int j){return j%10>i%10;};
	for(int i = 0; i<1000000000; i++)
	{
		std::sort(v.begin(),v.end(),compare);
		v[0]+=5;
		v[1]-=5;
	}	
	std::cout<<v[0]<<" "<<v[1]<<std::endl;
}
void compareFunct()
{
	std::vector<int> v(2,1);
	for(int i = 0; i<1000000000; i++)
        {
                std::sort(v.begin(),v.end(),comp);
                v[0]+=5;
                v[1]-=5;
        }
        std::cout<<v[0]<<" "<<v[1]<<std::endl;
}
int main(int argc, char* argv[])
{
	std::string s = argv[1];
	if(s == "lambda")
	{	
		std::cout<<"lambda"<<std::endl;
		compareLambda();
	}
	else
	{
		std::cout<<"inline"<<std::endl;
		compareFunct();
	}
}
