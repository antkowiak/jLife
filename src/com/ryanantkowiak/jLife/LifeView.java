/**
 * View class for the game of Life.
 * 
 * @author Ryan Antkowiak 
 */
package com.ryanantkowiak.jLife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

/*
 * @brief	LifeView class
 */
public class LifeView extends JComponent
{
	private static final int CELL_SIZE = 10;
	private static final Color DEAD_COLOR = Color.ORANGE;
	private static final Color ALIVE_COLOR = Color.BLACK;
	
	private final int m_width;
	private final int m_height;
	private final LifeModel m_model;
	
	/*
	 * @brief	Constructor
	 * @param	model - the LifeView model to use for the simulation
	 */
	public LifeView(LifeModel model)
	{
		m_width = model.getWidth();
		m_height = model.getHeight();
		m_model = model;

		Dimension size = new Dimension(CELL_SIZE * (m_width - 1), CELL_SIZE * (m_height - 1));
		
		setSize(size);
		setPreferredSize(size);
		setVisible(true);
	}
	
	/*
	 * @brief	Force the life display to be updated (repainted)
	 */
	public void updateLifeDisplay()
	{
		repaint();
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * 
	 * @brief	Overridden function to paint the display
	 * @param	g - the graphics object with which to paint the display
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(DEAD_COLOR);
		g.fillRect(0, 0, CELL_SIZE * m_width, CELL_SIZE * m_height);
		if (m_model != null)
		{
			g.setColor(ALIVE_COLOR);
			for (int j = 0 ; j < m_height ; ++j)
			{
				for (int i = 0 ; i < m_width ; ++i)
				{
					if (m_model.getState(i, j))
					{
						g.fillRect(CELL_SIZE * i, CELL_SIZE * j, CELL_SIZE, CELL_SIZE);
					}	
				}
			}
		}
	}

}
