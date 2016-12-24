package analyzer.logmanager;

import java.util.ArrayList;

public class IDlog {
	private String id;
	private ArrayList<String> logs;
	private int count;
	
	public IDlog(String id){
		this.id= id;
		count=1;
		logs=new ArrayList<>();
	}
	
	public String getId() {
		return id;
	}

	public ArrayList<String> getLogs() {
		return logs;
	}

	public void addLog(String log){
		logs.add(log);
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
