/**
 * Data model class for the game of Life.
 * 
 * @author Ryan Antkowiak (antkowiak@gmail.com)
 */
package com.ryanantkowiak.jLife;

public class LifeModel
{
	private boolean [] [] m_grid;
	
	private LifeModel()
	{
		m_grid = null;
	}
	
	public LifeModel(int width, int height)
	{
		m_grid = new boolean [width] [height];
		
		randomizeGrid();
	}
	
	public LifeModel(LifeModel rhs)
	{
		if (rhs != null && rhs.m_grid != null && rhs.m_grid[0] != null)
		{
			m_grid = new boolean [rhs.m_grid.length] [rhs.m_grid[0].length];
			
			for (int i = 0 ; i < rhs.m_grid.length ; ++i)
			{
				if (rhs.m_grid[i] != null)
				{
					for (int j = 0 ; j < rhs.m_grid[i].length ; ++j)
					{
						this.m_grid[i][j] = rhs.m_grid[i][j];
					}
				}
			}
		}
	}
	
	public boolean getState(int i, int j)
	{
		if (m_grid != null && i < m_grid.length && m_grid[i] != null && j < m_grid[i].length)
		{
			return m_grid[i][j];
		}
		
		return false;
	}
	
	public void setState(int i, int j, boolean state)
	{
		if (m_grid != null && i < m_grid.length && m_grid[i] != null && j < m_grid[i].length)
		{
			m_grid[i][j] = state;
		}
	}
	
	public void advanceGeneration()
	{
		LifeModel base = new LifeModel(this);
		
		zeroGrid();
		
		for (int i = 0 ; i < m_grid.length ; ++i)
		{
			for (int j = 0 ; j < m_grid[0].length ; ++j)
			{
				m_grid[i][j] = base.getNextGenerationState(i, j);
			}
		}
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		if (m_grid != null && m_grid.length > 0 && m_grid[0] != null)
		{
			for (int i = 0 ; i < m_grid.length ; ++i)
			{
				for (int j = 0 ; j < m_grid[0].length ; ++j)
				{
					if (m_grid[i][j])
					{
						sb.append("*");
					}
					else
					{
						sb.append("_");
					}
				}
				sb.append("\n");
			}
		}
		
		return sb.toString();
	}
	
	private boolean getNextGenerationState(int i, int j)
	{
		if (		m_grid == null
				||	m_grid.length <= 0
				||	m_grid[0] == null
				||	m_grid[0].length <= 0)
		{
			return false;
		}
		
		int neighbors = 0;
		
		if (m_grid[normalizeI(i-1)][normalizeJ(j-1)]) ++neighbors;
		if (m_grid[normalizeI(i-1)][normalizeJ(j)])   ++neighbors;
		if (m_grid[normalizeI(i-1)][normalizeJ(j+1)]) ++neighbors;
		if (m_grid[normalizeI(i)]  [normalizeJ(j-1)]) ++neighbors;
		if (m_grid[normalizeI(i)]  [normalizeJ(j+1)]) ++neighbors;
		if (m_grid[normalizeI(i+1)][normalizeJ(j-1)]) ++neighbors;
		if (m_grid[normalizeI(i+1)][normalizeJ(j)])   ++neighbors;
		if (m_grid[normalizeI(i+1)][normalizeJ(j+1)]) ++neighbors;
	
		// 1. Any life cell with fewer than two live neighbors dies from under-population
		if (m_grid[i][j] && neighbors < 2)
			return false;
		
		// 2. Any live cell with two or three live neighbors lives on to the next generation
		if (m_grid[i][j] && (neighbors == 2 || neighbors == 3) )
			return true;
		
		// 3. Any live cell with more than three live neighbors dies from over-population
		if (m_grid[i][j] && neighbors > 3)
			return false;
		
		// 4. Any dead cell with exactly three live neighbors becomes a live cell by reproduction
		if (m_grid[i][j] == false && neighbors == 3)
			return true;
		
		return false;
	}
	
	private int normalizeI(int i)
	{
		if (i < 0)
		{
			i = m_grid.length - 1;
		}
		
		if (i >= m_grid.length)
		{
			i = 0;
		}
		
		return i;
	}
	
	private int normalizeJ(int j)
	{
		if (j < 0)
		{
			j = m_grid.length - 1;
		}
		
		if (j >= m_grid[0].length)
		{
			j = 0;
		}
		
		return j;
	}
	
	public void zeroGrid()
	{
		if (null != m_grid)
		{
			for (int i = 0 ; i < m_grid.length ; ++i)
			{
				if (m_grid[i] != null)
				{
					for (int j = 0 ; j < m_grid[i].length ; ++j)
					{
						m_grid[i][j] = false;
					}
				}
			}
		}
	}
	
	private void randomizeGrid()
	{
		if (null != m_grid)
		{
			for (int i = 0 ; i < m_grid.length ; ++i)
			{
				if (m_grid[i] != null)
				{
					for (int j = 0 ; j < m_grid[i].length ; ++j)
					{
						m_grid[i][j] = Math.random() >= 0.5f;
					}
				}
			}
		}
	}
	
	
}
