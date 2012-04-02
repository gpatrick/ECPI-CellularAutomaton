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
			System.out.println("Usage: oddneighbors row col");
			System.exit(1);
		}
		
		int rows = Integer.parseInt(args[0]);
		int cols = Integer.parseInt(args[1]);

		/*
		 * Create new automaton
		 */
		OddNeighborAutomaton model = new OddNeighborAutomaton(rows, cols);
		
		/*
		 * Initialize automaton
		 */
		int counter = 0;
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				model.setCell(i, j, counter++);
			}
		}
		
		//System.out.println("Before: \n" + model);
		
		/*
		 * Compute one time step
		 */
		//model.step();
		
//		System.out.println("After: \n" + model);
		
		Controller controller = new Controller();
		//ConsoleView view = new ConsoleView();
		
		GraphicalView view = new GraphicalView();
		
		controller.registerModelObject(model);
		controller.registerViewObject(view);
		//controller.changed(model, "View");
		view.update(model, null);
		
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
