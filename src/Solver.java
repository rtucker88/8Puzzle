import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * The main class to solve the 8-puzzle.
 * @author ryan
 *
 */
public class Solver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] testBoard = { -1, 1, 3, 4, 2, 5, 7, 8, 6 };
		Board starting = new Board(testBoard);
		
		long startTime = System.currentTimeMillis();
		depthFirstSearch(starting, Heuristics.OUT_OF_PLACE);
		long endTime = System.currentTimeMillis();
		
		System.out.println("Took " + (endTime - startTime) + " milliseconds.");

	}
	
	/**
	 * Going to assume a valid board state is based in.
	 * @param initialState the inital board state
	 * @param heuristic the heuristic to use for searching
	 */
	public static void depthFirstSearch(Board initialState, Heuristics heuristic) {
		// Get the initial board set up
		Node rootNode = new Node(initialState, 0, null, heuristic);
		
		// Create the priority queue and set-up the initial node
		PriorityQueue<Node> queue = new PriorityQueue<Node>();
		
		queue.add(rootNode);
		
		// Now search
		boolean goalStateFound = false;
		
		while(!goalStateFound) {
			if(queue.peek().getBoard().isGoalState()) {
				goalStateFound = true;
				break; // Trivial case
			}
			
			// Expand the current lowest cost node and add the branches to the queue
			List<Board> neighbors = queue.peek().getBoard().getNeighboringStates();
			
			int currentNumberOfMoves = queue.peek().getMovesPrior();
			++currentNumberOfMoves;
			
			Node currentNode = queue.remove();
			
			for(Board b : neighbors) {
				if(currentNode.getPrevious() != null && b.equals(currentNode.getPrevious().getBoard())) {
					// Skip this if we're going to a state we had before
					continue;
				}
				
				// Get the cost and add it to the queue
				queue.add(new Node(b, currentNumberOfMoves, currentNode, heuristic));
			}
			
			//System.out.println(queue.peek().getBoard().toString());
		}
		
		// Unwind the nodes to get how it was solved.
		
		Node lastNode = queue.remove();
		ArrayList<Node> solutionList = new ArrayList<Node>();
		
		while(lastNode.getPrevious() != null) {
			solutionList.add(lastNode);
			
			lastNode = lastNode.getPrevious();
		}
		
		System.out.println("Found a solution. Size: " + solutionList.size());
		
		for(int i = solutionList.size() - 1; i >= 0; i--) {
			System.out.println(solutionList.get(i).getBoard().toString());
		}
	}

}
