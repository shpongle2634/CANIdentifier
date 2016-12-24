package analyzer.idfilter;

import java.util.ArrayList;
import java.util.HashSet;


import analyzer.logmanager.IDlog;
import analyzer.logmanager.Log;

public class HashingIDfilter implements IDfilter {

	@Override
	public boolean idFiltering(Log baseDB, Log eventLog) {
		// TODO Auto-generated method stub
		System.out.println("idfiltering....");
		
		ArrayList<IDlog> baseIDlogs= baseDB.getIdlogs();
		ArrayList<IDlog> eventIDlogs = eventLog.getIdlogs();
		
		for(IDlog i : baseIDlogs){
			for(int x=0; x<eventIDlogs.size(); x++){
				if(eventIDlogs.get(x).getId().equals(i.getId())){
					eventIDlogs.remove(x);
				}
			}
		}
		return true;
	}

	@Override
	public ArrayList<IDlog> buildUpDB(ArrayList<String> rawlogs) {
		// TODO Auto-generated method stub
		System.out.println("buildUpDB....");
		ArrayList<IDlog> idlogs=new ArrayList<IDlog>();
		IDlog idlog=null;
		HashSet<String> ids= new HashSet<String>(); //using hash
		
		
		for(String s : rawlogs){
			String rawlog= s;
			s= s.substring(15,s.length());
			String fields[] = s.split(" ");//필드를 분리하기위해

			if(fields[0].length()<3||fields[0].contains("ErrorFrame") //필요없는 로그
					||fields[0].contains("ErrorFrame")||fields[0].contains("Statistic:"));
			else{
				if(!ids.contains(fields[0])){ //새아이디 : 새 IDlog 생성
					idlog=new IDlog(fields[0]);
					idlog.addLog(rawlog);
					idlogs.add(idlog); //실제 리스트
					ids.add(fields[0]); 
				}
				else{ //이미 있는 아이디 : 로그추가 랑 카운트 증가
					for(IDlog i: idlogs){
						if(i.getId().equals(fields[0])){
							i.addLog(rawlog); i.setCount(i.getCount()+1);
							break;
						}
					}
				}
			}
		}//id list만듬
//		private String id;
//		private ArrayList<String> logs;
//		private int count;
		
		return idlogs;
	}

}
