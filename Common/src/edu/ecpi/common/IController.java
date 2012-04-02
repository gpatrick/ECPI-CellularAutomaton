package edu.ecpi.common;

public interface IController {
	public void changed(Object o, String updateType);
	public void registerModelObject(INotifiable o);
	public void registerViewObject(INotifiable o);
}
