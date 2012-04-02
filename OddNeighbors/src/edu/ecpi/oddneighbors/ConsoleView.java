package edu.ecpi.oddneighbors;

import edu.ecpi.common.*;

public class ConsoleView implements INotifiable{

	protected IController controller;
	
	@Override
	public void fireChanged(String arg0) {
		System.out.println("View: fireChanged(" + arg0 + ")");
		// TODO Auto-generated method stub
		
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
	public void update(Object model, String changeType) {
		System.out.println("View: update(" + model + ", " + changeType + ")");
		System.out.println(model);
	}

}
