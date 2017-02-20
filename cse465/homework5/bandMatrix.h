#ifndef _bandMatrix_H_
#define _bandMatrix_H_
#include<iostream>
#include <stdexcept>
#include <cstring>
#include <memory>
class bandMatrix{
public:
	double* mat;
	int size;
	bandMatrix(int s); //constructor
	bandMatrix(const bandMatrix& b); //copy constructor
	bandMatrix(bandMatrix &&b);
	bandMatrix& operator =(bandMatrix &&b);
	bandMatrix& operator =(const bandMatrix &b); //assignent
	bandMatrix& operator +(const bandMatrix &b); //addition
	void set(int r, int c, int v); //set
	double get(int r, int c); //get
	~bandMatrix(); //destructor
};
#endif
