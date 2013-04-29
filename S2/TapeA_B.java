public class TapeA_B implements Tape {
	private Cell readhead;
	
	private int repOkNb=-1;
	private boolean nullNextEncountered;
	private boolean nullPreviousEncountered;
	
	public TapeA_B(String init) throws Exception {
	}
	
	public boolean repOk() {
		return false;
	}
	
	public boolean isSymbol(char s) throws Exception {
		return false;
	}
	
	public void putSymbol(char s) throws Exception {
	}

	public void leftMove() {
	}

	public void rightMove() {
	}
	
	public String toString() {
		return null;
	}
	
	private class Cell {
		char content;
		Cell next=null;
		Cell previous=null;
		int checkedFor=-1;
		
		public Cell() {
			content=(char) 32;
		}
	}
}
