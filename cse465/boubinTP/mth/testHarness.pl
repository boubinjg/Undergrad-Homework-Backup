query(mnCousins(c, c2, 4, 4)).
query(mnCousins(c, b2, 2, 2)).
query(mnCousins(c, c, 0, 0)).

writeln(T) :- write(T), nl.

main :- consult(mth),
	forall(query(Q), (Q->writeln(yes:Q) ; writeln(no:Q))),
	halt.
:- initialization(main).
