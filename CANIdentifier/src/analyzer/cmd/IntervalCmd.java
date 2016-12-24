package analyzer.cmd;

import analyzer.datamatcher.IntervalMatcher;
import analyzer.logmanager.Log;

public class IntervalCmd implements Cmd {

	private IntervalMatcher matcher;
	@Override
	public void execute(Log targetlog, Object[] params) {
		// TODO Auto-generated method stub
		System.out.println("execute : " + this.toString());
		Log log=  targetlog;
		matcher= new IntervalMatcher(log);
		matcher.doMatch(params);
		targetlog=matcher;
	}

}
