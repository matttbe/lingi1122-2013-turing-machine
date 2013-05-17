public class TapeA_B implements Tape {
	private Cell readhead;
	
	// private int repOkNb = -1; // not used
	// private boolean nullNextEncountered; // not used
	// private boolean nullPreviousEncountered; // not used
	
	private static final char B = ' '; // final variable for the Blank
	
	/**
	 * @param :	init : the string that represent the tape
	 * @post :		build a TapeA_B object, the readhead is the block 
	 * 				after the last char of init
	 * 				raise an exception if init is null or contains char not allowed
	 */
	public TapeA_B(String init) throws Exception {
		// ensure something to transform in a tape
		if (init == null)
			throw new Exception();
		
		// to ensure the position of the read head is after the last block of init
		String initB = init + " "; 


		// INIT
		readhead = null;
		Cell tempNew = null;
		int maxLength = initB.length ();
		int i = 0;
		char[] initChar = initB.toCharArray ();
		
		/**
		 * building of the tape:
		 * 	Inv: we have a tabular with id between 0 and 'maxLength'.
		 * 	     we have to read all cells.
		 * 	     at one moment, i is between 0 and 'maxLength',
		 * 	      all id's before i have already been read.
		 * 	         |0    |i    |maxLength
		 * 	         |01010101010|
		 * 	H: i == maxLength
		 * 	Var: i contains the index of the last char added to the
		 * 		 structure + 1
		 */
		while(i < maxLength){
			tempNew = new Cell();
			testChar(initChar[i]); // throw exception if needed
			tempNew.content = initChar[i];
			tempNew.previous = readhead;
			readhead = tempNew;
			if (readhead.previous != null) // first cell: previous doesn't exist
				readhead.previous.next = readhead;
			i++;
		}
		
		// CLOT: finish the tape
		readhead.next = null;
	}
	
	/**
	 * Test if the structure agreed the invariant of representation :
	 * - characters are in the Gamma alphabet (from char n 32 to 126)
	 * - no loop, linear course long the double linked list
	 * - only one cell with a null previous and only one with a null next
	 * - the readhead must be on the tape (not null)
	 * - B zone (contains all the not blanc char) as little as possible
	 * - B' (extended zone B with blanc at ends) zone as little as possible
	 * @return :	true, if the object agreed the invariant of representation
	 * 				false, if not
	 */
	public boolean repOk() {
		// SUB-PROB1: ensure at least one block
		if (readhead == null)
			return false;


		/* SUB-PROB2: read all cell ; direction: to the left.
		 * 	Aim: check if all chars are correct and if there is no double linkage
		 */
		// INIT
		Cell tempNow = readhead.next;	// the current cell
		Cell tempPast = readhead;	// the previous one
		/**
		 * var: at each step tempPast is the previous cell and tempNow 
		 *      is the cell on the right
		 * inv: readHead is not modified
		 * H: tempNow == null
		 */
		while (tempNow != null) {
			// ensure an available char
			try {
				testChar(tempNow.content);
			} catch (Exception e){
				return false;
			}
			// ensure double linkage 
			if ((tempNow.previous != tempPast) || (tempNow == readhead))
				return false;
			tempPast = tempNow;
			tempNow = tempPast.next;
		}


		/* SUB-PROB3: if the tape contains more than one element:
		 * 	Aim: test if there is no blank at the end
		 */
		if (tempPast != readhead && tempPast.content == B)
			return false;


		/* SUB-PROB4: read all cell ; direction: to the right.
		 * 	Aim: check if all chars are correct and if there is no double linkage
		 */
		// INIT
		tempNow = readhead.previous;
		tempPast = readhead;
		/**
		 * var: at each step tempPast is the next cell and tempNow 
		 *      is the cell on the left
		 * inv: readHead is not modified
		 * H: tempNow == null
		 */
		while (tempNow != null) {
			// ensure an available char
			try {
				testChar(tempNow.content);
			} catch (Exception e) {
				return false;
			}
			// ensure double linkage 
			if ((tempNow.next != tempPast) || (tempNow == readhead))
				return false;
			tempPast = tempNow;
			tempNow = tempPast.previous;
		}


		/* SUB-PROB5: if the tape contains more than one element:
		 * 	Aim: test if there is no blank at the end
		 */
		if (tempPast != readhead && tempPast.content == B)
			return false;

		// No sub-problem has failed:
		return true;
	}

	/**
	 * @pre: /
	 * @post: true iff the symbol bellow the read head is 's'.
	 *        An exception is produced if 's' is not valid
	 */
	public boolean isSymbol(char s) throws Exception {
		testChar(s);
		return readhead.content == s;
	}

	/**
	 * @pre:  readhead valid and != null (always the case)
	 * @post: replace the symbol bellow the read hind by 's'.
	 *        An exception is produced if 's' is not valid
	 */
	public void putSymbol(char s) throws Exception {
		testChar(s); 
		readhead.content = s; 
	}

	/**
	 * @pre:  readhead valid and != null (always the case)
	 * @post: move the read head to the cell on the left or do nothing if the
	 *        tape only contains a blank.
	 *        If the previous cell doesn't exist, create a new one with blank char.
	 *        If the read head is on the last cell which is blank (and is not
	 *         the only one), remove this cell.
	 */
	public void leftMove() {
		try {
			// case of basic tape with only one box with B inside
			if (readhead.next == null && readhead.previous == null && isSymbol(B))
				return;
			
			// case of we are leaving a end of the tape and it is B 
			// so we have to delete the last box ton maintain the invariant
			if (readhead.next == null && isSymbol(B))
				readhead.previous.next = null;
		} catch (Exception e) {
			return; // problem with isSymbol: should not happen because the
			        // symbol has been checked before (constructor or putSymbol)
		}

		// case of end of the tape and we have to go into the B territory
		if (readhead.previous == null){
			readhead.previous = new Cell();
			readhead.previous.next = readhead;
		}

		// normal case
		readhead = readhead.previous;
	}

	/**
	 * @pre:  readhead valid and != null (always the case)
	 * @post: move the read head to the cell on the right or do nothing if the
	 *        tape only contains a blank.
	 *        If the next cell doesn't exist, create a new one with blank char.
	 *        If the read head is on the first cell which is blank (and is not
	 *         the only one), remove this cell.
	 */
	public void rightMove() {
		try {
		// case of basic tape with only one box with B inside
			if (readhead.previous == null && readhead.next == null && isSymbol(B))
				return;
			// case of we are leaving a end of the tape and it is B 
			// so we have to delete the last box ton maintain the invariant
			if (readhead.previous == null && isSymbol(B)) // readhead.previous == null
				readhead.next.previous = null;
		} catch (Exception e) {
			return; // problem with isSymbol: should not happen because the
			        // symbol has been checked before (constructor or putSymbol)
		}
		// case of end of the tape and we have to go into the B territory
		if (readhead.next == null){
			readhead.next = new Cell();
			readhead.next.previous = readhead;
		}
		// normal case
		readhead = readhead.next;
	}

	/**
	 * @pre: readhead valid and != null (always the case)
	 * @post: return a String of the form alpha[s]beta with the content 
	 * 		  of all char available on the tape.
	 *        The char bellow the read head is surrounded by brackets (s).
	 * 		  alpha is a suffixe of what is before the readhead
	 * 		  beta is a prefixe of what is after the readhead
	 */
	public String toString() {
		String answer = "[" + readhead.content + "]";
		Cell temp;
		temp = readhead.next;
		/**
		 * Inv: answer contains the string [s]beta' where beta' is the 
		 * 		string representation up to the previous cell of temp.
		 * Var: at each steps, temp take the value of temp.next so temp
		 * 		contains the first cell after readhead with the content
		 * 		is not already in answer
		 */
		while (temp != null){
			answer = answer + temp.content;
			temp = temp.next;
		}
		temp = readhead.previous;
		/**
		 * Inv: answer contains the string alpha'[s]beta where alpha' is the 
		 * 		string representation up to the next cell of temp.
		 * Var: at each steps, temp take the value of temp.previous so temp
		 * 		contains the first cell before readhead with the content
		 * 		is not already in answer
		 */
		while (temp != null){
			answer = temp.content + answer;
			temp = temp.previous;
		}
		return answer;
	}

	/**
	 * @pre: /
	 * @post: a new Cell is created. The char is B (blank) by default
	 */
	private class Cell {
		char content = B;
		Cell next = null;
		Cell previous = null;
		// int checkedFor = -1; // not used
		
		public Cell() {
		}
	}

	/**
	 * @pre: s is an ASCII char.
	 * @post: throws an exception if the char is invalid (not between 32 and 126)
	 */
	private void testChar(char s) throws Exception {
		if ((int) s > 126 || (int) s < 32) {
			System.err.println ("Invalid char: '" + s + "'");
			throw new Exception();
		}
	}
}
