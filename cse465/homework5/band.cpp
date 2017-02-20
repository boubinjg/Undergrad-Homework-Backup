#include "band.h"

	band::band(int s) //constructor
	{
		size = s;
		mat = new double[size*3 - 2];
	}
	band::band(const band& b) //copy constructor
	{
		size = b.size;
		mat = new double[size * 3 - 2];
		for(int i = 0; i<size*3 - 2; i++)
			mat[i] = b.mat[i];
	}
	band::band(band &&b) //move constructor
	{
		mat = b.mat;
		b.mat = nullptr;
		
		size = b.size;
		b.size = 0;	
	}
	band& band::operator =(band&& b) //move assignment
	{
		delete [] mat;
		mat = b.mat;
		b.mat = nullptr;
		
		size = b.size;
		b.size = 0;
	}
	band& band::operator = (const band &b) //assignent
	{
		delete mat;
		int *n = new double[b.size*3 - 2];
		std::memcpy(n, b.mat, (sizeof(int)*b.size*3)-2);
		mat = n;
		return *this;
	}
	band& band::operator + (const band &b) //addition
	{
		if(size != b.size)
			throw std::invalid_argument("Matrix Addition Dimension Mismatch");
		
		band* n = new band(size);
		
		for(int i = 0; i<b.size*3 - 2; i++)
			n->mat[i] = mat[i] + b.mat[i];	
		return *n;
	}
	void band::set(int r, int c, int v) //set
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
	double band::get(int r, int c) //get
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
	band::~band() //destructor
	{
		delete [] mat;
	}
