
public class Test
{

	/**
	 * @param args
	 */
	public static void main (String[] args)
	{
		Tape tape = null;
		try
		{
			tape = new TapeA_B ("10101001");
			System.out.println (tape.toString ()); // 10101001[ ]
			System.out.println (tape.isSymbol ('1')); // false
			System.out.println (tape.isSymbol ('0')); // false
			System.out.println (tape.isSymbol (' ')); // true
			tape.putSymbol ('0');
			System.out.println (tape.toString ()); // 10101001[0]
			System.out.println (tape.isSymbol ('1')); // false
			System.out.println (tape.isSymbol ('0')); // true
			System.out.println (tape.isSymbol ((char) 0)); // exception
		} catch (Exception e)
		{
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		System.out.println ("blank: 1");
		try
		{
			tape.putSymbol (' ');
		} catch (Exception e1)
		{
			// TODO Bloc catch généré automatiquement
			e1.printStackTrace();
		}
		System.out.println ("left: 1");
		tape.leftMove ();
		System.out.println ("left: 2");
		tape.leftMove ();
		try
		{
			System.out.println (tape.isSymbol ('1')); // false
			System.out.println (tape.isSymbol ('0')); // true
			System.out.println (tape.isSymbol (' ')); // false
			System.out.println (tape.toString ()); // 101010[0]1
		} catch (Exception e)
		{
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		tape.rightMove ();
		tape.rightMove ();
		tape.rightMove ();
		tape.rightMove ();
		System.out.println (tape.toString ()); // 10101001BBB
		for (int i = 0; i < 16; i++)
		{
			System.out.println ("Left " + i);
			tape.leftMove ();
		}
		System.out.println (tape.toString ()); // BBB(...)B10101001
		System.out.println ("\nNEW TEST: with empty string\n");
		Tape tape1 = tape;
		try
		{
			tape = new TapeA_B ("");
		} catch (Exception e)
		{
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		System.out.println (tape.toString ()); // "[B]"
		tape.rightMove ();
		tape.rightMove ();
		System.out.println (tape.toString ()); // "[B]"
		try
		{
			tape.isSymbol (' '); // true
			tape.putSymbol ('1');
		} catch (Exception e)
		{
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		System.out.println (tape.toString ()); // "[1]"
		tape.leftMove ();
		tape.leftMove ();
		try
		{
			tape.putSymbol ('0');
		} catch (Exception e)
		{
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		System.out.println (tape.toString ()); // "[0]  1"

		System.out.println ("\nTEST 1: repOK\n");
		System.out.println (tape1.repOk ());

		System.out.println ("\nTEST 2: repOK\n");
		System.out.println (tape.repOk ());
	}

}
