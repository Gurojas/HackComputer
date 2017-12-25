/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackcomputer;

import java.util.ArrayList;

/**
 *
 * @author Gustavo Rojas
 */
public class TraductorInstrucciones {
    
    private ArrayList<String> instruccionesBin;
    private ArrayList<String> instruccionesAsm;
    
    public TraductorInstrucciones(){
        this.instruccionesBin = new ArrayList<>();
        this.instruccionesAsm = new ArrayList<>();
    
    }
    
    public ArrayList<String> convertirInstruccionBinAAsm(ArrayList<String> instrucciones){
        for (String instruccion : instrucciones){
            int bits[] = convertirAInt(instruccion);
            // pregunto si la instruccion es tipo A o C
            if (esInstruccionTipoA(bits[0])){
                String value = this.getValue(instruccion);
                this.instruccionesAsm.add(value);
            }
            else{
                
                // dest=comp;jump
                
                String dest = this.obtenerDest(instruccion);
                // bit 3 = a bit
                String comp = this.obtenerComp(bits[3], instruccion);
                String jump = this.obtenerJump(instruccion);
                
                // comp;jum
                if (dest.equals("")){
                    this.instruccionesAsm.add(comp+";"+jump);
                }
                // dest=comp;
                else{
                    this.instruccionesAsm.add(dest+"="+comp);
                }
                
            }
        }
        
        return this.instruccionesAsm;
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
        }
        else if (bitsComp.equals("111111")){
            comp = "1";
        }
        else if (bitsComp.equals("111010")){
            comp = "-1";
        }
        else if (bitsComp.equals("001100")){
            comp = "D";
        
        }
        else if (bitsComp.equals("110000")){
            if(guardarRegistroA(bitA)){
                comp = "A";
            }
            else{
                comp = "M";
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
        
        }
        else if (bitsComp.equals("110011")){
            if(guardarRegistroA(bitA)){
                comp = "-A";
            }
            else{
                comp = "-M";
            }
        
        }
        else if (bitsComp.equals("011111")){
            comp = "D+1";
        
        }
        else if (bitsComp.equals("110111")){
            if(guardarRegistroA(bitA)){
                comp = "A+1";
            }
            else{
                comp = "M+1";
            }
        
        }
        else if (bitsComp.equals("001110")){
            comp = "D-1";
        
        }
        else if (bitsComp.equals("110010")){
            if(guardarRegistroA(bitA)){
                comp = "A-1";
            }
            else{
                comp = "D-1";
            }
        
        }
        else if (bitsComp.equals("000010")){
            if(guardarRegistroA(bitA)){
                comp = "D+A";
            }
            else{
                comp = "D+M";
            }
            
        }
        else if (bitsComp.equals("010011")){
            if(guardarRegistroA(bitA)){
                comp = "D-A";
            }
            else{
                comp = "D-M";
            }
            
        }
        else if (bitsComp.equals("000111")){
            if(guardarRegistroA(bitA)){
                comp = "A-D";
            }
            else{
                comp = "M-D";
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
        }
        else if (bitsDest.equals("010")){
            dest = "D";
        }
        else if (bitsDest.equals("011")){
            dest = "MD";
        }
        else if (bitsDest.equals("100")){
            dest = "A";
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
            jump = "JGT";
        }
        else if (bitsJump.equals("010")){
            jump = "JEQ";
        }
        else if (bitsJump.equals("011")){
            jump = "JGE";
        }
        else if (bitsJump.equals("100")){
            jump = "JLT";
        }
        else if (bitsJump.equals("101")){
            jump = "JNE";
        }
        else if (bitsJump.equals("110")){
            jump = "JLE";
        }
        else if (bitsJump.equals("111")){
            jump = "JMP";
        }
        
        return jump;
    }
    
     //@value
    public String getValue(String instruccion){
        String bin = instruccion.substring(1);
        int numBin = Integer.parseInt(bin, 2);
        return "@"+numBin;
    }
        
        
   
}
