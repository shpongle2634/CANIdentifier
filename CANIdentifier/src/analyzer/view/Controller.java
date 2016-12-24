/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer.view;

import java.util.ArrayList;
import javafx.stage.Stage;

/**
 *
 * @author TaeHoon
 */
public abstract class Controller {
    
    protected Stage dialogstage;
    protected ArrayList<Object> params;
    
    
    public abstract void ok_btn_handler();
    
    public void setStage(Stage dialogstage) {
        this.dialogstage=dialogstage;
    }
    public void setParams(ArrayList<Object> params) {
        this.params=params;
    }
    public void cancel_btn_handler() {
        dialogstage.close();
    }
}
