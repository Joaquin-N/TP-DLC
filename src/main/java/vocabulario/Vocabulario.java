package vocabulario;

import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.ejb.Singleton;
import persistencia.Persistencia;

@Singleton
@Startup
public class Vocabulario {
    HashMap<String, Termino> v;
    int N; //Cantidad de documentos
    
    @Inject
    Persistencia p;
    
    public Vocabulario(){  
    }
    
    @PostConstruct
    public void initialize(){
        v = new HashMap<>();
        long startTime = System.currentTimeMillis();
        p.cargarVocabulario(this);            
        long endTime = System.currentTimeMillis();      

        long minutos = (endTime - startTime) / 60000;
        long segundos = ((endTime - startTime) % 60000) / 1000;

        System.out.println("--- Carga de vocabulario finalizada. Tiempo transcurrido: " + minutos + "' " + segundos + "''");
    }

    public HashMap<String, Termino> getV() {
        return v;
    }
    
    public void setN(int N){
        this.N = N;
    }
    
    public int getCantidadDocumentos(){
        return N;
    }
    
    public void insertarTermino(Termino t){
        v.put(t.getPalabra(), t);        
    }
        
    public Termino getTermino(String palabra){
        return v.get(palabra);
    }
    
    public void recargar(){
        initialize();
    }
}
