package edu.ecpi.gameoflife;

import edu.ecpi.cellularautomaton.CellularAutomaton;
import edu.ecpi.cellularautomaton.CellularAutomatonConstants;

public class GameOfLifeAutomaton extends CellularAutomaton {
	public static final int CELL_DEAD = 0;
	public static final int CELL_LIVE = 1;
	
	public GameOfLifeAutomaton(int rows, int cols) {
		super(rows, cols);
	}

	/*
	 * Note: The neighbors array is a clockwise walk around the current cell. The zeroth element
	 * 		 is the current element. A value of NO_VALUE, means that those cells are off of the 
	 * 		 bounds of the automaton, and should be ignored.
	 * @see edu.ecpi.cellularautomaton.CellularAutomaton#computeState(int[])
	 */
	protected int computeState(int neighbors[]){
		int numberOfLiveNeighbors = 0;
		for(int i = 1; i < neighbors.length; i++){
			if(neighbors[i] != CellularAutomatonConstants.NO_VALUE){
				if(neighbors[i] == GameOfLifeAutomaton.CELL_LIVE){ 
					numberOfLiveNeighbors++;
				}
			}
		}
		if(neighbors[0] == GameOfLifeAutomaton.CELL_LIVE){
			if(numberOfLiveNeighbors == 0 || numberOfLiveNeighbors == 1 || numberOfLiveNeighbors > 3){
				return CELL_DEAD;
			}
		}
		else{
			if(numberOfLiveNeighbors != 3){
				return CELL_DEAD;
			}
		}

		return CELL_LIVE;
	}
	
	/*
	 * The update method handles two cases. 
	 *    Case 1: The object being updated is a cell. This happens as a result of the user 
	 *    		  clicking a cell in the view. In this case, convert the cell from live to dead
	 *    		  (or vice versa) and let the controller know that a cell changed, and
	 *    
	 *    Case 2: Update was called as a result of the timer firing. In this case, simply step
	 *    		  the automaton and notify the controller that the state has changed.
	 * 
	 * @see edu.ecpi.cellularautomaton.CellularAutomaton#update(java.lang.Object, java.lang.String)
	 */
	public void update(Object o, String updateType){
		if(o.getClass() == Cell.class){ //Is this object a cell class
			Cell cell = (Cell) o;
			int currentCellValue = getCell(cell.getRow(), cell.getCol());
			setCell(cell.getRow(), cell.getCol(), currentCellValue + 1);
			fireChanged(updateType);
		}
		else{
			step();
			fireChanged(updateType);
		}
	}
}
