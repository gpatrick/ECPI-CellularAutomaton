/*
 * Author: Greg Patrick
 * 
 * Description: The Main class is the entry point for the OddNeighbors program. It uses the 
 * 				CellularAutomaton framework (which employs the MVC pattern) to execute a 
 * 				simple program where each cell sets its value to the sum of the values of its 
 * 				neighbors that contain odd values.
 * 
 * Known Bugs: None
 */

package edu.ecpi.oddneighbors;

import java.util.Timer;
import java.util.TimerTask;
import edu.ecpi.common.IController;
import edu.ecpi.common.INotifiable;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length < 2){
			System.out.println("Usage: oddneighbors rows cols");
			System.exit(1);
		}
		
		int rows = Integer.parseInt(args[0]);
		int cols = Integer.parseInt(args[1]);

		/*
		 * Create new automaton (the model in our MVC solution)
		 */
		OddNeighborAutomaton model = new OddNeighborAutomaton(rows, cols);
		
		/*
		 * Initialize automaton (cells are numbered row by row from 0 to (rows * cols) 
		 */
		int counter = 0;
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				model.setCell(i, j, counter++);
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
