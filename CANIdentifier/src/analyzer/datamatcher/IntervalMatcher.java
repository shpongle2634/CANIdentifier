package analyzer.datamatcher;

import java.util.ArrayList;

import analyzer.logmanager.IDlog;
import analyzer.logmanager.Log;

public class IntervalMatcher extends Matcher{

	Double interval;
	Double errorRange;
	private ArrayList<IDlog> idlogs;
	public IntervalMatcher(Log log) {
		super(log);
		// TODO Auto-generated constructor stub
		idlogs=log.getIdlogs();
		interval=null;
		errorRange=(double) 0.1;
	}

	@Override
	public void doMatch(Object[] params) {
		// TODO Auto-generated method stub

		ArrayList<IDlog> found=new ArrayList<>();
		interval=Double.parseDouble((String)params[0]);
                if(params.length ==2)
                    errorRange=Double.parseDouble((String)params[1]);
		if(params!=null){
			
			for(IDlog i: idlogs){ //각 아이디마다 로그들을 살핀다.
				ArrayList<String>logs= i.getLogs();
				String log1=null, log2=null, time1=null, time2=null;
				for(int m =0; m< logs.size()-1; m++){//로그들 사이에 interval에 해당하는 애들을 매칭.
					log1=logs.get(m);
					String[] fields1= log1.split(" ");
					time1=fields1[2].length()>2 ?fields1[2] : fields1[3];
					//				System.out.println(log1);
					//				System.out.println("fields1[2] : "+fields1[2]);
					//				System.out.println("fields1[3] : "+fields1[3]);

					for(int n=m+1; n < logs.size(); n++){
						log2=logs.get(n);
						String[] fields2= log2.split(" ");
						time2=fields2[2].length()>2 ?fields2[2] : fields2[3];
						//					System.out.println(log2);
						//					System.out.println("fields2[2] : "+fields2[2]);
						//					System.out.println("fields2[3] : "+fields2[3]);
						double result=Double.parseDouble(time2) - Double.parseDouble(time1);
						if(result >= interval- (errorRange) && result <= interval+(errorRange)) //오차범위 적용
							found.add(i);
					}
				}
			}
			log.setIdlogs(found);

//			for(IDlog i :found){
//				System.out.println(i.getId());
//				for(String s: i.getLogs())
//					System.out.println(s);
//			}
		}

	}

}
