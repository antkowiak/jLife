/**
 * Data model class for the game of Life.
 * 
 * @author Ryan Antkowiak (antkowiak@gmail.com)
 */
package com.ryanantkowiak.jLife;

/*
 * @brief	LifeModel class
 */
public class LifeModel
{
	/*
	 * @brief	2D array grid of booleans to keep track of live cells
	 */
	private boolean [] [] m_grid;
	
	/*
	 * @brief	Default constructor.  Private, should not be used.
	 */
	private LifeModel()
	{
		m_grid = null;
	}
	
	/*
	 * @brief	Constructor
	 * @param	width - the number of cells wide to simulate
	 * @param	height - the number of cells tall to simulate
	 */
	public LifeModel(int width, int height)
	{
		m_grid = new boolean [width] [height];
		
		randomizeGrid();
	}
	
	/*
	 * @brief	Copy constructor
	 * @param	rhs - the object to copy
	 */
	public LifeModel(LifeModel rhs)
	{
		// If rhs is a valid object, do a deep copy of the 2D array
		if (rhs.isValid())
		{
			m_grid = new boolean [rhs.m_grid.length] [rhs.m_grid[0].length];
			
			for (int i = 0 ; i < rhs.m_grid.length ; ++i)
			{
				for (int j = 0 ; j < rhs.m_grid[i].length ; ++j)
				{
					this.m_grid[i][j] = rhs.m_grid[i][j];
				}
			}
		}
	}
	
	/*
	 * @brief	Return the number of cells wide
	 * @return	int - the number of cells wide
	 */
	public int getWidth()
	{
		if (!isValid())
			return 0;
		
		return m_grid.length;
	}
	
	/*
	 * @brief	Return the numbers of cells high
	 * @return	int - the number of cells high
	 */
	public int getHeight()
	{
		if (!isValid())
			return 0;
		
		return m_grid[0].length;
	}
	
	/*
	 * @brief	Return the state of the cell at a location
	 * @param	i - the first array index location
	 * @param	j - the second array index location
	 * @return	boolean - true if the cell is alive
	 */
	public boolean getState(int i, int j)
	{
		if (isValid())
		{
			return m_grid[i][j];
		}
		
		return false;
	}
	
	/*
	 * @brief	Set the state of the cell at a given location
	 * @param	i - the first array index location
	 * @param	j - the second array index location
	 * @param	state - the state to set the cell (alive=true)
	 */
	public void setState(int i, int j, boolean state)
	{
		if (isValid())
		{
			m_grid[i][j] = state;
		}
	}
	
	/*
	 * @brief	Advance the Life game by one generation
	 */
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
	
	/*
	 * @(non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 
	 * @brief	Return a string representation of this object
	 * @return	String - the string representation of the object
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		if (isValid())
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
	
	/*
	 * @brief	Determine if the internal data structure (grid) is valid
	 * @return	boolean - true if the data is valid
	 */
	private boolean isValid()
	{
		if (m_grid == null)
			return false;
		
		if (m_grid.length <= 0)
			return false;
		
		if (m_grid[0] == null)
			return false;
		
		if (m_grid[0].length <= 0)
			return false;
		
		return true;
	}
	
	/*
	 * @brief	Get the state of the cell at (i, j) for the next generation
	 * @param	i - the first array index location
	 * @param 	j - the second array index location
	 * @return	boolean - true if the cell is alive in the next generation
	 */
	private boolean getNextGenerationState(int i, int j)
	{
		if (!isValid())
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
	
	/*
	 * @brief	Normalize an 'i' coordinate.  Ensure it is not negative and does not exceed the width
	 * @param	i - the 'i' coordinate of the 2D array
	 * @return	int - the normalized value
	 */
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
	
	/*
	 * @brief	Normalize a 'j' coordinate.  Ensure it is not negative and does not exceed the height
	 * @param	j - the 'j' coordinate of the 2D array
	 * @return	int - the normalized value
	 */
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
	
	/*
	 * @brief	Zero out the grid, make all cells dead.
	 */
	public void zeroGrid()
	{
		if (isValid())
		{
			for (int i = 0 ; i < m_grid.length ; ++i)
			{
				for (int j = 0 ; j < m_grid[i].length ; ++j)
				{
					m_grid[i][j] = false;
				}
			}
		}
	}
	
	/*
	 * @brief	Randomize the grid.  Initialize each cell with the probability P(0.5) if the cell is alive.
	 */
	private void randomizeGrid()
	{
		for (int i = 0 ; i < m_grid.length ; ++i)
		{
			for (int j = 0 ; j < m_grid[i].length ; ++j)
			{
				m_grid[i][j] = Math.random() >= 0.5f;
			}
		}
	}
	
}
