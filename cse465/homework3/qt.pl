%qt
eo(L,[],L).
eo([],L,L).
eo([H1|T1], [H2|T2], [H1,H2|T]) :- eo(T1, T2, T).

enigma2(A, [A|_]) :- !.
enigma2(A, [_|T]) :- enigma2(A,T).

enigma([A,B|_], [A|T2]) :- enigma2(B,T2), !.
enigma([A,B|T1], [_|T2])  :- enigma([A,B|T1], T2).
