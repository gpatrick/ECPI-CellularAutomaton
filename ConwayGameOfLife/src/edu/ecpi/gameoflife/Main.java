/*
 * Author: Greg Patrick
 * 
 * Description: The Main class is the entry point for Conway's Game of Life. It uses the 
 * 				CellularAutomaton framework (which employs the MVC pattern) to execute 
 *				the following rules:
 *
 *				Live Cells:
 *					- If a cell has 0 or 1 neighbors, it dies of loneliness
 *					- If a cell has 4 or more neighbors, it dies of overcrowding
 *					- If a cell has 2 or 3 neighbors, it survives.
 *				Dead Cells:
 *					- If a cell has exactly 3 live neighbors, it is born 
 * 
 * Known Bugs: None
 */

package edu.ecpi.gameoflife;

import java.util.Timer;
import java.util.TimerTask;
import edu.ecpi.common.IController;
import edu.ecpi.common.INotifiable;
import java.util.Random; 

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length < 2){
			System.out.println("Usage: gameoflife rows cols");
			System.exit(1);
		}
		
		int rows = Integer.parseInt(args[0]);
		int cols = Integer.parseInt(args[1]);

		/*
		 * Create new automaton (the model in our MVC solution)
		 */
		GameOfLifeAutomaton model = new GameOfLifeAutomaton(rows, cols);
		
		/*
		 * Initialize automaton cells to random values (approximately 10% alive)
		 */
		Random rng = new Random();
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				int randvalue = rng.nextInt(100);
				if(randvalue < 10){
					model.setCell(i, j, GameOfLifeAutomaton.CELL_LIVE);
				}
				else{
					model.setCell(i, j, GameOfLifeAutomaton.CELL_DEAD);
				}
			}
		}
		
		/*
		 * Setup the view and controller. The GraphicalView accepts mouse clicks in order to 
		 * toggle a cell (by updating the model)... NOTE: The view is only updated when it is 
		 * told to update itself because the model changed, i.e, the GraphicalView does not change
		 * the cell's color internally as a result of the click
		 */
		Controller controller = new Controller();
		GraphicalView view = new GraphicalView();
		
		controller.registerModelObject(model);
		controller.registerViewObject(view);
		
		view.update(model, null);
		
		/*
		 * Setup a timer to fire a changed event every second. This causes the automaton to 
		 * automatically update its state.
		 */
		Timer t = new Timer();
		Task task = new Task(controller, model);
		t.scheduleAtFixedRate(task, 1000, 2000);
		
		view.setVisible(true);
	}
}

class Task extends TimerTask{
	protected IController controller;
	protected INotifiable model;
	
	public Task(IController controller, INotifiable model){
		this.controller = controller;
		this.model = model;
	}
	
	public void run(){
		controller.changed(model, "View");
	}
}
