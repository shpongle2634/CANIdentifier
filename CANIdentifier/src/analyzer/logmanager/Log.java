package analyzer.logmanager;

import java.util.ArrayList;

public abstract class Log {
	protected ArrayList<IDlog> idlogs;
	protected ArrayList<String> rawlogs ;
	public ArrayList<IDlog> getIdlogs() {
		return idlogs;
	}
	public void setIdlogs(ArrayList<IDlog> idlogs) {
		this.idlogs = idlogs;
	}
	public ArrayList<String> getRawlogs() {
		return rawlogs;
	}
	public void setRawlogs(ArrayList<String> rawlogs) {
		this.rawlogs = rawlogs;
	}
        
        public int getTotalPacketCounts(){
            int count = 0;
            for(IDlog i : idlogs){
             count+=i.getCount();
            }
            return count;
        }
        
        public String getTotalTime(){
//            System.out.println( rawlogs.get(rawlogs.size()-1).substring(0,11));
        return rawlogs.get(rawlogs.size()-1).substring(2,11);
        }
       
       public int getSenderCounts(){
           return idlogs.size();
       }
}
