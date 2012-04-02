package edu.ecpi.oddneighbors;

import acm.graphics.*;

public class Cell extends GRect{
	protected int row;
	protected int col;
	
	public Cell(double x, double y, double width, double height, int row, int col){
		super(x, y, width, height);
		this.row = row;
		this.col = col;
	}
	
	public int getRow(){
		return row;
	}
	
	public void setRow(int row){
		this.row = row;
	}
	
	public int getCol(){
		return col;
	}
	
	public void setCol(int col){
		this.col = col;
	}
}
