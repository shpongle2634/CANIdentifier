package analyzer.cmd;

import analyzer.logmanager.Log;

public interface Cmd {
	public void execute(Log targetlog, Object[] params);
}
