
public class Test_S3
{

	/**
	 * @param args
	 */
	public static void main (String[] args)
	{
		Tape tape = null;

		System.out.println ("Test1: machines");
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
		} catch (Exception e) // if exception in TapeA_B
		{
			e.printStackTrace();
		}

		//_________________ Test sub-machines
		System.out.println ("\n\nTest 2: sub-machines\n");
		try
		{
			String cIn, cOut, cExpected;
			int iExpected;

			String cInputsConvToUnary[] = {"101", "0", "00"}; // should receive valid binary: not "B"
			for (int i = 0; i < cInputsConvToUnary.length; i++)
			{
				String cInput = cInputsConvToUnary[i];

				// convertToUnaryLeft
				tape = new TapeA_B (cInput);
				cIn = tape.toString ();
				MTA_B.convertToUnaryLeft (tape);
				cOut = tape.toString ();
				iExpected = Integer.parseInt (cInput, 2); // cInput: binary
				cExpected = "";
				while (iExpected-- > 0)
					cExpected += "1"; // toUnary: 1 x iExpected
				cExpected += "[ ]"; // at the end
				printSubMachineTest ("convertToUnaryLeft", cIn, cOut, cExpected);

				// convertToUnaryRight
				tape = new TapeA_B (cInput);
				MTA_B.findFirstBlankOnTheLeft (tape); //[ ]00
				cIn = tape.toString ();
				MTA_B.convertToUnaryRight (tape);
				cOut = tape.toString ();
				iExpected = Integer.parseInt (cInput, 2); // cInput: binary
				cExpected = "[ ]"; // at the beginning
				while (iExpected-- > 0)
					cExpected += "1"; // toUnary: 1 x iExpected
				printSubMachineTest ("convertToUnaryRight", cIn, cOut, cExpected);
			}

			System.out.println ();

			String cInputsConvToBinar[] = {"11111", "1", ""}; // should receive valid binary: not "B"
			for (int i = 0; i < cInputsConvToBinar.length; i++)
			{
				String cInput = cInputsConvToBinar[i];

				// convertToBinaryLeft
				tape = new TapeA_B (cInput);
				cIn = tape.toString ();
				MTA_B.convertToBinaryLeft (tape);
				cOut = tape.toString ();
				cExpected = Integer.toBinaryString (cInput.length ()) + "[ ]"; // cInput = unary => number of chars
				printSubMachineTest ("convertToBinaryLeft", cIn, cOut, cExpected);

				// convertToBinaryRight
				tape = new TapeA_B (cInput);
				MTA_B.findFirstBlankOnTheLeft (tape); //[ ]00
				cIn = tape.toString ();
				MTA_B.convertToBinaryRight (tape);
				cOut = tape.toString ();
				cExpected = "[ ]" + Integer.toBinaryString (cInput.length ()); // cInput = unary => number of chars
				printSubMachineTest ("convertToBinaryRight", cIn, cOut, cExpected);
			}
		} catch (Exception e) // if exception in TapeA_B
		{
			e.printStackTrace();
		}
		
	}

	private static void printSubMachineTest (String cMeth, String cIn, String cOut, String cExpected)
	{
		boolean bSame = cOut.compareTo (cExpected) == 0;
		System.out.print (cMeth + ":\tIn: '" + cIn + "' ;\tOut: '" + cOut + "'\t=> expected: '" + cExpected + "'\t=> ");
		if (bSame)
			System.out.println ("OK");
		else
			System.out.println ("ERROR");
	}

}

