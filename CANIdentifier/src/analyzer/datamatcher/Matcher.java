package analyzer.datamatcher;

import analyzer.logmanager.Log;

public abstract class Matcher extends Log {
	protected Log log;
	public Matcher(Log log){
		this.log = log;
	}
	
	public abstract void doMatch(Object[] params);
}
