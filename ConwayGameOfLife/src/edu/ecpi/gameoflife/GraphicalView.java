package edu.ecpi.gameoflife;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import acm.graphics.*;
import edu.ecpi.cellularautomaton.CellularAutomaton;
import edu.ecpi.common.*;


public class GraphicalView extends JFrame implements INotifiable, MouseListener{
	private static final long serialVersionUID = 1L;
	protected IController controller;
	protected GCanvas canvas;
	protected Object model;
	protected Cell cell;
	protected Cell[][] cellGrid;
	
	public GraphicalView(){
		//System.out.println("ContentPane width:" + getContentPane().getWidth());
		//System.out.println("ContentPane height:" + getContentPane().getHeight());
		canvas = new GCanvas();
		this.getContentPane().add(BorderLayout.CENTER, canvas);
		canvas.setSize(1024,768);
		setMinimumSize(new Dimension(1024, 768));
	}
	
	@Override
	public void fireChanged(String arg0) {
		controller.changed(cell, "View");
	}

	@Override
	public IController getController() {
		return controller;
	}

	@Override
	public void setController(IController arg0) {
		controller = arg0;
	}

	@Override
	public void update(Object arg0, String arg1) {
		//System.out.println("GraphicalView: update(" + arg0);
		CellularAutomaton model = (CellularAutomaton) arg0;
		int rows = model.getRows();
		int cols = model.getCols();
		
		
		int canvasWidth = canvas.getWidth();
		int canvasHeight = canvas.getHeight();
		
		int rectWidth = canvasWidth/cols;
		int rectHeight = canvasHeight/rows;
		
		if(cellGrid == null){
			initializeCellGrid(rows, cols, rectWidth, rectHeight);
		}
				
		for(int row = 0; row < rows; row++){
			for(int col = 0; col < cols; col++){
				if(model.getCell(row, col) % 2 == 0){//Even
					cellGrid[row][col].setFillColor(Color.blue);
				}
				else{
					cellGrid[row][col].setFillColor(Color.white);
				}
				cellGrid[row][col].setFilled(true);
			}
		}
		this.repaint();
	}
	
	public void initializeCellGrid(int rows, int cols, int width, int height){
		cellGrid = new Cell[rows][cols];
		for(int row = 0; row < rows; row++){
			for(int col = 0; col < cols; col++){
				cellGrid[row][col] = new Cell(col*width, row*height, width, height, row, col);
				canvas.add(cellGrid[row][col]);
				cellGrid[row][col].addMouseListener(this);
			}
		}
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(640, 480);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		cell = (Cell) event.getSource();
		//System.out.println("Cell is at: (" + cell.getRow() + "," + cell.getCol() + ")" );
		fireChanged(null);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
