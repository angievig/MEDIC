:-use_module(library(clpfd)).
productline(L):-
L=[ B, D, E, A, C], 
B in 1..3, 
D in 1..2, 
E in 1..3, 
A in 1..3, 
C in 1..2, 
A#= 1 #==> D#\=1, 
C#=1  #==> E#=1, 
E#\= 1, 
B#= 1 #==> C#\=2, 
A#=1, 
A#= 1 #==> B#=1, 
A#= 2 #==> B#=2, 
labeling([ff],L).