# MasterThesisSudoku
Master Thesis at IT University Copenhagen

How to run:

Code can be run in an IDE by running the main method in Pilot
If run in a command prompt run with java -cp ".;sat4j-sat.jar" Pilot.java to run with the imported sat4j library

Within the project:

The project consists of a Pilot used for running various sections of the code.
Remaining code is divided into following sections:

I/O:
- InputGenerator	Generate Sudoku instances
- InputReader		Reads Sudoku instance from file
- OutputGenerator	Outputs Sudoku instances
- SudokuMap			Translates from ASCII characters to numbers
- SudokuReverser	Reverse engineers completed grids nondeterministically
- Puzzle			Puzzle class to hold int array with number of clues

Solvers:
- Naive				Naive depth-first backtracking solver

- CNFEncoder		Translates a Sudoku to CNF-SAT
	- SATSolver		Runs an instance of CNF-SAT
	
- Dancing Links		Creates the Cover Matrix and constructs puzzle from DLX solution
	- DancingNode	Class for Dancing Nodes used by Dancing Links and DLX
	- ColumnNode	Class for Column Nodes used by Dancing Links and DLX
	- DLX			Solves instances of Sudoku

Library:
- sat4j-sat

Data:
- Generated Data	Data generated with the InputGenerator
- Experiment Data	Benchmarks of solved instances
- Scrapped Data		Sudoku found online with additional added clues