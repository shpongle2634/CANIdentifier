/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer.view;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author TaeHoon
 */
public class CrossController extends Controller implements Initializable{
    @FXML
    private TextField action_count_txt;
    @FXML
    private ListView<String> cross_log_list;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cross_log_list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //팝업 리스트를 초기화
        ObservableList<String> items = FXCollections.observableArrayList();
        File logpath= new File(FXMLDocumentController.rootPath);
        if( logpath.exists() == false ) {
            System.out.println( "log 폴더가 존재하지 않습니다." );
       }else {
        File [] arrFile = logpath.listFiles();
            for(int i=0; i<arrFile.length; ++i){
                items.add(arrFile[i].getName());
            }
            cross_log_list.setItems(items);
        }
    }

    @FXML
    public void ok_btn_handler() {
        //선택한 로그들을 크로스매치에 이용한다.
        ObservableList<String> logs = cross_log_list.getSelectionModel().getSelectedItems();
      if(logs!=null && params!=null){
          for(String log : logs)
            params.add(log);
      
          dialogstage.close();
      }
      
    }
 
}
