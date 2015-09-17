/**
 * View class for the game of Life.
 * 
 * @author Ryan Antkowiak (antkowiak@gmail.com)
 */
package com.ryanantkowiak.jLife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

public class LifeView extends JComponent
{
	private static final int CELL_SIZE = 10;
	private static final Color DEAD_COLOR = Color.ORANGE;
	private static final Color ALIVE_COLOR = Color.BLACK;
	
	private final int m_width;
	private final int m_height;
	private final LifeModel m_model;
	
	public LifeView(int width, int height, LifeModel model)
	{
		m_width = width;
		m_height = height;
		m_model = model;

		Dimension size = new Dimension(CELL_SIZE * m_width, CELL_SIZE * m_height);
		
		setSize(size);
		setPreferredSize(size);
		setVisible(true);
	}
	
	public void updateLifeDisplay()
	{
		repaint();
	}
	
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
