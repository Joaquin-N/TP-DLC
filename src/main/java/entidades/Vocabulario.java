package entidades;

import java.util.HashMap;

public class Vocabulario {
    HashMap<String, Termino> v;
    
    public Vocabulario(){
       v = new HashMap<>();
    }
    
    public void insertarTermino(Termino t){
        v.put(t.getPalabra(), t);        
    }
        
    public Termino getTermino(String palabra){
        return v.get(palabra);
    }
    
    @Override
    public String toString() {
        return "Terminos totales: " + v.size() + "<br>" + v.values().toString();
    }
}
