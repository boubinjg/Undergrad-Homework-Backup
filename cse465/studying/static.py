a = 0
b = 1
c = 2
def f1():
	def f1a():
		b = 3;
		print(str(a) + " " + str(b) + " " + str(c))
	
	def f1b():
		b = 4
		print(str(a) + " " + str(b) + " " + str(c))
		f1a()
	a = 5
	b = 6
	print(str(a) + " " + str(b) + " " + str(c))
	f1b()

def f2():
	a = 7;
	c = 8;
	print(str(a) + " " + str(b) + " " + str(c))
	f1()
f1()
f2()
