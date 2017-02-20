% hw3.pl
%p1
maxnums(A, B, MAX) :- (A>B -> MAX is A; MAX is B).

sum([], 0).
sum([H|T], Sum) :- sum(T, Sum1), Sum is H + Sum1.

father(mark, kelly).
father(mark, adam).

mother(carol, kelly).
mother(carol, adam).

parent(X, Y) :- father(X, Y).
parent(X, Y) :- mother(X, Y).

female(kelly).
female(carol).
male(adam).
male(mark).

sister(X, Y) :- parent(Z, Y), parent(Z, X), female(Y), X\==Y.

max([M],M).
max([H|L], M) :- max(L, NM), (H > NM -> M is H ; M is NM).

partitionable([H|T]) :- partition([H],T).
partitionable([H]) :- H == 0.
partition([H1|T1], [H2|T2]) :- sum([H1|T1],A), sum([H2|T2],B), A == B.
partition([H1|T1], [H2|T2]) :- append([H1|T1], [H2], S), partition(S, T2).

%p2
getCommon(State1, State2, Place) :- location(_, Place, State1, _,_,_), location(_, Place, State2, _,_,_).
getStateInfo(Placename, State, Zip) :- location(Zip, Placename, State, _,_,_).

%p3
snoun([sun]).
snoun([bus]).
snoun([deer]).
snoun([grass]).
snoun([party]).

pnoun([suns]).
pnoun([buses]).
pnoun([deer]).
pnoun([grasses]).
pnoun([parties]).

article([a]).
%pretty sure this is supposed to be "an", not "and" as it says in the HW document
article([an]).
article([the]).

adverb([loudly]).
adverb([brightly]).

adjective([yellow]).
adjective([big]).
adjective([brown]).
adjective([green]).
adjective([party]).

pverb([shine]).
pverb([continue]).
pverb([party]).
pverb([eat]).

sverb([shines]).
sverb([continues]).
sverb([parties]).
sverb([eats]).

sentence(S) :- append(NPS, VPS, S), nps(NPS), vps(VPS).
sentence(S) :- append(NPP, VPP, S), npp(NPP), vpp(VPP).
%singular
nps([ART|NP]) :- article([ART]), np2s(NP).
nps(NP) :- np2s(NP).

np2s(NP2) :- snoun(NP2).
np2s([ADJ|NP2]) :- adjective([ADJ]), np2s(NP2).

vps(VP) :- sverb(VP).
vps([VERB|ADV]) :- sverb([VERB]), adverb(ADV).

%plural
npp([ART|NP]) :- article([ART]), np2p(NP).
npp(NP) :- np2p(NP).

np2p(NP2) :- pnoun(NP2).
np2p([ADJ|NP2]) :- adjective([ADJ]), np2p(NP2).

vpp(VP) :- pverb(VP).
vpp([VERB|ADV]) :-pverb([VERB]), adverb(ADV).














