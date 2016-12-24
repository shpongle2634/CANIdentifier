package analyzer.cmd;

import analyzer.datamatcher.CrossMatcher;
import analyzer.logmanager.Log;

public class CrossCmd implements Cmd {

	private CrossMatcher matcher;
	@Override
	public void execute(Log targetlog, Object[] params) {
		// TODO Auto-generated method stub
		System.out.println("execute : " + this.toString());
		Log log= targetlog;
		matcher= new CrossMatcher(log);
		matcher.doMatch(params);
		targetlog=matcher;
	}

}
