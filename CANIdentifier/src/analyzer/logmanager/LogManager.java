package analyzer.logmanager;

import java.util.ArrayList;
import java.util.HashMap;

import analyzer.idfilter.IDfilter;

public class LogManager {
	private HashMap<String,Log> logmap=null;
	private Log baseDB=null;
	
	public LogManager(){
		logmap=new HashMap<String, Log>();
	}
	public Log makeLog(IDfilter filter,String eventName, ArrayList<String> rawLogs){
		Log log =new EventLog();
		log.setRawlogs(rawLogs);// make new log object
		
		log.setIdlogs(filter.buildUpDB(rawLogs)); //bulid up id list
		
//		System.out.println(log);
		logmap.put(eventName, log); //save

		return log;
	}
	
	public Log getLog(String name){
		return logmap.get(name);
	}

	public Log getBaseDB() {
		return baseDB;
	}

	public void setBaseDB(Log baseDB) {
		this.baseDB = baseDB;
	}
	
	
}
