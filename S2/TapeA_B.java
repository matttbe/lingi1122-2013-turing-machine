public class TapeA_B implements Tape {
    private Cell readhead;
	
	private int repOkNb = -1;
	private boolean nullNextEncountered;
	private boolean nullPreviousEncountered;
	
	private static final char B = ' '; // final variable for the Blanc
	
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
		
		// to ensure the position of the readhead the block after the last of init
		String initB = init + " "; 
		
		// initialisation
		readhead = null;
		Cell tempNew = null;
		int maxLength = initB.length ();
		int i = 0;
		char[] initChar = initB.toCharArray ();
		
		// building of the tape
		while(i < maxLength){
			tempNew = new Cell();
			testChar(initChar[i]); // throw exception if needed
			tempNew.content = initChar[i];
			tempNew.previous = readhead;
			readhead = tempNew;
			readhead.previous.next = readhead;
			i++;
		}
		
		// finish the tape
		readhead.next = null;
		nullNextEncountered = true;
		nullPreviousEncountered = (readhead.previous == null);
	}
	
	
	public boolean repOk() {
		if (repOkNb != -1) // deja teste
			return (repOkNb == 0);
		repOkNb = 0;
		
		// 1ere boucle, on regarde vers next : 
		Cell tempNow = readhead.next;
		Cell tempPast = readhead;
		while (tempNow != null && repOkNb == 0) {
			// test le char
			try {
				testChar(tempNow.content);
			} catch (Exception e){
				repOkNb++;
			}
			// verifie qu'on pointe bien vers le prev, chaine qui augmente bien et on verifie qu'on est pas sur une case deja testee
			if ((tempNow.previous != tempPast) || (tempNow != readhead))
				repOkNb++;
			tempPast = tempNow;
			tempNow = tempPast.next;
		}
		// teste si il y a des blancs au bout de la chaine (B' minimale)
		if (repOkNb == 0 && tempPast != readhead && tempPast.content == B)
			repOkNb++;
		// on a fini le cote next, on redemare de readhead dans l'autre sens
		tempNow = readhead.previous;
		tempPast = readhead;
		while (tempNow != null && repOkNb == 0) {
			// test le char
			try {
				testChar(tempNow.content);
			} catch (Exception e) {
				repOkNb++;
			}
			// verifie qu'on pointe bien vers le prev, chaine qui augmente bien et on verifie qu'on est pas sur une case deja testee
			if ((tempNow.next != tempPast) || (tempNow != readhead))
				repOkNb++;
			tempPast = tempNow;
			tempNow = tempPast.previous;
		}
		// teste si il y a des blancs au bout de la chaine (B' minimale)
		if (repOkNb == 0 && tempPast != readhead && tempPast.content == B)
			repOkNb++;
		return (repOkNb == 0);
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
		try {
			// case of basic tape with only one box with B inside
			if (nullNextEncountered && nullPreviousEncountered && isSymbol(B))
				return;
			
			// case of we are leaving a end of the tape and it is B 
			// so we have to delete the last box ton maintain the invariant
			if (nullNextEncountered && isSymbol(B))
				readhead.previous.next = null;
		} catch (Exception e) {
			return;
		}

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
		try {
		// case of basic tape with only one box with B inside
			if (nullPreviousEncountered && nullNextEncountered && isSymbol(B))
				return;
			// case of we are leaving a end of the tape and it is B 
			// so we have to delete the last box ton maintain the invariant
			if (nullPreviousEncountered && isSymbol(B))
				readhead.next.previous = null;
		} catch (Exception e) {
			return;
		}
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
		// int checkedFor = -1; // not used
		
		public Cell() {
		}
	}
	
	private void testChar(char s) throws Exception {
		if (s > 126 || s < 32)
			throw new Exception();
	}
}
