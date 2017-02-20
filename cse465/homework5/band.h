#ifndef _band_H_
#define _band_H_
#include<iostream>
#include <stdexcept>
#include <cstring>
class bandMatrix{
public:
	double* mat;
	int size;
	bandMatrix(int s); //constructor
	bandMatrix(const bandMatrix& b); //copy constructor
	bandMatrix(bandMatrix &&b);
	bandMatrix& operator =(band &&b);
	bandMatrix& operator =(const bandMatrix &b); //assignent
	bandMatrix& operator +(const bandMatrix &b); //addition
	void set(int r, int c, int v); //set
	int get(int r, int c); //get
	~bandMatrix(); //destructor
};
#endif
