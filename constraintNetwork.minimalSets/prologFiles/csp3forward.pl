:-use_module(library(clpfd)).
productline(L):-
L=[ C, E, A, B, D], 
C in 1..2, 
E in 1..3, 
A in 1..3, 
B in 1..3, 
D in 1..2, 
A#=1, 
A#= 2 #==> B#=2, 
A#= 1 #==> D#\=1, 
A#= 1 #==> B#=1, 
labeling([ff],L).