package busqueda;

import entidades.Documento;
import java.util.ArrayList;
import java.util.List;
import persistencia.Persistencia;
import vocabulario.Termino;
import vocabulario.Vocabulario;
import entidades.Posteo;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@SessionScoped
public class Buscador implements Serializable{  
    @Inject Persistencia p;
    @Inject Vocabulario v;

    public Buscador() {        
    }  
    
    public List<Documento> buscar(String querry, int R){
        String[] palabras = querry.split("\\+");
        
        ArrayList<Termino> terminos = new ArrayList(palabras.length);
        
        // Buscamos los términos asociados a cada palabra
        for (String p : palabras) {
            Termino t = v.getTermino(p);
            if(t != null){
                terminos.add(t);
            }
        }
        
        // Ordenamos de menor a mayor nr (cantidad de documentos en los que aparece el término)
        terminos.sort((t1, t2) -> t1.compareTo(t2));
        Ranking rank = new Ranking(v.getCantidadDocumentos());
        
         // Buscamos los R primeros documentos de cada término y los ubicamos en el ranking
        for (Termino t : terminos) {
            List<Posteo> posteos = p.buscarPosteos(t.getPalabra(), R);
            posteos.forEach((p) -> rank.procesarDocumento(p.getDocumento(), p.getTf(), t.getNr()));
        }
        
        // Prints de testeo
        System.out.println("Terminos: " + terminos);
        System.out.println("Ranking: " + rank.getRanking(-1));
        
        return rank.getRanking(R);
    }    
}
