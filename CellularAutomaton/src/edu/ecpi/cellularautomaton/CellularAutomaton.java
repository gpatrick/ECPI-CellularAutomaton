package edu.ecpi.cellularautomaton;

import edu.ecpi.common.INotifiable;
import edu.ecpi.common.IController;

public class CellularAutomaton implements INotifiable{
	protected int rows;
	protected int cols;
	protected int time;
	protected ColorMap colorMap;
	protected int[][] grid1;
	protected int[][] grid2;
	protected int[][] currentGrid;
	protected int[][] nextGrid;
	
	/*
	 * Attributes supporting INotifiable
	 */
	protected IController controller;
	
	public CellularAutomaton(int rows, int cols){
		this.rows = rows + 2;
		this.cols = cols + 2;
		initialize();
		int lastRow = this.rows - 1;
		int lastCol = this.cols - 1;
		
		for(int i = 0; i < this.cols; i++){
			grid1[0][i] = CellularAutomatonConstants.NO_VALUE;
			grid1[lastRow][i] = CellularAutomatonConstants.NO_VALUE;
			grid2[0][i] = CellularAutomatonConstants.NO_VALUE;
			grid2[lastRow][i] = CellularAutomatonConstants.NO_VALUE;
		}
		
		for(int i = 0; i < this.rows; i++){
			grid1[i][0] = grid2[i][0] = CellularAutomatonConstants.NO_VALUE;
			grid1[i][lastCol] = grid2[i][lastCol] = CellularAutomatonConstants.NO_VALUE;
		}
	}
	
	protected void initialize(){
		this.time = 0;
		this.colorMap = null;
		this.grid1 = new int[rows][cols];
		this.grid2 = new int[rows][cols];
		this.currentGrid = grid1;
		this.nextGrid = grid2;
	}
	
	protected boolean inBounds(int row, int col){
		if(row > 0 && row < rows - 1 && col > 0 && col < cols - 1){
			return true;
		}
		return false;
	}
	
	public int[][] getGrid(){
		int[][] result = new int[rows-2][cols-2];
		for(int row = 1; row < rows-1; row++){
			for(int col = 1; col < cols-1; col++)
				result[row-1][col-1] = currentGrid[row][col];
		}
		return result;
	}
	
	public int getCell(int row, int col){
		int tempRow = row + 1;
		int tempCol = col + 1;
		if(inBounds(tempRow, tempCol)){
			return currentGrid[tempRow][tempCol];
		}
		return CellularAutomatonConstants.OUT_OF_BOUNDS;
	}
	
	public void setCell(int row, int col, int value){
		int tempRow = row + 1;
		int tempCol = col + 1;
		if(inBounds(tempRow, tempCol)){
			currentGrid[tempRow][tempCol] = value;
		}
	}
	
	public int getRows(){
		return rows - 2;
	}

	public int getCols(){
		return cols - 2;
	}
	
	public void reset(){
		initialize();
	}

	public ColorMap getColorMap(){
		return colorMap;
	}
	
	public void setColorMap(ColorMap colorMap){
		this.colorMap = colorMap;
	}
	
	protected int computeState(int cells[]){
		return CellularAutomatonConstants.NO_VALUE;
	}
	
	public void step(){
		int tempArray[] = new int[CellularAutomatonConstants.NEIGHBOR_COUNT];
		for(int row = 1; row < rows - 1; row++){
			for(int col = 1; col < cols - 1; col++){
				tempArray[0] = currentGrid[row][col];
				tempArray[1] = currentGrid[row-1][col-1];
				tempArray[2] = currentGrid[row-1][col];
				tempArray[3] = currentGrid[row-1][col+1];
				tempArray[4] = currentGrid[row][col+1];
				tempArray[5] = currentGrid[row+1][col+1];
				tempArray[6] = currentGrid[row+1][col];
				tempArray[7] = currentGrid[row+1][col-1];
				tempArray[8] = currentGrid[row][col-1];
				nextGrid[row][col] = computeState(tempArray);
				time++;
			}
		}
		//Swap Grids
		int temp[][] = currentGrid;
		currentGrid = nextGrid;
		nextGrid = temp;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		for(int row = 1; row < rows - 1; row++){
			for(int col = 1; col < cols - 1; col++){
				s.append(currentGrid[row][col] + "\t");
			}
			s.append("\n");
		}
		return s.toString();
	}

	@Override
	public void fireChanged(String updateType) {
		//System.out.println("Model: fireChanged(" + updateType + ")");
		controller.changed(this, updateType);		
	}

	@Override
	public void update(Object o, String updateType) {
		//System.out.println("Model: changed(" + o + ", " + updateType + ")");
		//step();
		//fireChanged(updateType);
	}
	
	public void setController(IController controller){
		this.controller = controller;
	}
	
	public IController getController(){
		return controller;
	}
}
