This is the README file for the first algorithms project
Written by Jayson Boubin

A makefile is included in this package to generate the appropriate executables.

The C++ file in this package was written and compiled for the g++ 4.8.4 ubuntu version

To run this program, the makefile generates the proj1 executable. This executable
takes 3 arguments: sorting method, file to sort, and print option

Sorting method: takes either "mergesort" or "selectionsort" and will sort using the 
corresponding algorithm.

file to sort: accepts a file name in the form of a text document which is to be sorted. 
I will provide files which can be sorted using this program.

print option: takes either "print" to print the sorted vector, or any other string to not print.
Some files are quite large, and therefore should not be printed, especially when program timing
is being examined. 


Example execution: ./proj1 mergesort thirtyThousand print 

This execution would use the file thirtyThousand (which contains thirty thousand random integers)
and will sort the vector using mergesort and print the vector using std::cout. 

Other included files:

proj1.cpp: the file being compiled into the proj1 object file using the makefile.

makefile: makes the executable, takes no arguments

various txt files: these txt files hold a number of integers corresponding to their name.
for example, million.txt holds one million unsorted random integers. These files are to be
provided as arguments to the proj1 executible for sorting. 
