package edu.ecpi.common;

public interface INotifiable {
	public void fireChanged(String updateType);
	public void update(Object o, String updateType);
	public void setController(IController c);
	public IController getController();
}
