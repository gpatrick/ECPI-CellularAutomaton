package edu.ecpi.gameoflife;

import edu.ecpi.common.IController;
import edu.ecpi.common.INotifiable;
import java.util.Vector;

public class Controller implements IController{
	protected Vector<INotifiable> modelObjects;
	protected Vector<INotifiable> viewObjects;
	protected Cell cell;

	public Controller(){
		modelObjects = new Vector<INotifiable>();
		viewObjects = new Vector<INotifiable>();
	}
	
	/*
	 * If cell attribute is null, update the model. Otherwise
	 * update (toggle) the cell.
	 * 
	 * @see edu.ecpi.common.IController#changed(java.lang.Object, java.lang.String)
	 */
	public void changed(Object sender, String updateType) {
		//System.out.println("Controller: changed(" + sender + ", " + updateType + ")");
		if(updateType.equalsIgnoreCase("Model")){
			for(INotifiable object : viewObjects){
				//if(cell != null){
				//	object.update(cell, "View");
				//}
				//else{
					object.update(sender, "View");
				//}
			}
		}
		else if(updateType.equalsIgnoreCase("View")){
			for(INotifiable object : modelObjects){
				object.update(sender, "Model");
			}
		}
	}

	public void registerModelObject(INotifiable arg0) {
		if(!modelObjects.contains(arg0)){
			modelObjects.add(arg0);
			arg0.setController(this);
		}
	}

	public void registerViewObject(INotifiable arg0) {
		if(!viewObjects.contains(arg0)){
			viewObjects.add(arg0);
			arg0.setController(this);
		}
	}
}
