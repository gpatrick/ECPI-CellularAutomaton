package edu.ecpi.oddneighbors;

import edu.ecpi.cellularautomaton.CellularAutomaton;
import edu.ecpi.cellularautomaton.CellularAutomatonConstants;

public class OddNeighborAutomaton extends CellularAutomaton {

	public OddNeighborAutomaton(int rows, int cols) {
		super(rows, cols);
	}

	protected int computeState(int neighbors[]){
		int result = 0;
		for(int i = 1; i < neighbors.length; i++){
			if(neighbors[i] != CellularAutomatonConstants.NO_VALUE){
				if(neighbors[i] % 2 != 0){ //Number is odd
					result++;
				}
			}
		}
		return result;
	}
	
	/*
	 * The update method handles two cases. 
	 *    Case 1: The object being updated is a cell. This happens as a result of the user 
	 *    		  clicking a cell in the view. In this case, convert the cell from even to odd
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
