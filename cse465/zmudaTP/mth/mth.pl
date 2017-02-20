parent(a,b1).
parent(b1,c).
parent(c,d).

parent(b2,c2).
parent(c2,d2).

mnCousins(A, B, M, N) :- parent(X, Y1), parent(Y1, A), 
					parent(X, Y2), parent(Y2, B), M=N.
