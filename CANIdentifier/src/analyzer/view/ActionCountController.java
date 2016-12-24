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
public class ActionCountController extends Controller implements Initializable{
    @FXML
    private TextField action_count_txt;
    
    
    @FXML
    public void ok_btn_handler(){
        String count = action_count_txt.getText() ;
        if(count !=null && params !=null){
           params.add(count);
           dialogstage.close();
        }
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
 
    }

    
    
}
