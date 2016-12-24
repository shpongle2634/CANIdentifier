package analyzer.datamatcher;

import java.util.ArrayList;

import analyzer.logmanager.IDlog;
import analyzer.logmanager.Log;

public class ActionCountMatcher extends Matcher{
	private Integer count;
	private ArrayList<IDlog> resultset=new ArrayList<>();
	public ActionCountMatcher(Log log) {
		super(log);
		// TODO Auto-generated constructor stub
		count=null;
	}

	@Override
	public void doMatch(Object[] params) {
		// TODO Auto-generated method stub
		if(params !=null){
			count =Integer.parseInt((String)params[0]);
		}
		if(count !=null){
			for(IDlog i :  log.getIdlogs()){
				if(i.getCount()== count){
					resultset.add(i);
				}
			}
			//리턴
			log.setIdlogs(resultset);

//			for(IDlog i : resultset){
//				System.out.println(i.getId());
//				for(String s: i.getLogs())
//					System.out.println(s);
//			}
		}
	}

}
