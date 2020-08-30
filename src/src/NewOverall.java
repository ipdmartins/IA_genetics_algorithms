package src;

import java.util.ArrayList;

public class NewOverall {
	
	private ArrayList<Integer> idList = new ArrayList<Integer>();
	private ArrayList<Integer> newOverall = new ArrayList<Integer>();
	
	public ArrayList<Integer> getIdList() {
		return idList;
	}
	public void setIdList(int id) {
		this.idList.add(id);
	}
	public ArrayList<Integer> getNewOverall() {
		return newOverall;
	}
	public void setNewOverall(int overall) {
		this.newOverall.add(overall);
	}

	
}
