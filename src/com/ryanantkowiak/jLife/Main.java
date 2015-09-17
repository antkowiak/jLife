/**
 * Program entry point for game of Life.
 * 
 * @author Ryan Antkowiak (antkowiak@gmail.com)
 */
package com.ryanantkowiak.jLife;

import javax.swing.JFrame;

/**
 * @author Ryan Antkowiak (antkowiak@gmail.com)
 *
 * Main class for the java implementation of Life, with wrap-around logic.
 */
public class Main
{
	private static final int LIFE_WIDTH = 100;
	private static final int LIFE_HEIGHT = 50;
	
	private static final int GENERATION_DELAY = 100;
	private static final int NUM_GENERATIONS = 10000;
	
	/*
	 * @brief	Main entry point to the program
	 * @param	args - unused
	 */
	public static void main(String [] args)
	{
		// Create the model and view
		LifeModel lifeModel = new LifeModel(LIFE_WIDTH, LIFE_HEIGHT);
		LifeView lifeView = new LifeView(lifeModel);
		
		// Create the window for display
		JFrame frame = new JFrame("jLife - by Ryan Antkowiak (antkowiak@gmail.com)");
		frame.getContentPane().add(lifeView);
		frame.getContentPane().setSize(lifeView.getSize());
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Play the game of Life!
		for (int i = 0 ; i < NUM_GENERATIONS ; ++i)
		{
			lifeView.updateLifeDisplay();
		
			delay(GENERATION_DELAY);
			
			lifeModel.advanceGeneration();
		}
	}
	
	/*
	 * @brief	Attempt to delay the thread a number of milliseconds
	 * @param	ms - the time in milliseconds to delay
	 */
	private static void delay(int ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch (Exception e)
		{
		}
	}
}
