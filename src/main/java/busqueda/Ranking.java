package busqueda;

import java.util.ArrayList;
import entidades.Documento;

public class Ranking {
    int N;
    ArrayList<Documento> ld = new ArrayList<>();
    
    public Ranking(int N){
        this.N = N;
    }
    
    public void procesarDocumento(Documento d, int tf, int nr){
        if(ld.contains(d)){
            // Puede ser que contenga el mismo documento pero no el mismo 'objeto'
            // Recumeramos la misma instancia del documento
            int i = ld.indexOf(d);
            d = ld.get(i);
        } else{
            // Insertamos el documento en la lista
            d.setIr(0.00);
            ld.add(d);
        }        
        
        double wr = tf * Math.log((double)N/nr);
        d.sumarIr(wr);
    }
    
    public ArrayList<Documento> getRanking(){
        ld.sort((d1, d2) -> d1.compareTo(d2));
        return ld;
    }
    
    
}
