#include "bandMatrix.h"

	bandMatrix::bandMatrix(int s) //constructor
	{
		size = s;
		mat = new double[size*3 - 2];
	}
	bandMatrix::bandMatrix(const bandMatrix& b) //copy constructor
	{
		size = b.size;
		mat = new double[size * 3 - 2];
		for(int i = 0; i<size*3 - 2; i++)
			mat[i] = b.mat[i];
	}
	bandMatrix::bandMatrix(bandMatrix &&b) //move constructor
	{
		mat = b.mat;
		b.mat = nullptr;
		
		size = b.size;
		b.size = 0;	
	}
	bandMatrix& bandMatrix::operator =(bandMatrix&& b) //move assignment
	{
		delete [] mat;
		mat = b.mat;
		b.mat = nullptr;
		
		size = b.size;
		b.size = 0;
	}
	bandMatrix& bandMatrix::operator = (const bandMatrix &b) //assignent
	{
		delete [] mat;
		double *n = new double[b.size*3 - 2];
		//std::memcpy(n, b.mat, (8*b.size*3)-2);
		for(int i = 0; i<size*3 - 2; i++)
		{
			n[i] = b.mat[i];
		}
		mat = n;
		return *this;
	}
	bandMatrix& bandMatrix::operator + (const bandMatrix &b) //addition
	{
		if(size != b.size)
			throw std::invalid_argument("Matrix Addition Dimension Mismatch");
		
		bandMatrix* n = new bandMatrix(size);
		
		for(int i = 0; i<b.size*3 - 2; i++)
			n->mat[i] = mat[i] + b.mat[i];	
		return *n;
	}
	void bandMatrix::set(int r, int c, int v) //set
	{
		if(r < 0 || r >= size || c<0 || c >= size)
			throw std::invalid_argument("received invalid value");
		if(r == c)
			mat[r] = v;
		else if(r+1 == c)
		{
			  mat[size+c-1] = v;
		}
		else if(r-1 == c)
		{
			mat[2*size + c-1] = v;
		}
	}
	double bandMatrix::get(int r, int c) //get
	{
		if(r < 0 || r >= size || c<0 || c >= size)
			throw std::invalid_argument("received invalid value");
		if(r == c)
			return mat[r];
		else if(r+1 == c)
		{
			  return mat[size+c-1];
		}
		else if(r-1 == c)
		{
			return mat[2*size + c-1];
		}
		return 0;
	}
	bandMatrix::~bandMatrix() //destructor
	{
		delete [] mat;
	}
