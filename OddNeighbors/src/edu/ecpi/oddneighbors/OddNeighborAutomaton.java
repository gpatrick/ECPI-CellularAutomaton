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
	
	public void update(Object o, String updateType){
		if(o.getClass() == Cell.class){ //Is this a cell class
			Cell cell = (Cell) o;
			int currentCellValue = getCell(cell.getRow(), cell.getCol());
			setCell(cell.getRow(), cell.getCol(), currentCellValue + 1);
			fireChanged(updateType);
		}
		else{
			//System.out.println("Model: changed(" + o + ", " + updateType + ")");
			step();
			fireChanged(updateType);
		}
	}
}
