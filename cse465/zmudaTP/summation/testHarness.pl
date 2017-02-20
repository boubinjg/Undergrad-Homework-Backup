query(summation([2,3,4,5], 7)).
query(summation([2,3,4,5], 6)).
query(summation([2,3,4,5], 5)).

writeln(T) :- write(T), nl.

main :- consult(summation),
	forall(query(Q), (Q->writeln(yes:Q) ; writeln(no:Q))),
	halt.
:- initialization(main).
