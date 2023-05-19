:- use_module(library(clpfd)).

solve :-
    % Open the file for reading
    see('eightQueens_PrologInput.txt'),
    % Read the term from the stream
    read(Qs),
    % Close the file
    seen,
    % Open the file for writing
    tell('eightQueens_PrologOutput.txt'),
    % Call solve with the input Qs
    (   length(Qs, 8),
        Qs ins 1..8,
        safe_queens(Qs),
        findall(Qs, label(Qs), Solutions),
        once(label(Qs))
    ->  write(Qs)
    ;   write('false')
    ),
    % Close the file
    told,
write_solutions(Solutions).
write_solutions([]).
write_solutions([Solution|Solutions]) :-
    write(Solution), nl,
    write_solutions(Solutions).
safe_queens([]).
safe_queens([Q|Qs]) :- safe_queens(Qs, Q, 1), safe_queens(Qs).
safe_queens([], _, _).
safe_queens([Q|Qs], Q0, D0) :-
        Q0 #\= Q,
        abs(Q0 - Q) #\= D0,
        D1 #= D0 + 1,
        safe_queens(Qs, Q0, D1).