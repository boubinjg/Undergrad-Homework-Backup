// Copyright (C) 2016 ?????

#include <iostream>
#include <algorithm>
#include <string>
#include <cctype>

void bubbleup(std::string& s, int pos, int end) {
    for (int i = pos; i > end; i--) {
        // (s.at(i)).swap(s.at(i-1));
        std::swap(s[i], s[i-1]);
    }
}

void bubbleback(std::string& s, int pos, int end) {
    for (int i = pos; i < end-1; i++) {
        // (s.at(i)).swap(s.at(i+1));
        std::swap(s[i], s[i+1]);
    }
}

void gatherDigits(std::string& s, const int pos) {
    int store = 0;
    for (int i = s.length() - 1; i >= 0; i--) {
        if (std::isdigit(s[i])) {
            bubbleback(s, i, s.length() - store);
            store++;
        }
    }
    // What does it look like here? We're good at this point.
    // Now all digits should be at the end in order.
    int shift = 0;    
    for (unsigned int j = s.length() - store; j < s.length(); j++) {
        for (int k = j; k > pos + shift; k--) {
            if (std::isdigit(s[k])) {
                bubbleup(s, k, pos + shift);
                shift++;
            }
        }
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
