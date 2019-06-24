package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Games {
	
	
	//Instance variables for each game board variation
	int[] board;
	Games parent;
	List<Games> children;
	
	//Instance variables for finding the first zero value in each game board
	//and the row of the firstZero value
	int firstZero;
	int row;
	
	//Instance variables for all values 1-9 and potential moves for each
	//game board
	List<Integer> moves = Arrays.asList(-1, 1, 2, 3, 4, 5, 6, 7, 8, 9);
	List<Integer> availableMoves = new ArrayList<Integer>();
	
	//Constructor for initial game board. Creates all children, then does a 
	//DFS to find the complete board. 
	public Games(int[] grid){
		board = grid;
		children = createAllChildren(board);
		Games winner = findCorrectBoard();
		if(winner != null) {
			for(int i = 0; i < winner.board.length; i++) {
				if(i != 0 && i % 9 ==0) {
					System.out.println("");
					System.out.println("");
				}
				System.out.print(winner.board[i] + ", ");
			}
		} else {
			System.out.println("womp womp");
		}
	}
	
	//Constructor for instances of the original game board. Creates board
	//and assigns it a parent. Also recursively creates all children. 
	public Games(int[] grid, Games parent) {
		this.parent = parent;
		board = grid;
		children = createAllChildren(board);
	}
	
	//Returns true if the board is complete.
	public boolean isComplete() {
		for(int i = 0; i < 81; i++) {
			if(board[i] == 0) {
				return false;
			}
		}
		return true;
	}
	
	//DFS to find the correct board.
	public Games findCorrectBoard() {
		Games g = this;
		while(g != null) {
			if(g.isComplete())
				return g;
			if(g.children.isEmpty()) {
				g.parent.children.remove(0);
				g = g.parent;
			} else {
				g = g.children.get(0);
			}
		}
		return this;
	}
	
	//Method to create all children. 
	public List<Games> createAllChildren(int[] grid){
		findMoves();
		List<Games> kids = new ArrayList<Games>();
		if(availableMoves.size() != 0) {
			for(int i = 0; i < availableMoves.size(); i++) {
				int[] copy = newGrid(grid, availableMoves.get(i));
				kids.add(new Games(copy, this));
			}
		} else {
			return kids;
		}
		return kids;
	}
	
	//Method to alter the the grid so the first zero is filled in
	//with one of the availableMoves numbers.
	public int[] newGrid(int[] numbs, int numb) {
		boolean first = true;
		int[] endGoal = new int[numbs.length];
		for(int i = 0; i < numbs.length; i++) {
			if(first && numbs[i] == 0) {
				endGoal[i] = numb;
				first = false;
			} else {
				endGoal[i] = numbs[i];
			}
		}
		return endGoal;
	}
	
	//Removes unavailable moves from total moves, then adds these numbers
	//to available moves. 
	public void findMoves() {
		for(int i = 0; i < board.length; i++) {
			if(board[i] ==0) {
				firstZero = i;
				break;
			}
		}
		row = firstZero / 9;
		checkRow();
		checkCol();
		checkQuad();
		for(int i = 0; i < moves.size(); i++) {
			if(moves.get(i) > 0) {
				availableMoves.add(moves.get(i));
			}
		}
	}
	
	//Checks the row of the firstZero and removes numbers in the row
	//from total moves.
	public void checkRow() {
		for(int i = row * 9; i < (row + 1) * 9; i++) {
			moves.set(board[i], -1);
		}
	}
	
	//Checks the column of the firstZero and removes numbers in the column
	//from total moves.
	public void checkCol() {
		for(int i = 0;  i < board.length; i++) {
			if(i % 9 == firstZero % 9) {
				moves.set(board[i], -1);
			}
		}
	}
	
	//Checks the quadrant of the firstZero and removes numbers in the quadrant
	//from total moves.
	public void checkQuad() {
		int[] quadNumb;
		if(row < 3 && (firstZero % 9 < 3)) {
			quadNumb = new int[] {0, 1, 2, 9, 10, 11, 18, 19, 20};
		} else if(row < 3 && (firstZero % 9 < 6)) {
			quadNumb = new int[] {3, 4, 5, 12, 13, 14, 21, 22, 23};
		} else if(row < 3) {
			quadNumb = new int[] {6, 7, 8, 15, 16, 17, 24, 25, 26};
		} else if(row < 6 && (firstZero % 9 < 3)) {
			quadNumb = new int[] {27, 28, 29, 36, 37, 38, 45, 46, 47};
		} else if(row < 6 && (firstZero % 9 < 6)) {
			quadNumb = new int[] {30, 31, 32, 39, 40, 41, 48, 49, 50};
		} else if(row < 6) {
			quadNumb = new int[] {33, 34, 35, 42, 43, 44, 51, 52, 53};
		} else if(firstZero % 9 < 3) {
			quadNumb = new int[] {54, 55, 56, 63, 64, 65, 72, 73, 74};
		} else if(firstZero % 9 < 6) {
			quadNumb = new int[] {57, 58, 59, 66, 67, 68, 75, 76, 77};
		} else {
			quadNumb = new int[] {60, 61, 62, 69, 70, 71, 78, 79, 80};
		}
		
		for(int numb : quadNumb) {
			moves.set(board[numb], -1);
		}
	}
	
	//Main method that instantiates the Sudoku board from runtime parameters. 
	//Prints out the winning board.
	public static void main(String[] args) {
		if(args.length != 81) {
			System.out.println("Invalid Input");
			return;
		}
		
		int[] numbs = new int[81];
		for(int i = 0; i < args.length; i++) {
			numbs[i] = Integer.parseInt(args[i]);
		}
		Games game = new Games(numbs);
	}
	
	
}
