# SudokuSolver
A java program that takes an input of 81 numbers that resemble the starting position of a Sudoku puzzle. The program then generates a tree data structure with all legal possibilities from the original board. The program then performs an iterative DFS to find the correct board and prints this board to console.

The program does not check if the starting board cannot be completed, but it does check to make sure that 81 numbers were input. 


# Problems & Solutions
When I first created this application, I used a recursive DFS to find the correct board in the tree data structure. This would work for some starting Sudoku boards, but not for the harder Sudoku boards that created larger trees, as the recursion through the tree would produce a Stack Overflow error. After troubleshooting, I changed this from a recursive DFS to an iterative DFS, which solved the problem.
