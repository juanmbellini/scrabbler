package utility;

/**
 * This class represents a priority queue of conditions to evaluate words in the dictionary. Conditions are sorted
 * by on their position. It has a method to return a condition to the first position
 *
 */
public class ConditionsQueue {
	
	
	Node first;
	Node last;
	
	public ConditionsQueue() {
		first = null;
		last = null;
	}
	
	/**
	 * Evaluates if the queue is empty
	 * @return <code>true</code> if the queue is empty, or <code>false</code> if not
	 */
	public boolean isEmpty() {
		return first == null;
	}
	
	/**
	 * Enqueues the specified condition
	 * @param condition The condition to be enqueued
	 * @throw AllreadyInCollectionException When adding a condition to an already contained position
	 */
	public void enqueueCondition(WordCondition condition) throws AlreadyInCollectionException {
		
		Node current = first;
		Node prev = null;
		
		while (current != null && current.condition.getPosition() < condition.getPosition()) {
			prev = current;
			current = current.next;
		}
		
		if (current != null && current.condition.getPosition() == condition.getPosition()) {
			throw new AlreadyInCollectionException("You must specify only one letter per position");
		}
		
		Node aux = new Node(condition, current);
		
		if (prev == null) {
			first = aux;
		} else {
			prev.next = aux;
		}
	}
	
	
	
	public void returnConditionToTheQueue(WordCondition condition) {
		
		first = new Node(condition, first);
		if (last == null) {
			last = first;
		}
		
	}
	
	
	
	/**
	 * Dequeues the first condition in the queue
	 * @return The first condition in the queue, or <code>null</code> if the queue was empty
	 */
	public WordCondition dequeueCondition() {
		
		if (first == null) {
			return null;
		}
		
		WordCondition aux = first.condition;
		first = first.next;
		if (first == null) {
			last = null; // If after moving forward in the queue there is no more elements, there is no more last one
		}
		return aux;
	}

	
	public WordCondition peekCondition() {
		
		if (first == null) {
			return null;
		}
		return first.condition;
	}
	
	
	
	private static class Node{
		
		WordCondition condition;
		Node next;
		
		public Node(WordCondition condition, Node next) {
			this.condition = condition;
			this.next = next;
		}
	}

}
