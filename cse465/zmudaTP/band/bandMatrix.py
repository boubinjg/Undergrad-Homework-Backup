from copy import deepcopy
#TODO: Move, Move assign overload, assignment overload

class bandMatrix(object):
	mat = [];
	size = 0;
	#python does not allow for overloading, so all the constructors have to be done here
	def __init__(self, s):
		if type(s) is bandMatrix:
			self.mat = deepcopy(s.mat)
			self.size = deepcopy(s.size)
		else:
			self.mat = [0]*(s*3-2)
			self.size = s
	def __add__(self, other):
		if(self.size != other.size):
			print("Can't add Matrices of Different Sizes")
		else:
			for i in range(0, len(self.mat)):
				self.mat[i] += other.mat[i]
		return self;
	def set(self, r, c, v):
		if r<0 or r>=self.size or c<0 or c>=self.size:
			print("Invalid Value");
		if r == c:
			self.mat[r] = v
		elif r+1 == c:
			self.mat[self.size+c-1] = v
		elif r-1 == c:
			self.mat[2*self.size+c-1] = v;
	def get(self, r, c):
		if r<0 or r>=self.size or c<0 or c>=self.size:
			raise Exception("Invalid Value")
		if r == c:
			return self.mat[r]
		elif r+1 == c:
			return self.mat[size+c-1]
		elif r-1 == c:
			return self.mat[2*size+c-1]

def main():
	b = bandMatrix(5)
	a = bandMatrix(5)
	a.set(3,3,4)
	b.set(3,3,5)
	print(a.get(3,3))
	print(b.get(3,3))
	a = a+b
	print(a.get(3,3))
	a = bandMatrix(b)
	print(a.get(3,3))
	a = b;
	b.set(2,2,5)
	print(a.get(2,2))
	
if __name__ == "__main__": main()

