package com.tissuebank.model;

import java.util.List;

public class VariousActions {

  private List<Action> active_actions;
  private List<Action> completed_actions;
  private List<Action> locked_actions;

  public List<Action> getActive_actions() {
	return active_actions;
}
public void setActive_actions(List<Action> active_actions) {
	this.active_actions = active_actions;
}
public List<Action> getCompleted_actions() {
	return completed_actions;
}
public void setCompleted_actions(List<Action> completed_actions) {
	this.completed_actions = completed_actions;
}
public List<Action> getLocked_actions() {
	return locked_actions;
}
public void setLocked_actions(List<Action> locked_actions) {
	this.locked_actions = locked_actions;
}
	
}