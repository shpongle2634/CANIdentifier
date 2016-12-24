package analyzer.controller;

import java.util.ArrayList;
import java.util.HashMap;

import analyzer.cmd.ActionCountCmd;
import analyzer.cmd.Cmd;
import analyzer.cmd.CrossCmd;
import analyzer.cmd.IntervalCmd;
import analyzer.cmd.Invoker;
import analyzer.idfilter.HashingIDfilter;
import analyzer.idfilter.IDfilter;
import analyzer.logmanager.IDlog;
import analyzer.logmanager.Log;
import analyzer.logmanager.LogManager;
import analyzer.logmanager.FileParser;

public class MainController {
	private static Invoker invoker= new Invoker(); // 커맨드를 실행할 객체
	private static IDfilter filter=new HashingIDfilter(); // ID Filtering을 실행할 객체 
	private static HashMap<String, Cmd> cmdMap= new HashMap<>(); //사용가능한 커맨드리스트를 담음
	private static FileParser parser=new FileParser();
	private static LogManager logmanager= new LogManager();
	private static MainController controller= new MainController();
        
        
	private MainController(){	
		//init cmd map
		//"Cross Matcher", "ActionCount Matcher", "Interval Matcher"
		cmdMap.put("ActionCount Matcher",new ActionCountCmd());
		cmdMap.put("Cross Matcher",new CrossCmd());
		cmdMap.put("Interval Matcher",new IntervalCmd());
	}

        public static MainController getSingleton(){
            return controller;
        }
	/* 함수이름 			: buildUpBaseDB
	 * 설명 				: No Event 로그를 읽어들여오도록 Logmanager에게 요청한다.
	 * String eventName : No Event로그의 이름
	 * String filePath 	: 파일 경로
	 * */
	public String buildUpBaseDB(String logName, String filePath){
		if(logmanager.getBaseDB()==null){
		//file Read and Get Raw logs
		ArrayList<String> rawlogs=parser.parseLogList(filePath);

		//create new Log object And filtering id.
		Log baselog=logmanager.makeLog(filter,logName, rawlogs);

		logmanager.setBaseDB(baselog); //and set baselog

				ArrayList<IDlog> tmp=logmanager.getBaseDB().getIdlogs();
				String result="Base LOG 아이디 리스트\n";
				
				for(IDlog i: tmp){
					result+=(i.getId() + " : " + i.getCount()+"\n");
				}
				
			System.out.println(result);	
		return result;		
		}
		else return "알림 : Base DB가 이미 존재합니다.";
	}

	/* 함수이름 			: addEventLog
	 * 설명 				: Event 로그를 읽어들여오도록 Logmanager에게 요청한다.
	 * String eventName : 로그의 이름
	 * String filePath 	: 파일 경로
	 * */
	public void addEventLog(String eventName, String filePath){
//		String result="Event LOG 아이디 리스트(필터링 적용)\n (아이디  : 로그 수)\n";
		ArrayList<String> rawlogs=parser.parseLogList(filePath);
		logmanager.makeLog(filter, eventName, rawlogs);

                System.out.println(eventName + " 로그 불러들임");
//		ArrayList<IDlog> tmp = logmanager.getLog(eventName).getIdlogs();
//		for(IDlog i: tmp){
//			result +=(i.getId() + " : " + i.getCount()+" \n");
//		}
//		return result;
	}
	
        public void applyFiltering(String eventname){
            Log baselog =logmanager.getBaseDB();
            Log eventlog =logmanager.getLog(eventname);
            if(baselog!=null ){
                if(eventlog !=null)
                 filter.idFiltering(baselog,eventlog );
                else
                    System.out.println("Error, Event 로그를 읽을 수 없습니다.");
            }
            else {
            System.out.println("Error, No Event 로그를 읽을 수 없습니다.");
            }
        }
        
	/* 함수이름 			: executeCmd
	 * 설명 				: Matcher 알고리즘을 적용하도록 Command를 실행하도록 Invoker에게 전달한다.
	 * String cmd 		: 실행할 커맨드 이름
	 * String target 	: 커맨드를 적용할 로그 이름
	 * Object[] params 	: 커맨드를 실행하기위해 필요한 파라미터들
	 * */
	public void executeCmd(String cmd, String target, Object[] params){
		
		Cmd c =null;
		c= cmdMap.get(cmd);
		Log targetlog=null;
		targetlog= logmanager.getLog(target);
		if(targetlog !=null && params!=null){
			if(c!=null){//success
				invoker.setCmd(c);
				invoker.invoke(targetlog, params);
			}
			else{
				System.out.println("can't not found the cmd");
			}
		}else{
			System.out.println("can't not found the log");
		}

	}
	public Log getLog(String eventname){
//		String result="Event LOG 아이디 리스트(필터링 적용)\n (아이디  : 로그 수)\n";
//		ArrayList<IDlog> tmp = logmanager.getLog(eventname).getIdlogs();
//		for(IDlog i: tmp){
//			result +=(i.getId()+"\n");
//			for(String s: i.getLogs())
//				result+=s+"\n";
//		}
//		return result;
                return logmanager.getLog(eventname);
	}
	public boolean isExist(String logname){
		return (logmanager.getLog(logname)==null)? false: true;
	}
}
