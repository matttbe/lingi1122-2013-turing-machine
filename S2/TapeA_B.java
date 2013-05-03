public class TapeA_B implements Tape {
    private Cell readhead;
	
	private int repOkNb = -1;
	private boolean nullNextEncountered;
	private boolean nullPreviousEncountered;
	
	private static final char B = ' ';
	
	
	public TapeA_B(String init) throws Exception {
		if (init == null)
			throws new Exception();
		if (init.equals("")){
			readhead = new Cell();
			return;
		}			
		readhead = null;
		Cell tempNew = null;
		int maxLength = init.length;
		int i = 0;
		while(i < maxLength){
			tempNew = new Cell();
			testChar(init[i]); // throw exception if needed
			tempNew.content = init[i];
			tempNew.previous = readhead;
			readhead = tempNew;
			readhead.previous.next = readhead;
			i++;
		}
		readhead.next = null;
	}
	
	// utiliser des sous-problemes pour ca, ne pas hesiter a decomposer
	// eviter les spec par implementation
	public boolean repOk() {
		return false;
	}
	
	public boolean isSymbol(char s) throws Exception {
		testChar(s);
		return readhead.content == s;
	}
	
	public void putSymbol(char s) throws Exception {
		testChar(s); 
		readhead.content = s; 
	}

	public void leftMove() {
		// case of basic tape with only one box with B inside
		if (nullNextEncountered && nullPreviousEncountered && isSymbol(B)){
			return;
		// case of we are leaving a end of the tape and it is B 
		// so we have to delete the last box ton maintain the invariant
		if (nullNextEncountered && isSymbol(B))
			readhead.previous.next = null;
		// case of end of the tape and we have to go into the B territory
		if (nullPreviousEncountered){
			readhead.previous = new Cell();
			readhead.previous.next = readhead;
			nullNextEncountered = false;
		}
		// normal case
		readhead = readhead.previous;
	}

	public void rightMove() {
		// case of basic tape with only one box with B inside
		if (nullPreviousEncountered && nullNextEncountered && isSymbol(B)){
			return;
		// case of we are leaving a end of the tape and it is B 
		// so we have to delete the last box ton maintain the invariant
		if (nullPreviousEncountered && isSymbol(B))
			readhead.next.previous = null;
		// case of end of the tape and we have to go into the B territory
		if (nullNextEncountered){
			readhead.next = new Cell();
			readhead.next.previous = readhead;
			nullPreviousEncountered = false;
		}
		// normal case
		readhead = readhead.next;
	}
	
	public String toString() {
		String answer = "[" + readhead.content + "]";
		Cell temp;
		temp = readhead.next;
		while (temp != null){
			answer = answer + temp.content;
			temp = temp.next;
		}
		temp = readhead.previous;
		while (temp != null){
			answer = temp.content + answer;
			temp = temp.previous;
		}
		return answer;
	}
	
	private class Cell {
		char content = B;
		Cell next = null;
		Cell previous = null;
		int checkedFor = -1;
		
		public Cell() {
		}
	}
	
	private void testChar(char s) throws Exception {
		if (s > 126 || s < 32)
			throw new Exception();
	}
}
