/**
 * The node class for the A* search.
 * @author Ryan Tucker
 *
 */
public class Node implements Comparable<Node> {
	
	private Board board;
	private int movesPrior;
	private Node previous;
	private Heuristics heuristic;
	
	public Node(Board board, int movesPrior, Node previous, Heuristics heuristic) {
		this.board = board;
		this.movesPrior = movesPrior;
		this.previous = previous;
		this.heuristic = heuristic;
	}
	
	/**
	 * Gets the cost of this board state.
	 * @return
	 */
	public int getCost() {
		int cost = movesPrior;
		//System.out.println("##Moves prior: " + movesPrior);
		
		switch(heuristic) {
			case OUT_OF_PLACE:
				cost += board.tilesOutOfPlace();
				break;
			case MANHATTAN:
				cost += board.manhattan();
				break;
			case MY_HEURISTIC:
				break;
			default:
				// This is a plain DFS
				break;
		}
		
		//System.out.println("## Final cost: " + cost);
		
		return cost;
	}
	
	/**
	 * Returns the previous board state.
	 * @return the previous board state.
	 */
	public Node getPrevious() {
		return previous;
	}
	
	/**
	 * Gets the board held by the node.
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Gets the number of moves prior.
	 * @return the number of prior moves.
	 */
	public int getMovesPrior() {
		return movesPrior;
	}
	
	/**
	 * Implement the compareTo function so we can use Java's priority queue.
	 */
	@Override
	public int compareTo(Node otherNode) {
		if(this.getCost() < otherNode.getCost()) {
			return -1;
		}
		
		if(this.getCost() > otherNode.getCost()) {
			return 1;
		} else {
			return 0;
		}
	}
}
