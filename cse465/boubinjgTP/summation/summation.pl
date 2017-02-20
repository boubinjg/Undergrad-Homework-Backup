% summation.pl

sumList([], 0).
sumList([H|T], Sum) :- sumList(T, Sum1), Sum is H + Sum1.

powerset([],[]).
powerset([H|T], P) :- powerset(T,P).
powerset([H|T], [H|P]) :- powerset(T,P).

sumAll([H,T]) :- sumList(H).

sum([H|T],X):- powerset([H|T], [H1|T1]), sumList([H1|T1], Z), Z==X.
