
public class Test_S3
{

	/**
	 * @param args
	 */
	public static void main (String[] args)
	{
		Tape tape = null;
		try
		{
			final String cTestTapes[] = {"101 10", "101 0", "0 10", "0 0", "10 10"};
			final String cMethods[] = {"add", "substract", "multiply", "divide"};

			for (int i = 0; i < cTestTapes.length; i++)
			{
				String cTape = cTestTapes[i];
				String cTapes[] = cTape.split (" ");
				int iTapes[] = {Integer.parseInt(cTapes[0], 2), Integer.parseInt (cTapes[1], 2)};
				int iOp[] = {iTapes[0] + iTapes[1],	// add
						iTapes[0] - iTapes[1],		// substract
						iTapes[0] * iTapes[1],		// multiply
						iTapes[1] != 0 ? iTapes[0] / iTapes[1] : 0,		// divide (/!\ we can't x/0)
						iTapes[1] != 0 ? iTapes[0] % iTapes[1] : 0};	// modulo (/!\ we can't x%0)
				if (iOp[1] < 0) // substract: min 0
					iOp[1] = 0;
				System.out.println ("\n" + cTape + " => base10: " + iTapes[0] + " " + iTapes[1]);

				for (int j = 0; j < cMethods.length; j++)
				{
					String cMeth = cMethods[j];
					System.out.print (cMeth + ": ");

					if (i == 1 && j == 3)
					{
						System.out.println ("infinite loop \t => ERROR");
						continue; // avoid inf. loop => from calculability: we can't detect a inf. loop :)
					}

					tape = new TapeA_B (cTape);
					boolean bException = false;
					try
					{
						MTA_B.class.getDeclaredMethod (cMeth, new Class[]{Tape.class}).invoke (null, tape);
					} catch (java.lang.reflect.InvocationTargetException e)
					{
						System.out.print ("Exception... ");
						bException = true;
					}
					String cResult = tape.toString ();
					String cExpectedResult = Integer.toString (iOp[j], 2);

					if (j == 3) // with modulo
					{
						if (bException && iTapes[1] == 0) // exception only if there is a division by 0
						{
							System.out.println ("\t => Exception OK");
							continue;
						}
						System.out.print (cResult + " expected: " + cExpectedResult + " ");

						// cResult contains at least '[ ]'
						int iPosSpace = cResult.indexOf (' ');
						String cResultBinaryDiv = cResult.substring (0, iPosSpace); // result
						int iPosBracket = cResult.indexOf ('[');
						String cResultBinaryMod = null;
						if (iPosBracket >= iPosSpace)
							cResultBinaryMod = cResult.substring (iPosSpace + 1, cResult.indexOf ('[')); // reste

						String cExpectedResultMod = Integer.toString (iOp[j+1], 2);
						System.out.print (cExpectedResultMod + " => base10: "
							+ iOp[j] + " " + iOp[j+1]);
						if (cExpectedResult.compareTo (cResultBinaryDiv) != 0 || 
									cExpectedResultMod.compareTo (cResultBinaryMod) != 0)
							System.out.println ("\t => ERROR");
						else
							System.out.println ("\t => OK");
					}
					else
					{
						System.out.print (cResult + " expected: " + cExpectedResult + " ");
						String cResultBinary = cResult.substring (0, cResult.indexOf ('[')); // before the head
						System.out.print ("=> base10: " + iOp[j]);
						if (cExpectedResult.compareTo (cResultBinary) != 0)
							System.out.println ("\t => ERROR");
						else
							System.out.println ("\t => OK");
					}
				}
			}
		} catch (Exception e) // if error in TapeA_B
		{
			e.printStackTrace();
		}
	}

}

