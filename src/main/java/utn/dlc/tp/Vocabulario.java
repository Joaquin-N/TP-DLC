package utn.dlc.tp;

import java.util.Collection;
import java.util.HashMap;

public class Vocabulario {
    HashMap<String, Termino> v;
    
    public Vocabulario(){
       v = new HashMap<>();
    }
    
    public void insertarTermino(Termino t){
        v.put(t.getPalabra(), t);        
    }
    
    public void insertarPalabra(String p){
        Termino t = v.get(p);
        if(t == null)
            agregarTermino(p);
        else
            actualizarTermino(t);
    }
    
    private void agregarTermino(String p){
        Termino t = new Termino(p);
        v.put(p, t);
    }
    
    private void actualizarTermino(Termino t){
        t.incrementarNr();
    }
    
    public Collection<Termino> listarTerminos(){
        return v.values();
    }

    @Override
    public String toString() {
        return "Terminos totales: " + v.size() + "<br>" + v.values().toString();
    }
}
