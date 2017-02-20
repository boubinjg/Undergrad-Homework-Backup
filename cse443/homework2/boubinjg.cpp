// Copyright (C) 2016 ?????

#include <iostream>
#include <algorithm>
#include <string>
#include <cctype>

void gatherDigits(std::string& s, const int pos) {
	int digits = 0;
		for(int i = s.length()-1; i >= 0; i--) {
			if(std::isdigit(s.at(i))) {
				for(unsigned int j = i; j < s.length()-digits-1; j++) {
					std::swap(s.at(j), s.at(j+1));
				}
				digits++;
			}
		}
	int curpos = pos;
	for(unsigned int k = s.length()-digits; k < s.length(); k++) {
		for(int l = k; l > curpos; l--) {
			std::swap(s.at(l), s.at(l-1));
		}
		curpos++;
	}
}

// --------------------------------------------------------------
//        DO  NOT  MODIFY  CODE  BELOW  THIS  LINE
// --------------------------------------------------------------

int main() {
    std::string str;
    int pos = 0;
    do {
        std::cout << "Enter gather position (-1 to quit) and string:\n";
        std::cin >> pos;
        if (pos != -1) {
            std::cin >> str;
            gatherDigits(str, pos);
            std::cout << "Gathered @ " << pos << ": " << str << std::endl;
        }
    } while (pos != -1);
    return 0;
}
