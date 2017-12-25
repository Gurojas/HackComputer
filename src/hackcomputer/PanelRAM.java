/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackcomputer;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Gustavo Rojas
 */
public class PanelRAM  extends VBox{
    
    public ListView<String> lista;
    private int indices[]; 
    private Button btnClear;
    public TextField fieldRegisterA;
    private final int tamaño = 24576;
    
    public PanelRAM(){
        
        this.setSpacing(5);
        
        this.lista = new ListView<>();
        this.lista.getSelectionModel().select(0);
        this.lista.setPrefWidth(150);
        
        Label labelMemoria = new Label();
        Label labelFieldRegisterA = new Label();

        this.btnClear = new Button("Clear");
        
        this.fieldRegisterA = new TextField("0");
        this.fieldRegisterA.setPrefWidth(100);
        
        labelMemoria.setText("RAM");
        labelFieldRegisterA.setText("A");
        
        this.indices = new int[tamaño];
        
        this.inicializarLista();
        
        GridPane panel = new GridPane();
        
        panel.setHgap(5);
        panel.setGridLinesVisible(false);
              
        panel.add(labelMemoria, 0, 0);
        panel.add(btnClear, 1, 0);
        GridPane.setHalignment(btnClear, HPos.RIGHT);
        panel.add(lista, 0, 1);
        GridPane.setColumnSpan(lista, 2);
        
        HBox panelField = new HBox(5);
        panelField.getChildren().addAll(labelFieldRegisterA,fieldRegisterA);
        panelField.setAlignment(Pos.CENTER);

        this.getChildren().addAll(panel,panelField);

    }
    
    private void inicializarLista(){
        int n = this.indices.length;
        for (int i = 0; i < n; i++) {
            String e = String.valueOf(i);
            this.lista.getItems().add(e+"\t");
        }
    }
    
}
