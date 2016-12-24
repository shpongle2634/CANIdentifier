/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer.view;

import analyzer.controller.MainController;
import analyzer.logmanager.IDlog;
import analyzer.logmanager.Log;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 *
 * @author TaeHoon
 */
public class FXMLDocumentController implements Initializable {
    
    private MainController controller;
    final static public String rootPath= "./log/";
    final private ObservableList<String> categories =FXCollections.observableArrayList();
    final private ObservableList<String> numbers =FXCollections.observableArrayList();
   
    //Graph의 한 라인을 저장
    private HashMap<String, XYChart.Series<String, Double> > seriesmap = new HashMap<>();
    private HashMap<String, ObservableList<String>> applylist= new HashMap<>();
    private HashMap<String, String> popup_path = new HashMap<>();
    private HashMap<String, Class> popup_controller = new HashMap<>();
    /*
    FXML 내에 선언되어있는 엘리먼트들의 id와 레퍼런스 이름을 맞춰주어야 한다.
    fxml 상에서 event메소드를 매핑하여 아래의 event 핸들러가 작동할 것.
    */
    @FXML
    private AnchorPane main_pane;
    @FXML
    private SplitPane main_split_pane;
    @FXML
    private AnchorPane split_left_pane;
    @FXML
    private TabPane tab_pane;
    @FXML
    private Tab navitab, analytab;
    @FXML
    private ListView<String> navi_listview;
    @FXML
    private TitledPane analy_select_pane,analy_apply_pane;
    @FXML
    private ListView<String> analy_select_list;
    @FXML
    private ListView<String> analy_apply_select;
    @FXML
    private AnchorPane split_right_pane;
    @FXML
    private TextArea log_textarea;
    @FXML
    private LineChart<String, Double> linechart;
    @FXML
    private CategoryAxis xaxis;
    @FXML
    private NumberAxis yaxis;
    @FXML
    private MediaView simul_view;
    @FXML
    private MenuBar menubar;
    @FXML
    private MenuItem menu_close,menu_about;
    @FXML
    private TextField noevent_filename ,event_filename, total_paceket,
            total_sender, logging_time,result_packet,result_sender,
            percent_packet,percent_sender;
    @FXML
    private Button apply_algorithm_btn;
    @FXML
    private void close_action(ActionEvent event){
        System.exit(0);
     }
    
    @FXML
    private void sel_noevent_btn_handler(){
        //리스트뷰에서 선택한 로그를 베이스 로그로 읽어들인다.
        String noevent = navi_listview.getSelectionModel().getSelectedItem();
        if(noevent !=null){
        noevent_filename.setText(noevent);
        if(controller !=null){
            controller.buildUpBaseDB(noevent, rootPath+noevent);
            System.out.println(noevent);
        }
        }        
        else {
         System.out.println("Error: 파일을 읽을 수 없습니다."+noevent);
        }
    }
    
    
    //Event로그 선택
    @FXML
    private void sel_event_btn_handler(){
    String event = navi_listview.getSelectionModel().getSelectedItem();
        
        if(event !=null&&controller !=null){
            event_filename.setText(event);
            Log log= controller.getLog(event);
           total_paceket.setText(Integer.toString(log.getTotalPacketCounts()));
           total_sender.setText(Integer.toString(log.getSenderCounts()));
           logging_time.setText(log.getTotalTime());
//            System.out.println(log.getTotalTime());
            if(applylist.containsKey(event)){
                analy_apply_select.setItems(applylist.get(event));
            }else{
                applylist.put(event,FXCollections.observableArrayList());
                analy_apply_select.setItems(applylist.get(event));
            }
        }
    }
    //
    private double setResultProperty(String eventlog){
         Log result =controller.getLog(eventlog);
            //식별한 패킷들 및 송신자 수 파악, 뷰에 데이터 바인딩
            int result_packet_count=result.getTotalPacketCounts();
            int result_sender_count=result.getSenderCounts();
            int total_packet_count= Integer.parseInt(total_paceket.getText());
            int toal_sender_count = Integer.parseInt(total_sender.getText());
            
            
            double percent_pack= 100- ((double)result_packet_count/(double)total_packet_count) *100;
            double percent_send=100- ((double)result_sender_count/(double)toal_sender_count)*100;
            
            if (percent_pack !=0 || percent_send !=0){
            result_packet.setText(Integer.toString(result_packet_count));
            result_sender.setText(Integer.toString(result_sender_count));
            
            percent_packet.setText(String.format("%.2f", percent_pack));
            percent_sender.setText(String.format("%.2f", percent_send));
            
            }
            return percent_send;
    }
    
    private void setResultGraph(String category, String eventlog,Double percent){
     //그래프 설정
           if(!categories.contains(category)){
               categories.add(category);
               xaxis.setCategories(categories);
           }
           
           if(!seriesmap.containsKey(eventlog)){
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            String[] seriesname= eventlog.split("_");
            series.setName(seriesname[0]+seriesname[1]);
            series.getData().add(new XYChart.Data<>(category, percent));
            seriesmap.put(eventlog, series);
            linechart.getData().add(series);
            
            //Filtering 적용 전, 후, 알고리즘 적용 후 순차로 할 수 있도록.
            }else{
              XYChart.Series<String, Double> series= seriesmap.get(eventlog);
              series.getData().add(new XYChart.Data<>(category, percent));
              
           }
    }
    
    private void setResultLog(String eventlog){
        String result= "";
        
        if(controller !=null){
            ArrayList<IDlog> log = controller.getLog(eventlog).getIdlogs();
            
            for(IDlog i : log){
                for(String packet : i.getLogs()){
                    result += packet +"\n";
                }
            }
            log_textarea.setText(result);
        }
  
    }
    
    
    //Broadcast 패킷 필터링
    @FXML
    private void filtering_btn_handler(){
        String eventlog = event_filename.getText();
        
        //적용할 이벤트로그 가져와서 필터링 알고리즘적용
        if(eventlog !=null){
        controller.applyFiltering(eventlog);
        
        //분석결과에 데이터 바인딩
        double percent = setResultProperty(eventlog);
        
        //그래프에 반영
        setResultGraph("Filtering",eventlog,percent);
           
        //텍스트에 반영
        setResultLog(eventlog);
        }
        
        else {
            System.out.println("Error : Event 로그가 선택되지 않았습니다.");
        }
    }
  
    //적용할 Matcher 알고리즘 선택
    //알고리즘 추가 버튼
    @FXML
    private void add_algorithm_btn_handler(){
         String matcher = analy_select_list.getSelectionModel().getSelectedItem();
         
         //해당 이벤트 로그에 적용할 알고리즘을 추가한다.
        if(matcher !=null){
            String target = event_filename.getText();
            if (target !=null){
                if (applylist.containsKey(target)){
                    ObservableList algolist= applylist.get(target);
                    algolist.add(matcher);
                    analy_apply_select.setItems(algolist);
                }else{
                    applylist.put(target, FXCollections.observableArrayList(matcher));
                    analy_apply_select.setItems(applylist.get(target));
                }
            }
        }
        
    }
    
    
    @FXML
    private void apply_algorithm_btn_handler() throws IOException{
        String eventlog =event_filename.getText();
        //적용할 알고리즘리스트를 받아온다.
        ObservableList<String> algolist= applylist.get(eventlog);
        if(algolist !=null && controller !=null){
            //1. 필요한 파라미터를 받도록 해당하는 팝업을 오픈한다.
           
       
            for(String s : algolist){
                 Parent newroot;
                Stage newstage;
                //새창 띄워서 오픈
                 newstage= new Stage(); 
                 
                 FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource(popup_path.get(s)));
                 newroot=fxmlloader.load();
                 
                 
                 newstage.setScene(new Scene(newroot));
                 newstage.setTitle(popup_path.get(s));
                 
                 //Cross match인경우 리스트뷰에 쓰일 파라미터 전달
                 
                 newstage.initModality(Modality.APPLICATION_MODAL);
                 newstage.initOwner(apply_algorithm_btn.getScene().getWindow());
                 Controller con = fxmlloader.<Controller>getController();
                 ArrayList<Object> params =new ArrayList<>();
                 
                 con.setParams(params);
                 con.setStage(newstage);
              
                 newstage.showAndWait();
                 
                 //2. 파라미터랑 커맨드를 메인 컨트롤러로 전송하고 적용 결과를 받는다.
                 controller.executeCmd(s, eventlog, params.toArray());
                 
                 
                 //3. 그래프에 알고리즘 적용
                  //분석결과에 데이터 바인딩
                  double percent = setResultProperty(eventlog);
                  
                 //그래프에 반영
                 setResultGraph(s.substring(0, s.indexOf(" ")),eventlog,percent);
                
                 
                 //추출 패킷도 반영
                 setResultLog(eventlog);
            }
        }
    
    }
    
    @FXML
    private void delete_algorithm_btn_handler(){
        String algo = analy_apply_select.getSelectionModel().getSelectedItem();
        analy_apply_select.getItems().remove(algo);
    }
    
    
    //1. 폴더에서 로그파일 읽어오기
    //2. 로그별로 HashMap<Log> 생성
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        /*
        * 컨트롤러 및 모듈 생성 초기화
        */
       controller= MainController.getSingleton();
       
       File logpath= new File(rootPath);
       ObservableList<String> items = FXCollections.observableArrayList();
       
       //폴더에서 로그파일들을 읽어온다.
       if( logpath.exists() == false ) {
            System.out.println( "log 폴더가 존재하지 않습니다." );
       }else {
        File [] arrFile = logpath.listFiles();
        for( int i = 0; i < arrFile.length; ++i ) {
         if(arrFile[i].getName().contains(".asc")){
            String fileFullPath = rootPath + arrFile[i].getName();
            items.add(arrFile[i].getName()); //navi Explore에 로그추가.
            System.out.println( fileFullPath );
            controller.addEventLog(arrFile[i].getName(), fileFullPath);
         }
        }
         navi_listview.setItems(items);// Navi Explore에 리스트 반영
    }
       
       //알고리즘 메뉴 설정
       //"Cross Matcher", "ActionCount Matcher", "Interval Matcher"
       ObservableList<String> algorithms = FXCollections.observableArrayList("Cross Matcher", "ActionCount Matcher", "Interval Matcher");
       analy_select_list.setItems(algorithms);
       
       //알고리즘 팝업 설정
       popup_path.put("Cross Matcher","CrossPopup.fxml");
       popup_path.put("Interval Matcher", "IntervalPopup.fxml");
       popup_path.put("ActionCount Matcher", "ActionCountPopUp.fxml");
       popup_controller.put("Cross Matcher",CrossController.class);
       popup_controller.put("Interval Matcher", IntervalController.class);
       popup_controller.put("ActionCount Matcher", ActionCountController.class);
        
       
    }    
    
}

