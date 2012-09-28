import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a current board state.
 * @author Ryan Tucker
 *
 */
public class Board {
	private static final int BLANK_TILE = -1; // Represents the blank tile in the puzzle
	
	private int[] board; // The board itself
	
	/**
	 * Constructor that takes a given board.
	 * @param board the board
	 */
	public Board(int[] board) {
		// Deep copy the board state
		this.board = board.clone();
	}
	
	/**
	 * Returns the board
	 * @return the board.
	 */
	public int[] getBoard() {
		return board;
	}
	
	/**
	 * Gets the number of inversions in the board.
	 * @return the number of inversions in the board.
	 */
	private int getInversions() {
		// Get the number of inversions
		int inversions = 0;
		
		// We can skip the last tile
		for(int i = 0; i < board.length - 1; i++) {
			// Skip the blank tile
			if(board[i] == BLANK_TILE) {
				continue;
			}
			
			for(int j = i + 1; j < board.length; j++) {
				// Skip the blank tile if encountered
				if(board[j] == BLANK_TILE) {
					continue;
				}
				
				// Found an inversion
				if(board[i] > board[j]) {
					inversions++;
				}
			}
		}
		
		return inversions;
	}
	
	/**
	 * This tests if the given board state is solvable.
	 * @return true if solvable, false otherwise
	 */
	public boolean isSolvable() {
		
		int inversions = getInversions();
		
		if(inversions % 2 == 0) {
			// Even
			return true;
		} else {
			// Odd
			return false;
		}
	}
	
	/**
	 * Checks if the goal state has been reached
	 * @return true if goalstate is reached, false otherwise
	 */
	public boolean isGoalState() {
		for(int i = 1; i < board.length; i++) {
			if(board[i] != i) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Returns the number of tiles out of place.
	 * @return the number of tiles out of place.
	 */
	public int tilesOutOfPlace() {
		// Find the number of tiles out of place by looping over the board.
		
		int numberOutOfPlace = 0;
				
		for(int i = 1; i < board.length; i++) {
			if(board[i] != i) {
				++numberOutOfPlace;
			}
		}
		
		return numberOutOfPlace;
	}
	
	/**
	 * Returns the sum of the Manhattan distances.
	 * @return the sum of the Manhattan distances.
	 */
	public int manhattan() {
		// Find the sum of the Manhattan distances of the puzzle
		
		int manhattanSum = 0;
		
		// Loop over the board and find how far away each element is
		for(int i = 0; i < board.length; i++) {
			// Don't count the blank as out of place
			if(board[i] == BLANK_TILE) {
				continue;
			} else {
				final int properLocation = board[i]; // Find out where the tile should be in the board
				// i is the current location in the board
				
				// Row movement + column movement
				manhattanSum += (Math.abs(properLocation/3 - i/3) + Math.abs(properLocation % 3 - i % 3));
			}
		}
		
		return manhattanSum;
	}
	
	/**
	 * My admissible heuristic using number of inversions in the board.
	 * @return admissible heuristic
	 */
	public int myHeuristic() {
		return getInversions();
	}
	
	/**
	 * Swaps two values in the board and returns a new board.
	 * @param i the first slot to be swapped
	 * @param j the second slot to be swapped
	 * @return the new board state
	 */
	private Board swap(int i, int j) {
		Board newBoard = new Board(board);
		
		int temp = newBoard.board[i];
		
		newBoard.board[i] = board[j];
		newBoard.board[j] = temp;
		
		return newBoard;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(board);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (!Arrays.equals(board, other.board))
			return false;
		return true;
	}
	
	/**
	 * Returns a string representation of the board.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(board[i*3 + j] == BLANK_TILE) {
					sb.append('-');
				} else {
					sb.append(board[i*3 + j]);
				}

				sb.append(' ');
			}
			
			sb.append('\n');
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns a list of the possible neighboring states.
	 * @return
	 */
	public List<Board> getNeighboringStates() {
		List<Board> neighboringStates = new ArrayList<Board>(4);
		
		// Get all the neighboring states and load them into the list
		
		// Find where the blank is
		int blankLocation = 0;
		
		for(int i = 0; i < board.length; i++) {
			if(board[i] == BLANK_TILE) {
				blankLocation = i;
				break;
			}
		}
		
		// Swap and add to the list
		final int rowLocation = blankLocation / 3;
		final int columnLocation = blankLocation % 3;
		
		if(columnLocation - 1 >= 0) {
			neighboringStates.add(swap(blankLocation, blankLocation - 1));
		}
		
		if(columnLocation + 1 < 3) {
			neighboringStates.add(swap(blankLocation, blankLocation + 1));
		}
		
		if(rowLocation - 1 >= 0) {
			neighboringStates.add(swap(blankLocation, blankLocation - 3));
		}
		
		if(rowLocation + 1 < 3) {
			neighboringStates.add(swap(blankLocation, blankLocation + 3));
		}
		
		// List should be filled, return
		return neighboringStates;
	}
}
