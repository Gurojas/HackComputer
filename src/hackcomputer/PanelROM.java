/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackcomputer;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class PanelROM extends VBox{
    
    public ListView<String> lista;
    public int n = 2;
    private int indices[]; 
    public ComboBox<String> comboFormato;
    private Button btnClear;
    public TextField fieldPc;
    private final int tamaño = 32756;
    
    public PanelROM(){
        
        
        
        this.setSpacing(5);
        
        this.lista = new ListView<>();
        this.lista.getSelectionModel().select(0);
        this.lista.setPrefWidth(100);
        
        Label labelMemoria = new Label();
        Label labelFieldPc = new Label();
        this.comboFormato = new ComboBox();
        this.comboFormato.getItems().addAll("Asm","Bin");
        this.comboFormato.setValue("Asm");
        this.btnClear = new Button("Clear");
        
        this.fieldPc = new TextField("0");
        this.fieldPc.setPrefWidth(100);
        
        labelMemoria.setText("ROM");
        labelFieldPc.setText("PC");


        
        this.indices = new int[tamaño];
        
        this.inicializarLista();
        
        GridPane panel = new GridPane();
        
        panel.setHgap(5);
        panel.setGridLinesVisible(false);
              
        panel.add(labelMemoria, 0, 0);
        panel.add(comboFormato, 1, 0);
        panel.add(btnClear, 2, 0);
        panel.add(lista, 0, 1);
        GridPane.setColumnSpan(lista, 3);
        
        HBox panelField = new HBox(5);
        panelField.getChildren().addAll(labelFieldPc,fieldPc);
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

    public ListView<String> getLista() {
        return lista;
    }
    
    
    
}
