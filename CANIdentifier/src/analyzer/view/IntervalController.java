/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author TaeHoon
 */
public class IntervalController extends Controller implements Initializable{
    @FXML
    private TextField interval_txt,error_range_txt;
   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @Override
    public void ok_btn_handler() {
        System.out.println("ok handler"+params);
        if(params !=null){
            
            System.out.println("params !=null");
            String interval =interval_txt.getText();
            String error_range = error_range_txt.getText();
            System.out.println(interval + " " + error_range);
            if(interval !=null){
                params.add(interval);
                if (error_range != null)  params.add(error_range);
                else params.add("0.1");
                dialogstage.close();
            }
        }
    }
}

