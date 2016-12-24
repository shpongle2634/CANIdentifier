package analyzer.idfilter;

import java.util.ArrayList;

import analyzer.logmanager.IDlog;
import analyzer.logmanager.Log;

public interface IDfilter {
	public boolean idFiltering(Log baseDB, Log eventLog);
	public ArrayList<IDlog> buildUpDB(ArrayList<String> rawlogs);
}
