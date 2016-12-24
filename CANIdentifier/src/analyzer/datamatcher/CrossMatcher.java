package analyzer.datamatcher;

import analyzer.controller.MainController;
import analyzer.idfilter.HashingIDfilter;
import analyzer.logmanager.Log;

public class CrossMatcher extends Matcher{
        
        private HashingIDfilter filter= new HashingIDfilter();
        private MainController con = MainController.getSingleton();
        
	public CrossMatcher(Log log) {
		super(log);
		// TODO Auto-generated constructor stub
                
	}

	@Override
	public void doMatch(Object[] params) {
		// TODO Auto-generated method stub
		for(Object compare :  params){
                    con.addEventLog("cross"+(String)compare, "./log/"+(String)compare);
                    Log comparelog = con.getLog((String) "cross"+compare);
                    filter.idFiltering(comparelog, log);
                }
                
	}

}
