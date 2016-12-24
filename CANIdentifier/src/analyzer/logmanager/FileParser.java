package analyzer.logmanager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileParser {
	
	public ArrayList<String> parseLogList(String filePath){
		BufferedReader freader=null;
		//파일 오픈
		freader=openLog(filePath);
		//로그 저장
		ArrayList<String> loglist=parsing(freader);
		//파일 닫기
		closeLog(freader);
		//로그 반환.
		return loglist;
	}
	
	private ArrayList<String> parsing(BufferedReader freader){
		//parsing.
		String log;
		ArrayList<String> loglist=new ArrayList<String>();
		try {
			int lineindex=0;
			while ((log = freader.readLine()) != null) {
				if(lineindex++ >5)//필요없는 부분 삭제
				loglist.add(log); //로그 저장
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loglist.remove(loglist.size()-1);
		return loglist;
	}
	
	private BufferedReader openLog(String filepath){
		BufferedReader freader=null;
		try {
			freader=new BufferedReader(new FileReader(filepath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return freader;
	}
	
	private void closeLog(BufferedReader freader){
		try {
			freader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

