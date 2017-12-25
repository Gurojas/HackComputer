/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackcomputer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Gustavo Rojas
 */
public class LectorInstrucciones {
    
    private Scanner lector;
    private ArrayList<String> instrucciones;
    
    public LectorInstrucciones(String rutaArchivo){
        File file = new File(rutaArchivo);
        
        try {
            this.lector = new Scanner(file);
        } 
        catch (FileNotFoundException ex) {
            System.out.println("No existe el archivo");
            ex.printStackTrace();
        }
        this.instrucciones = new ArrayList<>();
    }
    
    public ArrayList<String> leerInstrucciones(){
        int i = 0;
        while(this.lector.hasNextLine()){
            String instruccion = this.lector.nextLine();
            System.out.println(instruccion);
            this.instrucciones.add(instruccion);
        }
        this.lector.close();
        return this.instrucciones;
    }
    
    
    
}
