package busqueda;

import java.util.ArrayList;
import entidades.Documento;
import java.util.List;

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
        
        // Calculamos el ir (indice de relevancia) del documento para el término dado
        double wr = tf * Math.log((double)N/nr);
        // Lo sumamos al acumulador de ir del documento
        d.sumarIr(wr);
    }
    
    public List<Documento> getRanking(int R){
        // Ordenamos los documentos por mayor ir
        ld.sort((d1, d2) -> d1.compareTo(d2));
        
        if(R == -1 || R > ld.size()) return ld;
        return ld.subList(0, R);
    }
    
    
}
