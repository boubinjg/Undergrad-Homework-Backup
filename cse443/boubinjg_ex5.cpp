// Copyright (C) 2016 ????

// A C++ program that uses std::vector along with algorithms to
// perform various operations on a given list of numbers.

#include <iostream>
#include <algorithm>
#include <functional>
#include <iterator>
#include <numeric>
#include <vector>
#include <cassert>



// A shortcut for std::vector<int>
using IntVec = std::vector<int>;

// Forward prototype declaration to help compiler resolve method calls
int getFactorCount(const int num);

/** Method to create a vector of given size and filled with numbers
    1..size.

    This 3 line method create a vector of given size (using suitable
    constructor) and replaces the default values (of zeros) with
    numbers 1..size using std::iota method.

    \param[in] size The size of the vector.

    \return A vector of given size and filled with numbers 1..size,
    i.e., {1, 2, 3, ..., size}.
*/

IntVec createVector(const int size) {
    // Implement this method using the information in the doxygen
    // comments above.
    IntVec list(size);
    std::iota(list.begin(), list.end(), 1);
    return list;
}

/** Method to sort the given vector such that numbers with highest
    number of prime factors are first in the list.

    This 2 to 3 line method suitably (via a lambda) uses
    getFactorCount() method and std::sort to order the numbers in the
    given vector.  The numbers are sorted such that the number with
    largest number of prime factors is first in the list.
  
    \param[in,out] vec The vector to be sorted.  
*/
void sortByFactorCount(IntVec& vec) {
    // Implement this method using the information in the doxygen
    // comments above.
    auto fac = [](int i, int j){return getFactorCount(i)>getFactorCount(j);};
    return std::sort(vec.begin(),vec.end(),fac);
    
}

/** Method to extract the list of prime numbers in the given vector.

    This 3 to 4 line method uses the getFactorCount() method along
    with std::copy_if algorithm to return a vector that contains the
    prime numbers in the given vector.  Recollect that prime numbers
    have extactly 2 factors (namely 1 and the number itself).  For
    example, if the input vector is {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
    then this method returns the vector {2, 3, 5, 7}.
    
    Tip: The method needs to use std::back_inserter to push_back prime
    numbers onto the returned vector.

    \param[in] vec The vector from which prime numbers are to be
    obtained.

    \return A vector with the list of prime numbers in the given
    vector vec.
*/
IntVec getPrimes(const IntVec& vec) {
    // Implement this method using the information in the doxygen
    // comments above.
    IntVec ret(0);
    std::copy_if(vec.begin(),vec.end(),
                 std::back_inserter(ret),
                 [](int x){return getFactorCount(x) == 1;});
    return ret;
}

/** Method to return numbers in vec with their sign negated.

    This 3 line method uses the std::transform algorithm to create and
    return a new vector which contains negated values from vec.  For
    example, if the parameter vector has the values {-1, 2, -3, 4, 5}
    then this method returns a vector: {1, -2, 3, -4, -5}.

    \param[in] vec The vector whose numbers are to be negated.

    \return Vector with values in vec negated.
*/
IntVec negate(const IntVec& vec) {
    // Implement this method using the information in the doxygen
    // comments above.
    IntVec ret(vec.size());
    auto neg = [](int x){return -x;};
    std::transform(vec.begin(), vec.end(), ret.begin(), neg);
    return ret;
}

//----------------------------------------------------
//  DO  NOT  MODIFY  CODE  BELOW  THIS  LINE 
//----------------------------------------------------

int getFactorCount(const int num) {
    const int maxFactor = std::sqrt(num) + 1;
    int factors = 0;
    for (int i = 1; (i < maxFactor); i++) {
        if (num % i == 0) {
            factors++;  // i is a factor of num
        }
    }
    return factors;
}

std::ostream& operator<<(std::ostream& os, const IntVec& vec) {
    std::copy(vec.begin(), vec.end(),
              std::ostream_iterator<IntVec::value_type>(std::cout, " "));
    return os;
}

int main(int argc, char *argv[]) {
    const int size = ((argc > 1) ? std::stoi(argv[1]) : 10);
    // Test the createVector method.
    IntVec vec = createVector(size);
    std::cout << "Initial vector : " << vec << std::endl;
    // Test the sortByFactorCount
    sortByFactorCount(vec);
    std::cout << "Sorted vector  : " << vec << std::endl;
    // Test prime extraction
    std::cout << "Prime numbers  : " << getPrimes(vec) << std::endl;
    // Test negation method
    std::cout << "Negated numbers: " << negate(vec) << std::endl;
    return 0;
}

// End of source code


