/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackcomputer;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 *
 * @author Gustavo Rojas
 */
public class EjecutorInstrucciones {
    
    private TextField registroA;
    private TextField registroD;
    private ListView<String> ram;
    private int A = 0;
    private int res = 0;

    
    public EjecutorInstrucciones(TextField registroA, TextField registroD,ListView<String> ram ){
        this.registroA = registroA;
        this.registroD = registroD;
        this.ram = ram;
    }
    
    
    public void ejecutarInstruccion(String instruccion){
        
        int bits[] = convertirAInt(instruccion);
        if (esInstruccionTipoA(bits[0])){
            
            this.guardarEnRegA(instruccion);
            HackComputer.pcCounter++;
        }
        else{
            
            // bit 3 = a bit
            
            
            String comp = this.obtenerComp(bits[3], instruccion);
            String dest = this.obtenerDest(instruccion);
            
            //String jump = this.obtenerJump(instruccion);
            if (this.obtenerJump(instruccion).equals("")){
                HackComputer.pcCounter++;
            }

            

            
        }

    }
    
    
    public int[] convertirAInt(String instruccion){
        int n = instruccion.length();
        int instruccionInt[] = new int[n];
        for (int i = 0; i < n; i++) {
            char c = instruccion.charAt(i);
            instruccionInt[i] = Character.getNumericValue(c);
        }
        return instruccionInt;
    }
    
    public boolean esInstruccionTipoA(int bit){
        return bit == 0; // si es igual a cero es instruccion tipo A
    }
    
    public boolean guardarRegistroA(int bit){
        return bit == 0; // si es igual a cero entonces se guarda en el registro A
    }
    
    public String obtenerComp(int bitA,String instruccion){
        // en el indice 4 comienza el bit c hsta el 10 
        String bitsComp = instruccion.substring(4, 10);
        String comp = "";
        
        if (bitsComp.equals("101010")){
            comp = "0";
            //this.ram.getItems().set(this.A, this.A+"  "+0);
            this.res = 0;
            
            
        }
        else if (bitsComp.equals("111111")){
            comp = "1";
            //this.ram.getItems().set(this.A, this.A+"  "+1);
            this.res = 1;
        }
        else if (bitsComp.equals("111010")){
            comp = "-1";
            //this.ram.getItems().set(this.A, this.A+"  "+-1);
            this.res = -1;
        }
        else if (bitsComp.equals("001100")){
            comp = "D";
            this.res = Integer.valueOf(this.registroD.getText());
        }
        else if (bitsComp.equals("110000")){
            if(guardarRegistroA(bitA)){
                comp = "A";
                this.res = Integer.valueOf(this.registroA.getText());
            }
            else{
                comp = "M";
                int data = obtenerDataRam(this.ram.getItems().get(this.A));
                this.res = data;
                System.out.println(res);
            }

        
        }
        else if (bitsComp.equals("001101")){
            comp = "!D";
        
        }
        else if (bitsComp.equals("110001")){
            if(guardarRegistroA(bitA)){
                comp = "!A";
            }
            else{
                comp = "!M";
            }
                
        }
        else if (bitsComp.equals("001111")){
            comp = "-D";
            this.res = -Integer.valueOf(this.registroD.getText());
        
        }
        else if (bitsComp.equals("110011")){
            if(guardarRegistroA(bitA)){
                comp = "-A";
                this.res = -Integer.valueOf(this.registroA.getText());
            }
            else{
                comp = "-M";
                int data = obtenerDataRam(this.ram.getItems().get(this.A));
                this.res = -data;
            }
        
        }
        else if (bitsComp.equals("011111")){
            comp = "D+1";
            this.res = Integer.valueOf(this.registroD.getText()) + 1;
        
        }
        else if (bitsComp.equals("110111")){
            if(guardarRegistroA(bitA)){
                comp = "A+1";
                this.res = Integer.valueOf(this.registroA.getText()) + 1;
            }
            else{
                comp = "M+1";
                int data = obtenerDataRam(this.ram.getItems().get(this.A));
                this.res = data + 1;
            }
        
        }
        else if (bitsComp.equals("001110")){
            comp = "D-1";
            this.res = Integer.valueOf(this.registroD.getText()) - 1;
        
        }
        else if (bitsComp.equals("110010")){
            if(guardarRegistroA(bitA)){
                comp = "A-1";
                this.res = Integer.valueOf(this.registroA.getText()) - 1;
            }
            else{
                comp = "M-1";
                int data = obtenerDataRam(this.ram.getItems().get(this.A));
                this.res = data - 1;
            }
        
        }
        else if (bitsComp.equals("000010")){
            if(guardarRegistroA(bitA)){
                comp = "D+A";
                this.res = Integer.valueOf(this.registroD.getText()) + Integer.valueOf(this.registroA.getText());
            }
            else{
                comp = "D+M";
                int data = obtenerDataRam(this.ram.getItems().get(this.A));
                this.res = Integer.valueOf(this.registroD.getText()) + data;
            }
            
        }
        else if (bitsComp.equals("010011")){
            if(guardarRegistroA(bitA)){
                comp = "D-A";
                this.res = Integer.valueOf(this.registroD.getText()) - Integer.valueOf(this.registroA.getText());
            }
            else{
                comp = "D-M";
                int data = obtenerDataRam(this.ram.getItems().get(this.A));
                this.res = Integer.valueOf(this.registroD.getText()) - data;
            }
            
        }
        else if (bitsComp.equals("000111")){
            if(guardarRegistroA(bitA)){
                comp = "A-D";
                this.res = Integer.valueOf(this.registroA.getText()) - Integer.valueOf(this.registroD.getText());
            }
            else{
                comp = "M-D";
                int data = obtenerDataRam(this.ram.getItems().get(this.A));
                this.res = data - Integer.valueOf(this.registroD.getText());
            }
            
        }
        else if (bitsComp.equals("000000")){
            if(guardarRegistroA(bitA)){
                comp = "D&A";
            }
            else{
                comp = "D&M";
            }
            
        }
        else if (bitsComp.equals("010101")){
            if(guardarRegistroA(bitA)){
                comp = "D|A";
            }
            else{
                comp = "D|M";
            }
            
        }

        return comp;
    }
    
    public String obtenerDest(String instruccion){
        // en el indice 10 comienza el bit d hsta el 13 
        String bitsDest = instruccion.substring(10, 13);
        String dest = "";
        if (bitsDest.equals("000")){
            dest = "";
        }
        else if (bitsDest.equals("001")){
            dest = "M";
            this.ram.getItems().set(this.A, this.A+"  "+res);
            this.ram.getSelectionModel().select(this.A);
            this.ram.getFocusModel().focus(this.A);
            this.ram.scrollTo(this.A);
        }
        else if (bitsDest.equals("010")){
            dest = "D";
            this.registroD.setText(String.valueOf(this.res));
        }
        else if (bitsDest.equals("011")){
            dest = "MD";
        }
        else if (bitsDest.equals("100")){
            dest = "A";
            this.registroA.setText(String.valueOf(this.res));
        }
        else if (bitsDest.equals("101")){
            dest = "AM";
        }
        else if (bitsDest.equals("110")){
            dest = "AD";
        }
        else if (bitsDest.equals("111")){
            dest = "AMD";
        }
        
        return dest;
    }
    
    public String obtenerJump(String instruccion){
        String bitsJump = instruccion.substring(13);
        String jump = "";
        if (bitsJump.equals("000")){
            jump = "";
        }
        else if (bitsJump.equals("001")){
            
            if (this.res > 0){
                jump = "JGT";
                HackComputer.pcCounter = this.A;
            }
        }
        else if (bitsJump.equals("010")){
            
            if (this.res == 0){
                jump = "JEQ";
                HackComputer.pcCounter = this.A;
            }
        }
        else if (bitsJump.equals("011")){
            
            if (this.res >= 0){
                jump = "JGE";
                HackComputer.pcCounter = this.A;
            }
        }
        else if (bitsJump.equals("100")){
            
            if (this.res < 0){
                jump = "JLT";
                HackComputer.pcCounter = this.A;
            }
        }
        else if (bitsJump.equals("101")){
            
            if (this.res != 0){
                jump = "JNE";
                HackComputer.pcCounter = this.A;
            }
            
        }
        else if (bitsJump.equals("110")){
            
            if (this.res <= 0){
                jump = "JLE";
                HackComputer.pcCounter = this.A;
            }
        }
        else if (bitsJump.equals("111")){
            jump = "JMP";
            HackComputer.pcCounter = this.A;
        }
        
        return jump;
    }
    
     //@value
    public void guardarEnRegA(String instruccion){
        
        String bin = instruccion.substring(1);
        int numBin = Integer.parseInt(bin, 2);
        this.A = numBin;
        this.registroA.setText(String.valueOf(this.A));
        System.out.println("Valor de A: "+this.A);

    }
    
    public int obtenerDataRam(String data){
        int index = data.indexOf("  ");
        String num = data.substring(index+2);
        return Integer.valueOf(num);
    }
    

    
    
    
}
