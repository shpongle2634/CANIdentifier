package analyzer.cmd;

import analyzer.datamatcher.ActionCountMatcher;
import analyzer.logmanager.Log;

public class ActionCountCmd implements Cmd {

	private ActionCountMatcher matcher;
	@Override
	public void execute(Log targetlog, Object[] params) {
		// TODO Auto-generated method stub
		System.out.println("execute : " + this.toString());
		Log log= targetlog;
		matcher= new ActionCountMatcher(log);
		matcher.doMatch(params);
		targetlog=matcher;
	}

}
