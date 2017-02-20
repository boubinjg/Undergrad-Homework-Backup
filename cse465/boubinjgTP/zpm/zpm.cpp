#include <iostream>
#include <string>
#include <algorithm>
#include <vector>
#include <math.h>
#include <limits>
#include <set>
#include <fstream>
#include <sstream>
#include <regex> 
#include <unordered_map>
struct VAR {
	int ival;
	std::string sval;
	bool isInt;
	bool isString;
};
std::regex variable("[a-zA-Z.*]");
std::unordered_map<std::string, VAR> variables;
int linen;
void concat(std::string var,std::string varval, std::string s, std::string manip)
{
	varval.erase(varval.size()-1);
	s.erase(s.begin());
	if(manip != "+=")
	{
		std::cout<<"ILLEGAL STRING OPERATION: Line "<<linen<<std::endl;
		abort();
	}
	else
	{
		VAR v;
		v.isString = true;
		v.isInt = false;
		v.sval = varval + s;
		variables.erase(variables.find(var));
		variables.insert({var,v});
	}
}
void manipInt(int i, int j, std::string s, std::string manip)
{
	VAR v;
	v.isInt = true;
	v.isString = false;
	if(manip == "+=")
		i += j;
	if(manip == "-=")
		i -= j;
	if(manip == "*=")
		i *= j;
	v.ival = i;
	variables.erase(variables.find(s));
	variables.insert({s,v});
}
void insertString(std::string in, std::string pos)
{
	VAR v;
	v.sval = in;
	v.isString = true;
	v.isInt = false;
	variables.erase(variables.find(pos));
	variables.insert({pos,v});
}
void insertInt(int i, std::string s)
{
	VAR v;
	v.ival = i;
	v.isString = false;
	v.isInt = true;
	variables.erase(variables.find(s));
	variables.insert({s,v});
}
void print(std::vector<std::string> tokens)
{
	std::unordered_map<std::string, VAR>::const_iterator pos = variables.find(tokens[1]);
	if(pos->second.isInt)
		std::cout<<pos->first<<" = "<<pos->second.ival<<std::endl;
	else
	{
		std::string s = pos->second.sval;
		s.erase(s.begin());
		s.erase(s.size()-1);
		std::cout<<pos->first<<" = "<<s<<std::endl;
	}
}
void assign(std::vector<std::string> tokens)
{
	//std::unordered_map<std::string, VAR>::const_iterator pos = variables.find(tokens[0]);
	//declare new variable
	VAR pos = variables[tokens[0]];
	bool exists = true;
	if(!pos.isString && !pos.isInt)
		exists = false;
	if(tokens.size() > 4)
	{
		for(int i = 3; i<tokens.size()-1; i++)
			tokens[2] += " " + tokens[i];
	}
	if(!exists && tokens[1] == "=")
	{
		std::string s = tokens[2];
		if(s[0] == '\"')
		{	
			VAR v;
			v.isString = true;
			v.isInt = false;
			v.sval = tokens[2];
			variables.insert({tokens[0],v});
		}
		else
		{
			VAR v;
			v.isInt = true;
			v.isString = false;
			if(regex_match(tokens[2], variable))
			{
				VAR pos = variables[tokens[2]];
				if(!exists)
					std::cout<<"VARIABLE REFERENCED BEFORE ASSIGNMENT"<<std::endl;
				else
					v.ival = pos.ival;
			}
			else
				v.ival = stoi(tokens[2]);
			variables.insert({tokens[0],v});
		}
	}
	//assign value to existinc variable
	else if(exists && tokens[1] == "=")
	{
		//assigning var
		if(regex_match(tokens[2],variable))
		{
			VAR vpos = variables[tokens[2]];
			if(!vpos.isInt && !vpos.isString)
			{
				std::cout<<"VARIABLE REFERENCED BEFORE INIT: Line "<<linen<<std::endl;
				abort();
			}
			else if(vpos.isInt)
				insertInt(vpos.ival, tokens[0]);	
			else
				insertString(vpos.sval, tokens[0]);
		}
		//assigning literal
		else
		{
			if(tokens[2][0] == '\"')
				insertString(tokens[2], tokens[0]);
			else
				insertInt(std::stoi(tokens[2]), tokens[0]);
		}
	}
	//perform arithmetic or concatenation
	else if(exists && tokens[1].size() == 2)
	{
		if(regex_match(tokens[2],variable))
		{
			VAR vpos = variables[tokens[2]];
			if(!vpos.isInt && !vpos.isString)
			{
				std::cout<<"VARIABLE REFERENCED BEFORE INIT: Line "<<linen<<std::endl;
				abort();
			}
			else if(tokens[0] == tokens[2])
			{
				if(pos.isInt && vpos.isInt)
					manipInt(pos.ival, pos.ival, tokens[0],tokens[1]);
				else if(pos.isString && vpos.isString)
					concat(tokens[0], pos.sval, pos.sval, tokens[1]);
				else
				{
					std::cout<<"TYPE MISMATCH: Line "<<linen<<std::endl;
					abort();
				}
			}
			else
			{
				if(pos.isInt && vpos.isInt)
					manipInt(pos.ival, vpos.ival, tokens[0],tokens[1]);
				else if(pos.isString && vpos.isString)
					concat(tokens[0], pos.sval, vpos.sval, tokens[1]);
				else
				{
					std::cout<<"TYPE MISMATCH: Line "<<linen<<std::endl;
					abort();
				}
			}
		}
		else
		{

			if(pos.isInt && tokens[2][0] != '\"')
				manipInt(pos.ival, stoi(tokens[2]), tokens[0], tokens[1]);
			else if(pos.isString && tokens[2][0] == '\"')
				concat(tokens[0], pos.sval, tokens[2], tokens[1]);
			else
			{
				std::cout<<"TYPE MISMATCH: Line "<<linen<<std::endl;
				abort();
			}
		}
	}
	
}
void forloop(std::vector<std::string> tokens)
{
	int count = stoi(tokens[1]);
	for(int i = 0; i<count; i++)
	{
		for(int j = 2; j<tokens.size()-1 ;)
		{
			if(tokens[j] == "FOR")
			{
				std::vector<std::string> newtokens;
				for(int k = j; tokens[k-1] != "ENDFOR"; k++)
				{
					newtokens.push_back(tokens[k]);
					j++;
				}
				forloop(newtokens);
			}
			else if(tokens[j] == "PRINT")
			{
				std::vector<std::string> newtokens;
				newtokens.push_back(tokens[j]);
				newtokens.push_back(tokens[j+1]);
				print(newtokens);
				j += 3;
			}
			else
			{
				std::vector<std::string> newtokens;
				newtokens.push_back(tokens[j]);
				newtokens.push_back(tokens[j+1]);
				newtokens.push_back(tokens[j+2]);
				assign(newtokens);
				j += 4;
			}
		}
	}
}
int main(int argc, char* argv[])
{
	if(argc < 2)
	{
		std::cerr << "Please provide a zpm file, EX: prog1.zpm"<<std::endl;
		abort();
	}
	std::string line;
	std::string fname(argv[1]);
	std::ifstream infile(fname);
	std::vector<std::string> tokens;
	line = 1;
	while(std::getline(infile, line))
	{
		tokens.clear();
		std::istringstream ss(line);
		std::string token;
		while(std::getline(ss,token,' '))
			tokens.push_back(token);

		if(tokens[0] == "PRINT")
			print(tokens);
		else if(std::regex_match(tokens[0],variable))
			assign(tokens);
		else if(tokens[0] == "FOR")
			forloop(tokens);
		++linen;
	}
	return 0;
}

