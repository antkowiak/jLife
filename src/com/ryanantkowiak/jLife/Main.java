/**
 * 
 */
package com.ryanantkowiak.jLife;


/**
 * @author Ryan Antkowiak (antkowiak@gmail.com)
 *
 */
public class Main
{
	public static void main(String [] args)
	{
		LifeModel lm = new LifeModel(20, 20);
		
		lm.zeroGrid();
		
		// Set the state for a glider
		lm.setState(5, 5, true);
		lm.setState(5, 6, true);
		lm.setState(5, 7, true);
		lm.setState(4, 7, true);
		lm.setState(3, 6, true);
		
		for (int i = 0 ; i < 1000 ; ++i)
		{
			System.out.println(lm.toString());
			System.out.println();
			lm.advanceGeneration();
			try
			{
				Thread.sleep(100);
			}
			catch (Exception e)
			{
			}
		}
	}
}
