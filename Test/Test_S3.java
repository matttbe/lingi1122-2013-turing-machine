
public class Test_S3
{

	/**
	 * @param args
	 */
	public static void main (String[] args)
	{
		Tape tape = null;
		try
		{	// error: 101 0 => add: 110 ; divide: inf. loop
			//        0  10 => divide: error -> if(dividend>divisor)
			//        0   0 => add: 1 ; substract: [ ] ; multiply: [ ] ; divide: error -> if(dividend>divisor)
			//        10 10 => subtract: [ ]; divide: error -> if(dividend>divisor)
			// nombre très très grand: => ils utilisent des longs (alors qu'ils ne peuvent pas ;) )
			final String cTestTapes[] = {"101 10", "101 0", "0 10", "0 0", "10 10"};
			final String cMethods[] = {"add", "substract", "multiply", "divide"};

			for (int i = 0; i < cTestTapes.length; i++)
			{
				String cTape = cTestTapes[i];
				String cTapes[] = cTape.split (" ");
				int iTapes[] = {Integer.parseInt(cTapes[0], 2), Integer.parseInt (cTapes[1], 2)};
				int iOp[] = {iTapes[0] + iTapes[1], iTapes[0] - iTapes[1],
						iTapes[0] * iTapes[1], iTapes[1] != 0 ? iTapes[0] / iTapes[1] : 0,
						iTapes[1] != 0 ? iTapes[0] % iTapes[1] : 0};
				if (iOp[1] < 0) // substract: min 0
					iOp[1] = 0;
				System.out.println ("\n" + cTape + " => " + iTapes[0] + " " + iTapes[1]);

				for (int j = 0; j < cMethods.length; j++)
				{
					String cMeth = cMethods[j];
					if (i == 1 && j == 3)
						continue; // avoid inf. loop
					
					System.out.print (cMeth + ": ");
					tape = new TapeA_B (cTape);
					try
					{
						MTA_B.class.getDeclaredMethod (cMeth, new Class[]{Tape.class}).invoke (null, tape);
					} catch (java.lang.reflect.InvocationTargetException e)
					{
						System.out.print ("Exception... ");
					}
					if (j == 3) // modulo
						System.out.println (tape + " expected: "
							+ Integer.toString (iOp[3], 2) + " "
							+ Integer.toString (iOp[4], 2) + " => "
							+ iOp[3] + " " + iOp[4]);
					else
						System.out.println (tape + " expected: " + Integer.toString (iOp[j], 2) + " => " + iOp[j]);
				}
			}
		} catch (Exception e) // if error in TapeA_B
		{
			e.printStackTrace();
		}
	}

}

