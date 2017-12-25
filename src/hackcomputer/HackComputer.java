/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackcomputer;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Gustavo Rojas
 */
public class HackComputer extends Application {
    
    
    private ArrayList<String> instruccionesBin;
    private ArrayList<String> instruccionesAsm;
    private EjecutorInstrucciones ejecutor;
    private int countStep;
    public static Integer pcCounter;
    private Timer timer;
    private double tic = 1;
    private int i = 0;
    
    @Override
    public void start(Stage primaryStage) {
        

        
        this.countStep = 0;
        this.pcCounter = 0;
        
        double w = 800;
        double h = 600;
        
        BorderPane panelPrincipal = new BorderPane();
        
        // creacion barra de herramientas
        MenuBar barraMenu = new MenuBar();
        
        // Creacion de la opcion file
        Menu menu = new Menu("File");
        
        MenuItem itemLoadProgram = new MenuItem("Load Program");
        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
        MenuItem itemExit = new MenuItem("Exit");
        
        menu.getItems().addAll(itemLoadProgram,separatorMenuItem,itemExit);
        
        // añado a la barra de menu el menu File
        barraMenu.getMenus().add(menu);
        
        VBox panelBarraHerramientas = new VBox();
       
        
        // creacion barra de herramientas con botones
        
        ToolBar barraBotones = new ToolBar();
        
        Separator separador1 = new Separator(Orientation.VERTICAL);
        
        Button btnLoadProgram = new Button("Load Program");
        Button btnSingleStep = new Button("Single Step");
        Button btnRun = new Button("Run");
        Button btnStop = new Button("Stop");
        btnStop.setDisable(true);
        Button btnReset = new Button("Reset");
        
        Separator separador2 = new Separator(Orientation.VERTICAL);
        
        Slider slider = new Slider();
        slider.setMin(1);
        slider.setMax(10);
        slider.setValue(1);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setBlockIncrement(2);

        barraBotones.getItems().addAll(btnLoadProgram,separador1,btnSingleStep,btnRun,btnStop,btnReset,separador2,slider);
  
        
        // añado al panel superior las barras de herramientas
        panelBarraHerramientas.getChildren().addAll(barraMenu,barraBotones);
        
        // panel centro
        PanelROM panelROM = new PanelROM();
        PanelRAM panelRAM = new PanelRAM();
        
        HBox panelMemorias = new HBox(50);
        panelMemorias.setPadding(new Insets(20));
        panelMemorias.setAlignment(Pos.CENTER);
        panelMemorias.getChildren().addAll(panelROM,panelRAM);
        
        
        Label labelFieldRegisterD = new Label("D");
        
        TextField fieldRegisterD = new TextField("0");
        fieldRegisterD.setPrefWidth(100);
        
        HBox panelField = new HBox(5);
        panelField.setAlignment(Pos.CENTER);
        panelField.getChildren().addAll(labelFieldRegisterD,fieldRegisterD);
        
        VBox panelCentro = new VBox();
        panelCentro.getChildren().addAll(panelMemorias,panelField);
        
        // añado el panel de las memorias al panel principal
        panelPrincipal.setCenter(panelCentro);
        

        
        // añado al panel principal los demas subpaneles
        panelPrincipal.setTop(panelBarraHerramientas);
        
        Scene scene = new Scene(panelPrincipal, w, h);
        
        primaryStage.setTitle("Hack computer");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        // seccion de los eventos de las menuItem
        itemLoadProgram.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                
                fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("hack", "*.hack")
                );
                
                fileChooser.setTitle("Load hack program");
                File file = fileChooser.showOpenDialog(primaryStage);
                
                if (file != null){
                    String filePath = file.getAbsolutePath();
                    LectorInstrucciones lectorInstrucciones = new LectorInstrucciones(filePath);
                    instruccionesBin = lectorInstrucciones.leerInstrucciones();
                    TraductorInstrucciones traductorInstrucciones = new TraductorInstrucciones();
                    instruccionesAsm = traductorInstrucciones.convertirInstruccionBinAAsm(instruccionesBin);
                    int n = instruccionesBin.size();
                    for (int i = 0; i < n; i++) {
                        String Instruccion = instruccionesAsm.get(i);
                        panelROM.lista.getItems().set(i,i+"  "+Instruccion);
                    }
                }
            }
        });
        
        itemExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
        
        
        // seecion de los eventos de los botones
        
        btnLoadProgram.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Load Program");
                FileChooser fileChooser = new FileChooser();
                
                fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("hack", "*.hack")
                );
                
                
                fileChooser.setTitle("Load hack program");
                File file = fileChooser.showOpenDialog(primaryStage);
                
                if (file != null){
                    String filePath = file.getAbsolutePath();
                    LectorInstrucciones lectorInstrucciones = new LectorInstrucciones(filePath);
                    instruccionesBin = lectorInstrucciones.leerInstrucciones();
                    TraductorInstrucciones traductorInstrucciones = new TraductorInstrucciones();
                    instruccionesAsm = traductorInstrucciones.convertirInstruccionBinAAsm(instruccionesBin);
                    int n = instruccionesBin.size();
                    for (int i = 0; i < n; i++) {
                        String Instruccion = instruccionesAsm.get(i);
                        panelROM.lista.getItems().set(i,i+"  "+Instruccion);
                    }
                }
                
            }
        });
        
        this.ejecutor = new EjecutorInstrucciones(panelRAM.fieldRegisterA, fieldRegisterD, panelRAM.lista);

        btnSingleStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(HackComputer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (pcCounter < instruccionesBin.size()){
                    String instruccion = instruccionesBin.get(pcCounter);
                    ejecutor.ejecutarInstruccion(instruccion);
                    
                    
                    
                    
                    //pcCounter++;
                    
                    panelROM.fieldPc.setText(String.valueOf(pcCounter));
                    panelROM.lista.getSelectionModel().select(pcCounter);
                    panelROM.lista.getFocusModel().focus(pcCounter);
                    panelROM.lista.scrollTo(pcCounter);
                    
                    
                }
                
                
                
            }
                
            
        });
        
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*
                System.out.println("hola"+i);
                i++;
                */
                if (pcCounter < instruccionesBin.size()){
                    String instruccion = instruccionesBin.get(pcCounter);
                    ejecutor.ejecutarInstruccion(instruccion);
                    //pcCounter++;
                    
                    panelROM.fieldPc.setText(String.valueOf(pcCounter));
                    panelROM.lista.getSelectionModel().select(pcCounter);
                    panelROM.lista.getFocusModel().focus(pcCounter);
                    panelROM.lista.scrollTo(pcCounter);
                }
                
                
                

            }
        };
        
        this.timer = new Timer(event,this.tic);
        
        
        btnRun.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Run");
                btnLoadProgram.setDisable(true);
                btnRun.setDisable(true);
                btnSingleStep.setDisable(true);
                btnReset.setDisable(true);
                btnStop.setDisable(false);
                itemLoadProgram.setDisable(true);
                timer.start();
            }
        });
        
        btnStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Stop");
                btnLoadProgram.setDisable(false);
                btnSingleStep.setDisable(false);
                btnRun.setDisable(false);
                btnReset.setDisable(false);
                btnStop.setDisable(true);
                itemLoadProgram.setDisable(false);
                timer.stop();
            }
        });
        
        btnReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Reset");
            }
        });
        
        panelROM.comboFormato.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int n = instruccionesAsm.size();
                if(panelROM.comboFormato.getSelectionModel().isSelected(0)){
                    for (int i = 0; i < n; i++) {
                        String instruccionAsm = instruccionesAsm.get(i);
                        panelROM.lista.getItems().set(i,i+"  "+instruccionAsm);
                    }
                    
                }
                else{
                    for (int i = 0; i < n; i++) {
                        String instruccionAsm = instruccionesBin.get(i);
                        panelROM.lista.getItems().set(i,i+"  "+instruccionAsm);
                    }                
                }
            }
        });
        
        slider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                HackComputer.this.timer.setTic((double)newValue);
            }
        });
        
        

        panelROM.lista.getSelectionModel().select(0);
        panelRAM.lista.getSelectionModel().select(0);
        
       
        
    }
    
   
        
        
        
        


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
