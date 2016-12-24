package analyzer.cmd;

import java.util.ArrayList;

import analyzer.logmanager.Log;

public class Invoker {
	private ArrayList<Cmd> cmdlist;
	private Cmd current;
	public Invoker(){
		cmdlist=new ArrayList<>();
		current=null;
	}
	
	
	public void invoke(Log targetlog, Object[] params){
//		System.out.println("invoke : " +obj.toString());
		if(current!=null){
			current.execute(targetlog, params);
		}
	}
	
	public void setCmd(Cmd cmd){
//		System.out.println("setCmd : "+cmd.toString());
		this.current= cmd;
	}
	
}
